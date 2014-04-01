package org.geojsf.event;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;

import org.geojsf.xml.geojsf.Coordinate;

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
	private Float scale;
	
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
	
	public Coordinate getCoordinate()
	{
		return coordinate;
	}

	public Float getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = new Float(scale);
	}

	@Override public boolean isAppropriateListener(FacesListener faceslistener) {return (faceslistener instanceof AjaxBehaviorListener);}
    @Override public void processListener(FacesListener faceslistener) {((AjaxBehaviorListener) faceslistener).processAjaxBehavior(this);}
}