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
					org.primefaces.component.behavior.ajax.AjaxBehavior ajax = (org.primefaces.component.behavior.ajax.AjaxBehavior) cl;
					updateOnClick.append(ajax.getUpdate().replaceAll(":", ""));
					logger.info("Adding " +updateOnClick.toString() +" to " +key +" event.");
				}
			}
		}
		return updateOnClick.toString();
	}
	
	public void renderMapInitialization(FacesContext context) throws IOException
	{
		renderLinebreaks(1);
		Map map = (Map) component;
		writer.startElement("script", map);
		renderTextWithLB("jsf.ajax.addOnEvent(GeoJSF.ajaxResponse);");
		renderTextWithLB("// GeoJSF: Initializing OpenLayers map");
		renderTextWithLB("GeoJSF.bootstrap();");
		renderTextWithLB("GeoJSF.addClickHandler('" +map.getClientId() +"','" +map.getClientId() +":resetLayers','" +encodeAjax(map) +"');");
		renderTextWithLB("GeoJSF.initMap('" +map.getClientId() +"','');");
		
		if (context.getExternalContext().getInitParameter("geojsf.THEME")!=null)
		{
			renderTextWithLB("OpenLayers.ImgPath='" +context.getExternalContext().getRequestContextPath() +"/" +context.getExternalContext().getInitParameter("geojsf.THEME") +"/';");
		}
	    
		writer.writeText("GeoJSF.resetLayers();", null);
		renderLinebreaks(2);
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

}
