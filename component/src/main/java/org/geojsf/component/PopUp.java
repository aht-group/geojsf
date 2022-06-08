package org.geojsf.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.util.GeoJsfJsLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.PopUp")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class PopUp extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Graticule.class);

	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{

		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"control.js");
		}
		/*
		UIComponent parent = event.getComponent().getParent();
		if(Objects.isNull(searchUIComponentByName(parent,"popup")))
		{
			 HtmlPanelGroup popUpDiv = new HtmlPanelGroup();
			 popUpDiv.setLayout("block");
			 popUpDiv.setId("popup");
			 popUpDiv.setStyleClass("ol-popup");

			 HtmlPanelGroup popUpContentDiv = new HtmlPanelGroup();
			 popUpContentDiv.setLayout("block");
			 popUpContentDiv.setId("popup-content");
			 popUpContentDiv.setStyleClass("ol-popup");

			 popUpDiv.getChildren().add(popUpContentDiv);
			 parent.getChildren().add(popUpDiv);
		}
		*/
		super.processEvent(event);
	}

	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		logger.info("Adding PopUp control.");
	    String jsCall = "";
	    logger.info(jsCall);


		ResponseWriter writer = ctx.getResponseWriter();
		if (this.getParent() instanceof Map)
		{
			JsfRenderUtil renderer = new JsfRenderUtil(writer, this.getParent());
			renderer.renderLinebreaks(2);

			//First, render the JavaScript code to initialize the popup
			writer.startElement("script", this);
			renderer.renderPopUpInitialization(this.getFacesContext());
			writer.endElement("script");
		}
		else
		{
			logger.info("Adding PopUp control. --> not possible---> because parent element is not a map");
		}
	}
	/*
	// --------------------------
	// JSF Decode Phase method
	// --------------------------

	@Override
	public void decode(FacesContext context)
	{
		logger.info("Current Phase: " +context.getCurrentPhaseId().toString());

		if (this.isRendered())
		{
			java.util.Map<String,String> params = context.getExternalContext().getRequestParameterMap();
			String behaviorEvent = params.get("javax.faces.behavior.event");
		    logger.info("Handling event of type: " +behaviorEvent +" in decode phase.");

		    // For better reading of the following, some Booleans are defined here

		    Boolean isFeatureSelected = false;


		    if (null!=behaviorEvent)
		    {
		    	isFeatureSelected = behaviorEvent.equals("featureSelected");
		    }

			// Handling of featureSelected event fired by JavaScript API
	        if (null!= behaviorEvent && isFeatureSelected)
	        {
	        	java.util.Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
	     		if (behaviors.isEmpty())
	     		{
	     			logger.info("no behaviors.exiting.");
	     			return;
	     		}
	            List<ClientBehavior> behaviorsForEvent = behaviors.get(behaviorEvent);
	            if (behaviors.size() > 0)
	            {
	            	String behaviorSource = params.get("javax.faces.source");
	            	String clientId = getClientId(context);
	            	if (behaviorSource != null && null!= behaviorsForEvent && behaviorSource.equals(clientId))
	            	{
	            		for (ClientBehavior behavior: behaviorsForEvent)
	            		{
	            			logger.info("Found " +behavior.getClass().toString());
	            			AjaxBehaviorEvent ajaxEvent = new AjaxBehaviorEvent(this, behavior);


	            			behavior.broadcast(ajaxEvent);
	            		}
	            	}
	            }
	        }
		}

	}
*/
	@Override
	public Collection<String> getEventNames()
	{
	    ArrayList<String> events = new ArrayList<String>();
		events.add("featureSelected");
	    return events;
	}

	@Override
	public String getDefaultEventName() {return "";}

	/*private UIComponent searchUIComponentByName(UIComponent parent, String name)
	{
		UIComponent foundChild = null;

		if(Objects.nonNull(parent))
		{
			for(UIComponent component: parent.getChildren())
			{
			    if(component.getId().contains(name))
			    {
			    	foundChild = component;
			        break;
			    }
			}
		}

		return foundChild;

	}
	*/

}