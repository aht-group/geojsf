package org.geojsf.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


public class LayerSwitchHelper<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>,LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>> implements Serializable{
	
	private static final long serialVersionUID = -418623496825143747L;

	final static Logger logger = LoggerFactory.getLogger(LayerSwitchHelper.class);
	Gson gson = new Gson();
	
	Hashtable<String, Service> services;
	Hashtable<String, String>  layerNames;
	

	public LayerSwitchHelper(List list)
	{
		buildInternalListFromMapLayers(list);
	}
	
	public LayerSwitchHelper(Hashtable<String, Service> services, Hashtable<String, String> layerNames)
	{
		this.services   = services;
		this.layerNames = layerNames;
	}
	
	private void buildInternalListFromMapLayers(List<SERVICE> serviceList) {
		
		services        = new Hashtable<String, Service>();
		layerNames      = new Hashtable<String, String>();
		Service service = new Service();
		
		logger.info("Service list of map has " +serviceList.size() +" services.");
		//Construct the simplified dedicated services list from the Map datamodel
		for (SERVICE geoService : serviceList)
		{
			logger.info("Processing Service " +geoService.getId());
			service = new Service();
    		service.setServiceId(geoService.getId() +"");
    		Hashtable<String, Boolean> layers = new Hashtable<String, Boolean>();
    		for (Object o : geoService.getLayer())
    		{
    			GeoJsfLayer geoLayer = (GeoJsfLayer) o;
    			layers.put(geoLayer.getId() +"", true);
    			layerNames.put(geoLayer.getId() +"", geoLayer.getCode());
    			logger.info("Processing Layer " +geoLayer.getCode() +" with ID "+geoLayer.getId());
    		}
    		service.setLayer(layers);
    		services.put(service.getServiceId(), service);
		}
	}

	public class Service implements Serializable
	{
		private static final long serialVersionUID = 6027640604576855791L;
		
		private String serviceId;
		private Hashtable<String, Boolean> layer = new Hashtable<String, Boolean>();
		
		public void setServiceId(String serviceId) {this.serviceId = serviceId;}
		public String getServiceId() {return serviceId;}
		public Hashtable<String, Boolean> getLayer() {return layer;}
		public void setLayer(Hashtable<String, Boolean> layer) {this.layer = layer;}
	}

	private class Order implements Serializable
	{
		private static final long serialVersionUID = 4707371203319566564L;
		
		private String serviceId;
		private ArrayList<String> layer;
		private String command;
	
		public String getCommand() {return command;}
		public void setCommand(String command) {this.command = command;}
		public String getServiceId() {return serviceId;}
		public void setServiceId(String serviceId) {this.serviceId = serviceId;}
		public ArrayList<String> getLayer() {return layer;}
		public void setLayer(ArrayList<String> layer) {this.layer = layer;}
	}


	public String toggleLayer(String layerId)
	{
		String serviceId = getServiceFor(layerId);
		Service service  = services.get(serviceId);
		Boolean visible  = service.getLayer().get(layerId);
		return toggleLayer(serviceId, layerId, !visible);
	}
	
	public String getServiceFor(String layerId)
	{
		String serviceForLayer = "notdefined";
		for (String serviceId : services.keySet())
		{
			Service service = services.get(serviceId);
			if (service.layer.keySet().contains(layerId))
			{
				serviceForLayer = serviceId;
			}
		}
		return serviceForLayer;
	}
	
	public String toggleLayer(String serviceId, String layerId, Boolean active)
	{
		Order order     = new Order();
		String command  = null;
		Service service = services.get(serviceId);
		
		// The following code is building the list of layers that are to be shown after the command is executed
		// First, set the requested layer to the requested status
		service.getLayer().put(layerId, active);
		
		// Then process the complete list of layers assigned to the requested layers service
		ArrayList<String> activeLayers = new ArrayList<String>();
		for (String layer : service.getLayer().keySet())
		{
			Boolean visible  = service.getLayer().get(layer);
			if (visible) {activeLayers.add(layerNames.get(layer));}
		}
	
		// Use MERGE command as standard
		command = "merge";
		
		// Now let's see if there is another option that is more appropriate
		//If the only layer active is the one that should be hidden, hide the whole service
		if (activeLayers.size()==0 && !active && service.getLayer().size()==1)
		{command = "hide";}
		
		//If the layer should be shown and the service is hidden right now and there is only one layer in the service, show it back
		if (active && activeLayers.size()==1 && service.getLayer().size()==1)
		{command = "show";}

		//This is obsolete since it is covered indirectly by the code above
		//If there are other active layers, merge the newly constructed service
		/*		if (activeLayers.size()>1 && !active && service.getLayer().size()>1)
				{command = "merge";}
				
				//If the layer should be shown and the service is hidden right now and there is only one layer in the service, show it back
				if (active && activeLayers.isEmpty() && service.getLayer().size()>1)
				{command = "merge";}
		*/
		
		order.setCommand(command);
		order.setServiceId(serviceId);
		order.setLayer(activeLayers);
		return gson.toJson(order);
	}

	public Hashtable<String, Service> getServices() {return services;}
	public void setServices(Hashtable<String, Service> services) {this.services = services;}
	
	public Hashtable<String, String> getLayerNames() {return layerNames;}
	public void setLayerNames(Hashtable<String, String> layerNames) {this.layerNames = layerNames;}

	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		for (String serviceId : services.keySet())
		{
			buffer.append(serviceId +" has the following layer: ");
			Service service = services.get(serviceId);
			for (String layer : service.getLayer().keySet())
			{
				buffer.append(layer +"(" +service.getLayer().get(layer) +")");
			}
			buffer.append("---");
		}
		return buffer.toString();
	}
}
