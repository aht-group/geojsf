package org.geojsf.controller.util;

import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.EjbGeoMapFactory;
import org.geojsf.factory.ejb.EjbGeoViewFactory;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;

public class DummyViewFactory
{
	private EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fService;
	private EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fLayer;
	private EjbGeoMapFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fMap;
	private EjbGeoViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fView;
	
	public static DefaultGeoJsfService serviceOsm,serviceAht;
	private DefaultGeoJsfLayer layerOsmBasic,layerAhtRoads,layerAhtStreams,layerAhtRestricted;
	private DefaultGeoJsfMap map;
	
	public DefaultGeoJsfMap getMap() {return map;}

	public DummyViewFactory() throws UtilsIntegrityException
	{
		fService = EjbGeoServiceFactory.factory(DefaultGeoJsfService.class);
		fLayer = EjbGeoLayerFactory.factory(DefaultGeoJsfLang.class,DefaultGeoJsfLayer.class);
		fMap = EjbGeoMapFactory.factory(DefaultGeoJsfLang.class,DefaultGeoJsfMap.class);
		fView = EjbGeoViewFactory.factory(DefaultGeoJsfView.class);
		
		initServices();
		initLayer();
		initViews();
	}
	
	public static DefaultGeoJsfMap build()
	{
		DummyViewFactory f = null;
		try {f = new DummyViewFactory();}
		catch (UtilsIntegrityException e) {e.printStackTrace();}
		return f.getMap();
	}
	
	public static DefaultGeoJsfView buildView()
	{
		DummyViewFactory f = null;
		try {f = new DummyViewFactory();}
		catch (UtilsIntegrityException e) {e.printStackTrace();}
		return new DefaultGeoJsfView();
	}
	
	private void initServices() throws UtilsIntegrityException
	{
		if(serviceOsm==null){serviceOsm = fService.build("osm","http://vmap0.tiles.osgeo.org/wms/vmap0");}
		if(serviceAht==null){serviceAht = fService.build("aht","https://www.aht-group.com/geoserver/sf/wms");}
	}
	
	private void initLayer() throws UtilsIntegrityException
	{
		layerOsmBasic = fLayer.build("basic", serviceOsm,DefaultGeoJsfLang.defaultLangs);layerOsmBasic.setId(1);
		layerAhtRoads = fLayer.build("roads",serviceAht,DefaultGeoJsfLang.defaultLangs);layerAhtRoads.setId(2);
		layerAhtStreams = fLayer.build("streams",serviceAht,DefaultGeoJsfLang.defaultLangs);layerAhtStreams.setId(3);
		layerAhtRestricted = fLayer.build("restricted",serviceAht,DefaultGeoJsfLang.defaultLangs);layerAhtRestricted.setId(4);
	}
	
	private void initViews() throws UtilsIntegrityException
	{
		map = fMap.create("defaultMap",5,0,0,DefaultGeoJsfLang.defaultLangs);map.setId(1);
		map.getViews().add(fView.create(map, layerAhtRoads, 1, true,true));
		map.getViews().add(fView.create(map, layerAhtStreams, 2, true,true));
		map.getViews().add(fView.create(map, layerAhtRestricted, 3, true,true));
		map.getViews().add(fView.create(map, layerOsmBasic, 4, true,true));
	}
}
