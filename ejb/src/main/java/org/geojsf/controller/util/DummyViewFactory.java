package org.geojsf.controller.util;

import org.geojsf.factory.ejb.openlayer.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.openlayer.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.openlayer.EjbGeoViewFactory;
import org.geojsf.factory.ejb.openlayer.EjbGeoViewLayerFactory;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfViewLayer;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;

public class DummyViewFactory
{
	private EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fService;
	private EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fLayer;
	private EjbGeoViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fView;
	private EjbGeoViewLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfViewLayer> fViewLayer;
	
	public static DefaultGeoJsfService serviceOsm,serviceAht;
	private DefaultGeoJsfLayer layerOsmBasic,layerAhtRoads,layerAhtStreams,layerAhtRestricted;
	private DefaultGeoJsfView view;
	
	public DefaultGeoJsfView getView() {return view;}

	public DummyViewFactory() throws UtilsIntegrityException
	{
		fService = EjbGeoServiceFactory.factory(DefaultGeoJsfService.class);
		fLayer = EjbGeoLayerFactory.factory(DefaultGeoJsfLang.class,DefaultGeoJsfLayer.class);
		fView = EjbGeoViewFactory.factory(DefaultGeoJsfLang.class,DefaultGeoJsfView.class);
		fViewLayer = EjbGeoViewLayerFactory.factory(DefaultGeoJsfViewLayer.class);
		
		initServices();
		initLayer();
		initViews();
	}
	
	public static DefaultGeoJsfView build()
	{
		DummyViewFactory f = null;
		try {f = new DummyViewFactory();}
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
		view.getLayer().add(fViewLayer.create(view, layerAhtRoads, 1, true,true));
		view.getLayer().add(fViewLayer.create(view, layerAhtStreams, 2, true,true));
		view.getLayer().add(fViewLayer.create(view, layerAhtRestricted, 3, true,true));
		view.getLayer().add(fViewLayer.create(view, layerOsmBasic, 4, true,true));
	}
}