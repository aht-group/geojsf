package org.geojsf.util.layer;

import java.util.ArrayList;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LayerUtil {
	
	final static Logger logger = LoggerFactory.getLogger(LayerUtil.class);
	private Hashtable<String, Service> services = new Hashtable<String,Service>();
	
	public class Service
	{
		private String serviceId;
		private ArrayList<String> layer = new ArrayList<String>();
		private Integer noOfLayers = 0;
		
		public String getServiceId() {
			return serviceId;
		}
		public ArrayList<String> getLayer() {
			return layer;
		}
		public Integer getNoOfLayers() {
			return layer.size();
		}
		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}
		public void setLayer(ArrayList<String> layer) {
			this.layer = layer;
		}
		public void setNoOfLayers(Integer noOfLayers) {
			this.noOfLayers = noOfLayers;
		}
	}

	public class Order
	{
		private Service service;
		private String command;

		public Service getService() {
			return service;
		}
		public String getCommand() {
			return command;
		}
		public void setService(Service service) {
			this.service = service;
		}
		public void setCommand(String command) {
			this.command = command;
		}
		
	}
	
	public Order toggleLayer(String serviceId, String layerId, Boolean active)
	{
		Order order = new Order();
		Service service = new Service();
		String command = null;
		if (!services.containsKey(serviceId) && active)
		{
			service.setServiceId(serviceId);
			ArrayList<String> layers = new ArrayList<String>();
			layers.add(layerId);
			service.setLayer(layers);
			services.put(serviceId, service);
			command = "show";
		}
		else if (services.containsKey(serviceId) && active)
		{
			service = services.get(serviceId);
			service.layer.add(layerId);
			services.get(serviceId).getLayer().add(layerId);
			command = "merge";
		}
		if (!active)
		{
			service = services.get(serviceId);
			logger.info("Number of layers:" +service.noOfLayers);
			if (service.noOfLayers==1)
			{
				command = "hide";
			//	services.remove(serviceId);
			}
			else
			{
				service.layer.remove(layerId);
				services.get(serviceId).getLayer().remove(layerId);
				command = "merge";
			}
		}
		order.setCommand(command);
		order.setService(service);
		return order;
	}

	public Hashtable<String, Service> getServices() {
		return services;
	}

	public void setServices(Hashtable<String, Service> services) {
		this.services = services;
	}

}
