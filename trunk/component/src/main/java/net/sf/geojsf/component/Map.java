package net.sf.geojsf.component;

import java.util.ArrayList;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfService;

import org.geojsf.controller.util.GeoJsfMap;
import org.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="net.sf.geojsf.component.Map")
public class Map extends UINamingContainer{
	
	final static Logger logger = LoggerFactory.getLogger(Map.class);
	private ArrayList<DefaultGeoJsfService> serviceList;
	
	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>,VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>> 
		String layerString(SERVICE service)
	{
		List<LAYER> layers = service.getLayer();
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
	
	public String fallback()
	{
		
		 serviceList = new ArrayList<DefaultGeoJsfService>();
		 logger.info("Checking value existence ...");
		 if (getAttributes().get("value")==null)
		 {
			 logger.info("No value given - falling back to simple version");
			 String url = (String) getAttributes().get("simpleWmsUrl");
			 DefaultGeoJsfService service = new DefaultGeoJsfService();
			 service.setUrl(url);
			 service.setCode("BaseLayer");
			 ArrayList<String> layers = new ArrayList<String>();
			 Object layerList = getAttributes().get("simpleLayers");
			 logger.info("Detecting layer definition type..." +layerList.getClass().getSimpleName());
			 if (layerList.getClass().getSimpleName().equals("String"))
			 {
				 logger.info("Detected layer list given as Strings");
				 String layerString = (String)layerList;
				 for (String string : layerString.split(","))
				 {
					 DefaultGeoJsfLayer layer = new DefaultGeoJsfLayer();
					 layer.setCode(string);
					 service.getLayer().add(layer);
				 }
			 }
			 ArrayList<DefaultGeoJsfService> singleValue = new ArrayList<DefaultGeoJsfService>();
			 singleValue.add(service);
			 getAttributes().put("value", singleValue);
			 serviceList.add(service);
		 }
		 else
		 {
			 GeoJsfMap map = (GeoJsfMap) getAttributes().get("value");
			 serviceList = (ArrayList<DefaultGeoJsfService>) map.getLayerServices();
		 }
		return new String();
	}
	
	public void listenRedirect()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        MethodExpression ajaxEventListener = (MethodExpression) getAttributes().get("listener");
        ajaxEventListener.invoke(context.getELContext(), new Object[] {});
    }

	public ArrayList<DefaultGeoJsfService> getServiceList() {
		return serviceList;
	}

	public void setServiceList(ArrayList<DefaultGeoJsfService> serviceList) {
		this.serviceList = serviceList;
	}

}
