package org.geojsf.component;

import javax.el.ValueExpression;
import javax.faces.component.behavior.ClientBehaviorBase;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.context.FacesContext;

@FacesBehavior("org.geojsf.SwitchLayer")
public class SwitchLayer extends ClientBehaviorBase {
	
	public String layerId = "";
	
	

	public String getScript(ClientBehaviorContext context)
	{
		System.out.println("Testing Behavior");
		System.out.println("Switching Layer ID: " +layerId);
		System.out.println(context.getEventName() +" event has been fired in " +context.getFacesContext().getCurrentPhaseId());
		return new String("console.log('fired.');GeoJSF.switchLayer('" +layerId +"','" +context.getComponent().getClientId() +"');");
	}
	
	public void decode(FacesContext context)
	{
		System.out.println("decoding.");
	}
	
	public String getLayerId() {return layerId;}
	public void setLayerId(String layerId) {this.layerId = layerId;}
}
