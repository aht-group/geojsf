package net.sf.geojsf.controller.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfMapLayerFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMapLayerFactory.class);
	
	private Map<String,SERVICE> mapService;
	private List<SERVICE> orderedServices;
	
	final Class<SERVICE> clService;
	
    public GeoJsfMapLayerFactory(final Class<SERVICE> clService)
    {
    	this.clService=clService;
    	mapService = new Hashtable<String,SERVICE>();
    	orderedServices = new ArrayList<SERVICE>();
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
    	GeoJsfMapLayerFactory<L,D,SERVICE,LAYER,VIEW,VL> factory(final Class<SERVICE> clService)
    {
        return new GeoJsfMapLayerFactory<L,D,SERVICE,LAYER,VIEW,VL>(clService);
    }
	
	public List<SERVICE> build(VIEW view)
	{
		return build(view.getLayer());
    }
	
	public List<SERVICE> build(List<VL> list)
	{
		List<LAYER> tmp = new ArrayList<LAYER>();
		for(VL vl : list)
		{
			tmp.add(vl.getLayer());
		}
		return buildFromLayer(tmp);
	}
	
	public List<SERVICE> buildFromLayer(List<LAYER> list)
	{
		mapService.clear();
		for(LAYER layer : list)
		{
			SERVICE service = getService(layer.getService());
			service.getLayer().add(layer);
		}
		
		List<SERVICE> services = new ArrayList<SERVICE>();
		for(SERVICE s : orderedServices)
		{
			services.add(mapService.get(s.getUrl()));
		}
		return services;
	}
	
	private SERVICE getService(SERVICE service)
	{
		if(!mapService.containsKey(service.getUrl()))
		{
			try
			{
				SERVICE s = clService.newInstance();
				s.setCode(service.getCode());
				s.setUrl(service.getUrl());
				mapService.put(service.getUrl(), s);
				orderedServices.add(s);
			}
			catch (InstantiationException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
			
		}
		return mapService.get(service.getUrl());
	}
}