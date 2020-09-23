package org.geojsf.component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsfRenderUtil {
	
	final static Logger logger = LoggerFactory.getLogger(JsfRenderUtil.class);
	
	private ResponseWriter writer;
	private UIComponent component;
	
	public JsfRenderUtil(ResponseWriter componentWriter, UIComponent caller){
		this.writer = componentWriter;
		this.component = caller;
	}

	public void renderLinebreaks( Integer amount)
	{
		for (int i=0; i<amount; i++)
		{
			try {
				writer.writeText(System.getProperty("line.separator"), null);
			} catch (IOException e) {
				logger.warn("Could not access writer to render line break.");
			}
		}
	}
	
	public void renderDiv(String id, String style)
	{
		try {
			writer.startElement("div", component);
			writer.writeAttribute("id", id, null);
			writer.writeAttribute("style", style, null);
			writer.endElement("div");
		} catch (IOException e) {
			logger.warn("Could not render div!");
		}
		
	}
	
	@Deprecated
	public static String encodeAjax(ClientBehaviorHolder comp)
	{
		StringBuffer updateOnClick = new StringBuffer();
		java.util.Map<String, List<ClientBehavior>> behaviors = comp.getClientBehaviors();
		for (String key : behaviors.keySet())
		{
			List<ClientBehavior> c = behaviors.get(key);
			for (ClientBehavior cl : c)
			{
				logger.info("Detected " +cl.toString() +" added as ClientBehavior");
				if (cl.getClass().getName().equals("javax.faces.component.behavior.AjaxBehavior"))
				{
					AjaxBehavior ajax = (AjaxBehavior) cl;
					Collection<String> renderList = ajax.getRender();
					for (String str : renderList)
					{
						str = str.replaceAll(":", "");
						updateOnClick.append(str +" ");
					}
					logger.info("Adding " +updateOnClick.toString() +" to " +key +" event.");
				}
				if (cl.getClass().getName().equals("org.primefaces.component.behavior.ajax.AjaxBehavior"))
				{
					org.primefaces.behavior.ajax.AjaxBehavior ajax = (org.primefaces.behavior.ajax.AjaxBehavior) cl;
					updateOnClick.append(ajax.getUpdate().replaceAll(":", ""));
					logger.info("Adding " +updateOnClick.toString() +" to " +key +" event.");
				}
			}
		}
		return updateOnClick.toString();
	}
	
	public static String encodeAjax(ClientBehaviorHolder comp, String eventName)
	{
		StringBuffer updateOnClick = new StringBuffer();
		java.util.Map<String, List<ClientBehavior>> behaviors = comp.getClientBehaviors();
		for (String key : behaviors.keySet())
		{
			List<ClientBehavior> c = behaviors.get(key);
			for (ClientBehavior cl : c)
			{
				if (key.equals(eventName))
				{
					logger.info("Detected " +cl.toString() +" added as ClientBehavior");
					if (cl.getClass().getName().equals("javax.faces.component.behavior.AjaxBehavior"))
					{
						AjaxBehavior ajax = (AjaxBehavior) cl;
						if (null!=ajax.getRender())
						{
							Collection<String> renderList = ajax.getRender();
							for (String str : renderList)
							{
								str = str.replaceAll(":", "");
								updateOnClick.append(str +" ");
							}
							logger.info("Adding " +updateOnClick.toString() +" to " +key +" event.");
						}
					}
					if (cl.getClass().getName().equals("org.primefaces.behavior.ajax.AjaxBehavior"))
					{
						org.primefaces.behavior.ajax.AjaxBehavior ajax = (org.primefaces.behavior.ajax.AjaxBehavior) cl;
						if (null!=ajax.getUpdate())
						{
							updateOnClick.append(ajax.getUpdate().replaceAll(":", ""));
							logger.info("Adding " +updateOnClick.toString() +" to " +key +" event.");
						}
					}
				}
			}
		}
		return updateOnClick.toString();
	}
	
	public void renderMapInitialization(FacesContext context) throws IOException
	{
		renderLinebreaks(1);
		if (component instanceof Map)
		{
		    Map map = (Map) component;
		    writer.startElement("script", map);
		    renderTextWithLB("// GeoJSF: Initializing OpenLayers map");
		    renderTextWithLB("GeoJSF.setAjaxUpdates('" +encodeAjax(map, "mapClick") +"', '" +encodeAjax(map, "mapMove") +"');");
		    renderTextWithLB("GeoJSF.initMap('" +map.getClientId() +"','" +map.getBaseMap() +"');");
		    renderLinebreaks(2);
		    String srcUrl = context.getExternalContext().getRequestScheme() +"://" +context.getExternalContext().getRequestServerName() +":" +context.getExternalContext().getRequestServerPort() +context.getExternalContext().getRequestContextPath() +"/javax.faces.resource/30px-Location-marker.png.jsf?ln=geojsf";

		    if (map.getMarkerUrl()!=null)
		    {
			    srcUrl = map.getMarkerUrl();
			    renderTextWithLB("GeoJSF.setMarkerUrl('" +srcUrl+"');");
			    renderLinebreaks(2);
		    }
		    else
		    {
			    logger.info("Using standard marker Icon at " +srcUrl);
			    renderTextWithLB("GeoJSF.setMarkerUrl('" +srcUrl+"');");
			    renderLinebreaks(2);
		    }

		    if (map.getMarkerPosition()!=null)
		    {
			    renderTextWithLB("GeoJSF.setMarkerPosition(" +map.getMarkerPosition().getLat() +"," +map.getMarkerPosition().getLon() +");");
			    renderTextWithLB("GeoJSF.initMarker(" +map.getMarkerPosition().getLat() +"," +map.getMarkerPosition().getLon() +",'" +srcUrl+"');");
			    renderLinebreaks(2);
		    }
		}
		
		if (component instanceof CoordinatePicker)
		{
		    CoordinatePicker map = (CoordinatePicker) component;
		    writer.startElement("script", map);
		    renderTextWithLB("// GeoJSF: Initializing OpenLayers map");
		    renderTextWithLB("GeoJSF.setAjaxUpdates('" +encodeAjax(map, "mapClick") +"', '" +encodeAjax(map, "mapMove") +"');");
		    renderTextWithLB("GeoJSF.initMap('" +map.getClientId() +"','" +map.getBaseMap() +"');");
		    renderLinebreaks(2);
		    String srcUrl = context.getExternalContext().getRequestScheme() +"://" +context.getExternalContext().getRequestServerName() +":" +context.getExternalContext().getRequestServerPort() +context.getExternalContext().getRequestContextPath() +"/javax.faces.resource/30px-Location-marker.png.jsf?ln=geojsf";

		    if (map.getMarkerUrl()!=null)
		    {
			    srcUrl = map.getMarkerUrl();
			    renderTextWithLB("GeoJSF.setMarkerUrl('" +srcUrl+"');");
			    renderLinebreaks(2);
		    }
		    else
		    {
			    logger.info("Using standard marker Icon at " +srcUrl);
			    renderTextWithLB("GeoJSF.setMarkerUrl('" +srcUrl+"');");
			    renderLinebreaks(2);
		    }

		    if (map.getMarkerPosition()!=null)
		    {
			    renderTextWithLB("GeoJSF.setMarkerPosition(" +map.getMarkerPosition().getLat() +"," +map.getMarkerPosition().getLon() +");");
			    renderTextWithLB("GeoJSF.initMarker(" +map.getMarkerPosition().getLat() +"," +map.getMarkerPosition().getLon() +",'" +srcUrl+"');");
			    renderLinebreaks(2);
		    }
		}
		
		
		renderTextWithLB("// GeoJSF: Adding layers");
		renderTextWithLB("// GeoJSF: The last given layer will be taken as base layer:");
		renderLinebreaks(1);
	}
	
	public void renderTextWithLB(String text)
	{
		try {
			writer.writeText(text +System.getProperty("line.separator"), null);
		} catch (IOException e) {
			logger.info("Problem rendering " +text +" using JsfRenderUtil");
		}
	}
	
	public void renderScript(String command)
	{
	    renderTextWithLB("<script>" +command +"</script>");
	}
	
	public void renderTagStart(String tagName)
	{
	    renderTextWithLB("<" +tagName +">");
	}
	
	public void renderTagEnd(String tagName)
	{
	    renderTextWithLB("</" +tagName +">");
	}
	
	public void renderPlayerInitialization(ResponseWriter writer)
	{
		
	}

}
