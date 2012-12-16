package net.sf.geojsf.component;

import java.util.ArrayList;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="net.sf.geojsf.component.Map")
public class Map extends UINamingContainer{
	
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	
	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>> 
		String layerString(SERVICE service)
	{
		ArrayList<LAYER> layers = (ArrayList<LAYER>) service.getLayer();
		StringBuffer sb = new StringBuffer();
		for (LAYER layer : layers)
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
