package org.geojsf.util.layer;

import java.util.ArrayList;
import java.util.Hashtable;

import org.geojsf.util.layer.LayerUtil.Order;
import org.geojsf.util.layer.LayerUtil.Service;
import org.junit.Test;

public class TestToggleLayer {

	private Hashtable<String, Service> services = new Hashtable<String,Service>();
	private ArrayList<String> layers = new ArrayList<String>();
	
	@Test
	public void testMerge() {
		LayerUtil util = new LayerUtil();
		Service serviceA = util.new Service();
		serviceA.setServiceId("A");
		ArrayList<String> layers = new ArrayList<String>();
		layers.add("layer1");
		layers.add("layer2");
		serviceA.setLayer(layers);
		services.put(serviceA.getServiceId(), serviceA);
		util.setServices(services);
		Order order = util.toggleLayer(serviceA.getServiceId(), "layer2", false);
		System.out.println("order: " +order.getCommand() +" for " +order.getService().getLayer().toString());

	}

}
