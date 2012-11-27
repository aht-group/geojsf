package net.sf.geojsf.demo.util;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import net.sf.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfLang;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoLayerFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoServiceFactory;
import net.sf.geojsf.util.factory.ejb.openlayer.EjbGeoViewFactory;

public class DummyViewFactory
{
	private EjbGeoServiceFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fService;
	private EjbGeoLayerFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fLayer;
	private EjbGeoViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayer,DefaultGeoJsfView,DefaultGeoJsfService> fView;
	
	private DefaultGeoJsfService serviceOsm,serviceAht;
	private DefaultGeoJsfLayer layerOsmBasic,layerAhtRoads,layerAhtStreams;
	private DefaultGeoJsfView view;
	
	public DefaultGeoJsfView getView() {return view;}

	public DummyViewFactory() throws UtilsIntegrityException
	{
		fService = EjbGeoServiceFactory.factory(DefaultGeoJsfService.class);
		fLayer = EjbGeoLayerFactory.factory(DefaultGeoJsfLayer.class);
		fView = EjbGeoViewFactory.factory(DefaultGeoJsfView.class);
		
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
		serviceOsm = fService.create("osm","http://vmap0.tiles.osgeo.org/wms/vmap0");
		serviceAht = fService.create("aht","https://www.aht-group.com/geoserver/sf/wms");
	}
	
	private void initLayer() throws UtilsIntegrityException
	{
		layerOsmBasic = fLayer.create("basic", serviceOsm);layerOsmBasic.setId(1);
		layerAhtRoads = fLayer.create("roads",serviceAht);layerAhtRoads.setId(2);
		layerAhtStreams = fLayer.create("streams",serviceAht);layerAhtStreams.setId(3);
	}
	
	private void initViews() throws UtilsIntegrityException
	{
		view = fView.create("view");
		view.getLayer().add(layerOsmBasic);
		view.getLayer().add(layerAhtRoads);
		view.getLayer().add(layerAhtStreams);
	}
}
