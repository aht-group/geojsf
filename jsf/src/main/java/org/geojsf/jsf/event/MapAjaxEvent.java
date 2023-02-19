package org.geojsf.jsf.event;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;
import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.geojsf.Coordinate;
import org.geojsf.model.xml.geojsf.Scale;
import org.geojsf.model.xml.geojsf.ViewPort;
import org.geojsf.util.GeoJsfDistanceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This type of event is used in combination with the &lt;f:ajax&gt; tag.
 * Because the listener attribute of a JSF 2 - AJAX tag requires an
 * instance of an AjaxBehaviorEvent, further class casting is needed
 * to work with the custom features (right now the Coordinates object
 * attached to the event).
 */
public class MapAjaxEvent extends AjaxBehaviorEvent
{
	final static Logger logger = LoggerFactory.getLogger(MapAjaxEvent.class);
	private static final long serialVersionUID = 1L;

	private final static String keyClickLat = "org.geojsf.click.lat";
	private final static String keyClickLon = "org.geojsf.click.lon";

	private final static String keyFeatureId = "org.geojsf.click.feature.id";
	private final static String keyFeatureType = "org.geojsf.click.feature.type";
	private Coordinate click;
	public Coordinate getClickCoordinate(){return click;}

	private String selectedFeature;
	public String getSelectedFeature(){return selectedFeature;}

	private ViewPort viewPort;
	public ViewPort getViewPort() {return viewPort;}
//	public void setViewPort(ViewPort viewPort) {this.viewPort = viewPort;}

	public MapAjaxEvent(UIComponent component, ClientBehavior behavior)
	{
		super(component, behavior);
	}

	public void setClickCoordinates(java.util.Map<String,String> params)
	{
		if(params.containsKey(keyClickLat) && params.containsKey(keyClickLon))
		{
			String lat = params.get(keyClickLat);
			String lon = params.get(keyClickLon);
			setClickCoordinates(lat,lon);
		}
	}

	public void setClickCoordinates(String lat, String lon)
	{
		click = new Coordinate();
		click.setLat(new Double(lat));
		click.setLon(new Double(lon));
		logger.info("Setting click coordinates: lat:"+lat+" lon:"+lon);
	}

	public void setSelectedFeature(java.util.Map<String,String> params)
	{
		if(params.containsKey(keyFeatureId) && params.containsKey(keyFeatureType))
		{
			String featureId = params.get(keyFeatureId);
			String featureType = params.get(keyFeatureType);
			this.selectedFeature = featureId +  ":" + featureType;
		}
	}

	public void setViewport(java.util.Map<String,String> params)
	{
		try
		{
			String viewPortCenterLon = params.get("org.geojsf.viewport.center.lon");
			String viewPortCenterLat = params.get("org.geojsf.viewport.center.lat");
			String viewPortZoom		 = params.get("org.geojsf.viewport.zoom");
			String viewPortScale	 = params.get("org.geojsf.viewport.scale");
			String viewPortBottom    = params.get("org.geojsf.viewport.bottom");
			String viewPortTop       = params.get("org.geojsf.viewport.top");
			String viewPortLeft      = params.get("org.geojsf.viewport.left");
			String viewPortRight     = params.get("org.geojsf.viewport.right");

			logger.info("org.geojsf.viewport.bottom "+viewPortBottom);
			logger.info("org.geojsf.viewport.top "+viewPortTop);
			logger.info("org.geojsf.viewport.left "+viewPortLeft);
			logger.info("org.geojsf.viewport.right "+viewPortRight);
	

			boolean debug = false;
			if(debug)
			{
				logger.info("org.geojsf.viewport.center.lon "+viewPortCenterLon);
				logger.info("org.geojsf.viewport.center.lat "+viewPortCenterLat);
			}

			setViewport(viewPortCenterLat, viewPortCenterLon, viewPortZoom, viewPortScale,viewPortTop, viewPortBottom, viewPortLeft, viewPortRight);
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}

	public void setViewport(String viewPortCenterLat,String viewPortCenterLon, String viewPortZoom, String viewPortScale, String top, String bottom, String left, String right)
	{
		viewPort = new org.geojsf.model.xml.geojsf.ViewPort();
		viewPort.setLat(Double.parseDouble(viewPortCenterLat));
		viewPort.setLon(Double.parseDouble(viewPortCenterLon));
		viewPort.setTop(new Double(top));
		viewPort.setBottom(new Double(bottom));
		viewPort.setLeft(new Double(left));
		viewPort.setRight(new Double(right));
		Scale scale = new Scale();
		scale.setValue((new Double(viewPortScale)).intValue());
		scale.setZoom((new Double(viewPortZoom)).intValue());
		viewPort.setScale(scale);
//		JaxbUtil.info(viewPort);
		org.locationtech.jts.geom.Coordinate start = new org.locationtech.jts.geom.Coordinate(viewPort.getLeft(), viewPort.getTop());
		org.locationtech.jts.geom.Coordinate end   = new org.locationtech.jts.geom.Coordinate(viewPort.getLeft(), viewPort.getBottom());
		Double distance;
		try
		{
			distance = GeoJsfDistanceCalculator.getDistance(start, end);
			logger.info("Distance by GeoTools: " +distance + " m - will be ignored for given scale/resolution");
		//	viewPort.getScale().setValue(distance.intValue());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addScale(String scale, String zoom)
	{
		Scale scl = new Scale();
		scl.setValue(new Integer(scale).intValue());
		scl.setZoom(new Double(zoom).doubleValue());
		addScale(scl);
	}
	public void addScale(Scale scale)
	{
		viewPort.setScale(scale);
	}


	@Override public boolean isAppropriateListener(FacesListener faceslistener) {return (faceslistener instanceof AjaxBehaviorListener);}
    @Override public void processListener(FacesListener faceslistener) {((AjaxBehaviorListener) faceslistener).processAjaxBehavior(this);}
}