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

public class OSMViewFactory
{
	private EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fService;
	private EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fLayer;
	private EjbGeoMapFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fView;
	private EjbGeoViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> fViewLayer;
	
	public static DefaultGeoJsfService serviceOsm,serviceAht;
	private DefaultGeoJsfLayer layerOsmBasic,layerAhtRoads,layerAhtStreams,layerAhtRestricted;
	private DefaultGeoJsfMap view;
	
	public DefaultGeoJsfMap getView() {return view;}

	public OSMViewFactory() throws UtilsIntegrityException
	{
		fService = EjbGeoServiceFactory.factory(DefaultGeoJsfService.class);
		fLayer = EjbGeoLayerFactory.factory(DefaultGeoJsfLang.class,DefaultGeoJsfLayer.class);
		fView = EjbGeoMapFactory.factory(DefaultGeoJsfLang.class,DefaultGeoJsfMap.class);
		fViewLayer = EjbGeoViewFactory.factory(DefaultGeoJsfView.class);
		
		initServices();
		initLayer();
		initViews();
	}
	
	public static DefaultGeoJsfMap build()
	{
		OSMViewFactory f = null;
		try {f = new OSMViewFactory();}
		catch (UtilsIntegrityException e) {e.printStackTrace();}
		return f.getView();
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
		view = fView.create("view",5,0,0,DefaultGeoJsfLang.defaultLangs);
	//	view.getLayer().add(fViewLayer.create(view, layerAhtRoads, 1, true,true));
	//	view.getLayer().add(fViewLayer.create(view, layerAhtStreams, 2, true,true));
	//	view.getLayer().add(fViewLayer.create(view, layerAhtRestricted, 3, true,true));
		view.getViews().add(fViewLayer.create(view, layerOsmBasic, 4, true,true));
	}
}
