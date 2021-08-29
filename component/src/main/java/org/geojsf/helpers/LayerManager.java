package org.geojsf.helpers;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import javax.faces.context.ResponseWriter;
import org.geojsf.component.JsfRenderUtil;
import org.geojsf.component.Order;
import org.geojsf.component.entities.OlLayer;
import org.geojsf.component.entities.OlService;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LayerManager
{
    
    final static Logger logger = LoggerFactory.getLogger(LayerManager.class);
	    
    private ArrayList<Long> orderedLayers = new ArrayList<Long>();
    private Hashtable<Long, String>    layerNames;
    Hashtable<Long, OlService> serviceList;
    Gson gson = new Gson();
	
	
    public void initLayers(GeoJsfMap dmMap)
    {
	layerNames      = new Hashtable<Long, String>();
	
	// First - build a list of OpenLayers Services from the list of VIEWs. These are used to render the map layers initially
	serviceList = new Hashtable<Long, OlService>();

	// So collect all information from VIEWs
	Hashtable<String, OlService> serviceForUrl = new Hashtable<String, OlService>();
	for (Object o : dmMap.getViews())
	{
		GeoJsfView view		= (GeoJsfView)o;
		GeoJsfLayer   viewLayer   = view.getLayer();
		GeoJsfService viewService = view.getLayer().getService();

		OlLayer   layer   = new OlLayer();
		layer.setId(viewLayer.getId());
		layer.setName(viewLayer.getCode());
		layer.setVisible(view.isVisible());

		if (!serviceForUrl.containsKey(viewService.getWms()))
		{
			OlService service = new OlService();
			service.setUrl(viewService.getWms());
			service.setId(viewService.getId());
			service.setLayerVisibility(new LinkedHashMap<Long, Boolean>());
			service.getLayerVisibility().put(viewLayer.getId(), view.isVisible());
			layerNames.put(viewLayer.getId(), viewLayer.getCode());
			serviceForUrl.put(viewService.getWms(), service);
			orderedLayers.add(service.getId());
			logger.debug("Added layer " +viewLayer.getCode() +" to be " +view.isVisible());
		}
		else
		{
			OlService service = serviceForUrl.get(viewService.getWms());
			layerNames.put(viewLayer.getId(), viewLayer.getCode());
			service.getLayerVisibility().put(viewLayer.getId(), view.isVisible());
		//	orderedLayers.add(service.getId());
			logger.debug("Added layer " +viewLayer.getCode() +" to be " +view.isVisible() +" to existing Service.");
		}
	}


	// And add all constructed OpenLayers Services to the internal serviceList
	for (String url : serviceForUrl.keySet())
	{
		OlService service = serviceForUrl.get(url);
		serviceList.put(service.getId(), service);
	}

	logger.debug("Transfered " +serviceList.size() +" services from " +serviceForUrl.size() +" and the size of orderedLayers is " +orderedLayers.size());
						
    }
    
    public void renderLayers(ResponseWriter writer, JsfRenderUtil renderer, Boolean addScriptTag) throws IOException
	{
		if(addScriptTag){renderer.renderTagStart("Script");}
		
		//First, render the base layer (adding overlays first results in error)
		Long baseLayerId = orderedLayers.get(orderedLayers.size()-1);
		if (orderedLayers.size()==1)
		{
			encodeOlService(serviceList.get(baseLayerId), true, true, writer, renderer);
		}
		else
		{
			encodeOlService(serviceList.get(baseLayerId), true, false, writer, renderer);
		}
		renderer.renderTextWithLB("GeoJSF.registerEventHandlers();");
                
		
		//Finally, render the overlay layers
		//for (int i=0;i<serviceList.size()-1;i++)
 		for(int i=orderedLayers.size()-2;i==0;i--) //GEO-64
		{	
 			OlService service = serviceList.get(orderedLayers.get(i));
 			if (i==0)
 			{
 				encodeOlService(service, false, true, writer, renderer);
 			}
 			else
 			{
 				encodeOlService(service, false, false, writer, renderer);
 			}
 			  
		}
 		
 		if(addScriptTag){renderer.renderTagEnd("script");}
	}
    
    public String buildLayerString(OlService service)
    {
	StringBuffer sb = new StringBuffer();
	List<Long> list = new ArrayList<Long>();
	list.addAll(service.getLayerVisibility().keySet());
	Collections.reverse(list);
	for (Long i : list)
	{
		if (service.getLayerVisibility().get(i))
		{
			sb.append(layerNames.get(i) +",");
		}
	}
	if (sb.length()>0)
	{
		sb.deleteCharAt(sb.length()-1);
	}
	return sb.toString();
    }
	
	public void encodeOlService(OlService service, Boolean baseLayer, Boolean lastLayer, ResponseWriter writer, JsfRenderUtil renderer) throws IOException
	{
		String sLayers = buildLayerString(service);
		logger.debug("Adding to service " +service.getId() +": "+sLayers);
		renderer.renderTextWithLB("var url            = '" +service.getUrl() +"';");
		renderer.renderTextWithLB("var name           = '" +service.getId() +"';");
		renderer.renderTextWithLB("var params         = {};");
		renderer.renderTextWithLB("var options        = {};");
		renderer.renderTextWithLB("params.layers      = '"+sLayers+"';");
		
	/*	ArrayList<String> localTemporalLayer = MapUtil.hasTemporalLayer(service);
		if (localTemporalLayer.size()>0 && null!=timeInfo)
		{
			logger.info("Found time layers in Service " +service.getId() +": " +localTemporalLayer.toString());
		//	temporalLayerNames.addAll(localTemporalLayer);
			temporalLayerNames.add(service.getId() +"");
			renderer.renderTextWithLB("params.time      = '"+timeInfo +"';");
		}
	*/	
		
		//	renderer.renderTextWithLB(MapUtil.renderSqlViewParameters(map));
		renderer.renderTextWithLB("GeoJSF.addLayer(name, url, params, options);");
		renderer.renderLinebreaks(1);
		
		
		
		
		
		
		
	}
		
    public String toggleLayer(Long serviceId, Long layerId, Boolean active)
	{
		Order order       = new Order();
		String command    = null;
		OlService service = serviceList.get(serviceId);
		
		// The following code is building the list of layers that are to be shown after the command is executed
		// First, set the requested layer to the requested status
		service.setVisibility(layerId, active);
		
		// Then process the complete list of layers assigned to the requested layers service
		ArrayList<String> activeLayers = new ArrayList<String>();
		for (Long layerOfService : service.getLayerVisibility().keySet())
		{
			Boolean visible  = service.isLayerVisible(layerOfService);
			if (visible) {activeLayers.add(layerNames.get(layerOfService));}
		}
	
		// Use MERGE command as standard
		command = "merge";
		
		// Now let's see if there is another option that is more appropriate
		//If the only layer active is the one that should be hidden, hide the whole service
		if (activeLayers.size()==0 && !active && service.getLayerVisibility().size()==1)
		{command = "hide";}
		
		//If the layer should be shown and the service is hidden right now and there is only one layer in the service, show it back
		if (active && activeLayers.size()==1 && service.getLayerVisibility().size()==1)
		{command = "show";}

		//This is obsolete since it is covered indirectly by the code above
		//If there are other active layers, merge the newly constructed service
		/*		if (activeLayers.size()>1 && !active && service.getLayer().size()>1)
				{command = "merge";}
				
				//If the layer should be shown and the service is hidden right now and there is only one layer in the service, show it back
				if (active && activeLayers.isEmpty() && service.getLayer().size()>1)
				{command = "merge";}
		*/
		
		// To have the correct layer allignement for OpenLayers, reverse the list order
		Collections.reverse(activeLayers);
		
		// Create the layer switch command for JavaScript GeoJSF logic
		order.setCommand(command);
		order.setServiceId(serviceId);
		order.setLayer(activeLayers);
		return gson.toJson(order);
	}
    
    public Hashtable<Long,OlService> getServiceList() {return serviceList;}
    public void setServiceList(Hashtable<Long,OlService> serviceList) {this.serviceList = serviceList;}

    public ArrayList<Long> getOrderedLayers() {return orderedLayers;}
    public void setOrderedLayers(ArrayList<Long> orderedLayers) {this.orderedLayers = orderedLayers;}

    public Hashtable<Long, String> getLayerNames() {return layerNames;}
    public void setLayerNames(Hashtable<Long, String> layerNames) {this.layerNames = layerNames;}
    
    

}
