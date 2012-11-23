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
	
    public GeoJsfMapLayerFactory()
    {
    	mapService = new Hashtable<String,SERVICE>();
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
    	GeoJsfMapLayerFactory<L,D,LAYER,VIEW,SERVICE> factory()
    {
        return new GeoJsfMapLayerFactory<L,D,LAYER,VIEW,SERVICE>();
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
		if(!mapService.containsKey(service.getCode())){mapService.put(service.getCode(), service);}
		return mapService.get(service.getCode());
	}
}