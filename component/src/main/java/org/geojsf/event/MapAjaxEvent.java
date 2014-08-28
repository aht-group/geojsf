package org.geojsf.event;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;

import org.geojsf.xml.geojsf.Coordinate;
import org.geojsf.xml.geojsf.Scale;
import org.geojsf.xml.geojsf.ViewPort;

/**
 * This type of event is used in combination with the <f:ajax> tag.
 * Because the listener attribute of a JSF 2 - AJAX tag requires an
 * instance of an AjaxBehaviorEvent, further class casting is needed
 * to work with the custom features (right now the Coordinates object
 * attached to the event).
 */
public class MapAjaxEvent extends AjaxBehaviorEvent
{
	private static final long serialVersionUID = 1L;
	private Coordinate coordinate;
	private ViewPort viewPort;
	
	public MapAjaxEvent(UIComponent component, ClientBehavior behavior)
	{
		super(component, behavior);
	}
	
	public void setLatLon(String lat, String lon)
	{
		//Because the only the value String is relevant right now, the Coordinates are constructed from a given String 
		coordinate = new Coordinate();
		coordinate.setLat(new Double(lat));
		coordinate.setLon(new Double(lon));
	}
	
	public void setViewport(String lat, String lon, String top, String bottom, String left, String right)
	{
		viewPort = new org.geojsf.xml.geojsf.ViewPort();
		viewPort.setLat(new Double(lat));
		viewPort.setLon(new Double(lon));
		viewPort.setTop(new Double(top));
		viewPort.setBottom(new Double(bottom));
		viewPort.setLeft(new Double(left));
		viewPort.setRight(new Double(right));
	}
	
	public void addScale(Scale scale)
	{
		viewPort.setScale(scale);
	}
	
	public Coordinate getCoordinate()
	{
		return coordinate;
	}

	@Override public boolean isAppropriateListener(FacesListener faceslistener) {return (faceslistener instanceof AjaxBehaviorListener);}
    @Override public void processListener(FacesListener faceslistener) {((AjaxBehaviorListener) faceslistener).processAjaxBehavior(this);}

	public ViewPort getViewPort() {
		return viewPort;
	}

	public void setViewPort(ViewPort viewPort) {
		this.viewPort = viewPort;
	}

}