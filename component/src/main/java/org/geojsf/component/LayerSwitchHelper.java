package org.geojsf.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


public class LayerSwitchHelper implements Serializable{
	
	private static final long serialVersionUID = -418623496825143747L;

	final static Logger logger = LoggerFactory.getLogger(LayerSwitchHelper.class);
	Gson gson = new Gson();
	
	Hashtable<String, Service> services;
	
	public LayerSwitchHelper(List list)
	{
		buildInternalListFromMapLayers(list);
	}
	
	public LayerSwitchHelper(Hashtable<String, Service> services)
	{
		this.services = services;
	}
	
	private void buildInternalListFromMapLayers(List<DefaultGeoJsfService> serviceList) {
		
		services = new Hashtable<String, Service>();
		Service service = new Service();
		logger.info("Service list of map has " +serviceList.size() +" services.");
		//Construct the simplified dedicated services list from the Map datamodel
		for (GeoJsfService geoService : serviceList)
		{
			logger.info("Processing Service " +geoService.getId());
			service = new Service();
    		service.setServiceId(geoService.getId() +"");
    		Hashtable<String, Boolean> layers = new Hashtable<String, Boolean>();
    		for (Object o : geoService.getLayer())
    		{
    			GeoJsfLayer geoLayer = (GeoJsfLayer) o;
    			layers.put(geoLayer.getId() +"", true);
    			logger.info("Processing Layer " +geoLayer.getId());
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

	public String toggleLayer(String serviceId, String layerId, Boolean active)
	{
		Order order     = new Order();
		String command  = null;
		
		Service service = services.get(serviceId);
		ArrayList<String> activeLayers = new ArrayList<String>();
		
		for (String layer : service.getLayer().keySet())
		{
			Boolean visible  = service.getLayer().get(layer);
			if (visible) {activeLayers.add(layer);}
		}
		
		//To illustrate the cases, the following code is not very optimized
		
		//If the only layer active is the one that should be hidden, hide the whole service
		if (activeLayers.size()==1 && !active)
		{command = "hide";activeLayers.remove(layerId);}
		
		//If there are other active layers, merge the newly constructed service
		if (activeLayers.size()>1 && !active)
		{command = "merge";activeLayers.remove(layerId);}
		
		//If the layer should be shown and the service is hidden right now and there is only one layer in the service, show it back
		if (active && activeLayers.isEmpty() && service.getLayer().size()==1)
		{command = "show";activeLayers.add(layerId);}
		
		//If there are layer shown in the service right now and the requested one should be added, merge the params
		if (active && !activeLayers.isEmpty())
		{command = "merge";activeLayers.add(layerId);}
		
		order.setCommand(command);
		order.setServiceId(serviceId);
		order.setLayer(activeLayers);
		return gson.toJson(order);
	}

	public Hashtable<String, Service> getServices() {return services;}
	public void setServices(Hashtable<String, Service> services) {this.services = services;}
	
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
