package org.geojsf.event;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;

import org.geojsf.xml.gml.Coordinates;


/**
 * This type of event is used in combination with the <f:ajax> tag.
 * Because the listener attribute of a JSF 2 - AJAX tag requires an
 * instance of an AjaxBehaviorEvent, further class casting is needed
 * to work with the custom features (right now the Coordinates object
 * attached to the event).
 */
public class MapAjaxEvent extends AjaxBehaviorEvent {

	private static final long serialVersionUID = -3540720081415282597L;
	private Coordinates coordinates;
	
	public MapAjaxEvent(UIComponent component, ClientBehavior behavior) {
		super(component, behavior);
	}
	
	public void setCoordinates(String value)
	{
		//Because the only the value String is relevant right now, the Coordinates are constructed from a given String 
		coordinates = new Coordinates();
		coordinates.setValue(value);
	}
	
	public Coordinates getCoordinates()
	{
		return coordinates;
	}

	@Override
    public boolean isAppropriateListener(FacesListener faceslistener) {
            return (faceslistener instanceof AjaxBehaviorListener);
    }

    @Override
    public void processListener(FacesListener faceslistener) {
            ((AjaxBehaviorListener) faceslistener).processAjaxBehavior(this);
    }

}
