package net.sf.geojsf.controller.util;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfViewLayer;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfLang;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoLayerFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoServiceFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoViewFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoViewLayerFactory;

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
		if(serviceOsm==null){serviceOsm = fService.create("osm","http://vmap0.tiles.osgeo.org/wms/vmap0");}
		if(serviceAht==null){serviceAht = fService.create("aht","https://www.aht-group.com/geoserver/sf/wms");}
	}
	
	private void initLayer() throws UtilsIntegrityException
	{
		layerOsmBasic = fLayer.create("basic", serviceOsm,DefaultGeoJsfLang.defaultLangs);layerOsmBasic.setId(1);
		layerAhtRoads = fLayer.create("roads",serviceAht,DefaultGeoJsfLang.defaultLangs);layerAhtRoads.setId(2);
		layerAhtStreams = fLayer.create("streams",serviceAht,DefaultGeoJsfLang.defaultLangs);layerAhtStreams.setId(3);
		layerAhtRestricted = fLayer.create("restricted",serviceAht,DefaultGeoJsfLang.defaultLangs);layerAhtRestricted.setId(4);
	}
	
	private void initViews() throws UtilsIntegrityException
	{
		view = fView.create("view",5,0,0,DefaultGeoJsfLang.defaultLangs);
		view.getLayer().add(fViewLayer.create(view, layerOsmBasic, 1, true,true));
		view.getLayer().add(fViewLayer.create(view, layerAhtRoads, 2, true,true));
		view.getLayer().add(fViewLayer.create(view, layerAhtStreams, 3, true,true));
		view.getLayer().add(fViewLayer.create(view, layerAhtRestricted, 4, true,true));
	}
}
