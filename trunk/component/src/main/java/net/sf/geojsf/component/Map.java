package net.sf.geojsf.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfService;

@FacesComponent(value="net.sf.geojsf.component.Map")
public class Map extends UINamingContainer{
	
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	
	public String layerString(DefaultGeoJsfService service)
	{
		ArrayList<DefaultGeoJsfLayer> layers = (ArrayList<DefaultGeoJsfLayer>) service.getLayer();
		StringBuffer sb = new StringBuffer();
		Boolean firstLayer = true;
		for (DefaultGeoJsfLayer layer : layers)
		{
			if (firstLayer)  {sb.append(layer.getCode()); firstLayer = false;}
			if (!firstLayer) {sb.append("," +layer.getCode());}
		}
		return sb.toString();
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
