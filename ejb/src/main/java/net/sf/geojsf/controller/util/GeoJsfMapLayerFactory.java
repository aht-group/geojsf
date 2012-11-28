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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfMapLayerFactory<L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfMapLayerFactory.class);
	
	private Map<String,SERVICE> mapService;
	
	final Class<SERVICE> clService;
	
    public GeoJsfMapLayerFactory(final Class<SERVICE> clService)
    {
    	this.clService=clService;
    	mapService = new Hashtable<String,SERVICE>();
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
    	GeoJsfMapLayerFactory<L,D,LAYER,VIEW,SERVICE> factory(final Class<SERVICE> clService)
    {
        return new GeoJsfMapLayerFactory<L,D,LAYER,VIEW,SERVICE>(clService);
    }
	
	public List<SERVICE> build(VIEW view)
	{
		return build(view.getLayer());
    }
	
	public List<SERVICE> build(List<LAYER> list)
	{
		mapService.clear();
		for(LAYER layer : list)
		{
			SERVICE service = getService(layer.getService());
			service.getLayer().add(layer);
		}
		
		List<SERVICE> services = new ArrayList<SERVICE>();
		services.addAll(mapService.values());
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
			}
			catch (InstantiationException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
			
		}
		return mapService.get(service.getUrl());
	}
}