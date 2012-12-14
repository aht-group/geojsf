package net.sf.geojsf.component;

import java.util.ArrayList;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="net.sf.geojsf.component.Map")
public class Map extends UINamingContainer{
	
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	
	public String layerString(DefaultGeoJsfService service)
	{
		ArrayList<DefaultGeoJsfLayer> layers = (ArrayList<DefaultGeoJsfLayer>) service.getLayer();
		StringBuffer sb = new StringBuffer();
		for (DefaultGeoJsfLayer layer : layers)
		{
			sb.append(layer.getCode() +",");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	public void listen(AjaxBehaviorEvent evt)
	{
		logger.error("Received event: " +evt.toString());
	}
	
	public void listenRedirect()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        MethodExpression ajaxEventListener = (MethodExpression) getAttributes().get("listener");
        ajaxEventListener.invoke(context.getELContext(), new Object[] {});
    }

}
