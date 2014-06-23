package org.geojsf.controller.util;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.EjbGeoMapFactory;
import org.geojsf.factory.ejb.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.EjbGeoViewFactory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;

public class DummyViewFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,SERVICE,LAYER,MAP,VIEW,VP>>
{
	private EjbGeoServiceFactory<L,D,SERVICE,LAYER,MAP,VIEW,VP> fService;
	private EjbGeoLayerFactory<L,D,SERVICE,LAYER,MAP,VIEW,VP> fLayer;
	private EjbGeoMapFactory<L,D,SERVICE,LAYER,MAP,VIEW,VP> fMap;
	private EjbGeoViewFactory<L,D,SERVICE,LAYER,MAP,VIEW,VP> fView;
	
	public SERVICE serviceOsm,serviceAht;
	public SERVICE getServiceOsm() {return serviceOsm;}
	public SERVICE getServiceAht() {return serviceAht;}

	private LAYER layerOsmBasic,layerAhtRoads,layerAhtStreams,layerAhtRestricted;
	
	private MAP map;
	public MAP getMap() {return map;}
	
	final Class<L> clLang;
	final Class<D> clDescription;
	final Class<SERVICE> clService;
	final Class<LAYER> clLayer;
	final Class<MAP> clMap;
	final Class<VIEW> clView;
	
	private final String[] langs = {"en","de"};
	
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,SERVICE,LAYER,MAP,VIEW,VP>>
		DummyViewFactory<L,D,SERVICE,LAYER,MAP,VIEW,VP> factory(final Class<L> clLang,final Class<D> clDescription,final Class<SERVICE> clService,final Class<LAYER> clLayer,final Class<MAP> clMap,final Class<VIEW> clView)
    {
        return new DummyViewFactory<L,D,SERVICE,LAYER,MAP,VIEW,VP>(clLang,clDescription,clService,clLayer,clMap,clView);
    }
	
    public DummyViewFactory(final Class<L> clLang,final Class<D> clDescription,final Class<SERVICE> clService,final Class<LAYER> clLayer,final Class<MAP> clMap,final Class<VIEW> clView)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.clService = clService;
        this.clLayer = clLayer;
        this.clMap = clMap;
        this.clView = clView;
        
        fService = EjbGeoServiceFactory.factory(clService);
		fLayer = EjbGeoLayerFactory.factory(clLang,clLayer);
		fMap = EjbGeoMapFactory.factory(clLang,clMap);
		fView = EjbGeoViewFactory.factory(clView);
		
		try
		{
			initServices();
			initLayer();
			initViews();
		}
		catch (UtilsIntegrityException e) {e.printStackTrace();}
    } 
 
	private void initServices() throws UtilsIntegrityException
	{
		if(serviceOsm==null){serviceOsm = fService.build("osm","http://vmap0.tiles.osgeo.org/wms/vmap0");serviceOsm.setId(0);}
		if(serviceAht==null){serviceAht = fService.build("aht","https://www.aht-group.com/geoserver/sf/wms");serviceAht.setId(1);}
	}
	
	private void initLayer() throws UtilsIntegrityException
	{
		layerOsmBasic = fLayer.build("basic", serviceOsm,langs);layerOsmBasic.setId(1);
		layerAhtRoads = fLayer.build("roads",serviceAht,langs);layerAhtRoads.setId(2);
		layerAhtStreams = fLayer.build("streams",serviceAht,langs);layerAhtStreams.setId(3);
		layerAhtRestricted = fLayer.build("restricted",serviceAht,langs);layerAhtRestricted.setId(4);
	}
	
	private void initViews() throws UtilsIntegrityException
	{
		map = fMap.create("defaultMap",5,0,0,langs);map.setId(1);
		map.getViews().add(fView.create(map, layerAhtRoads, 1, true,true));
		map.getViews().add(fView.create(map, layerAhtStreams, 2, true,true));
		map.getViews().add(fView.create(map, layerAhtRestricted, 3, true,true));
		map.getViews().add(fView.create(map, layerOsmBasic, 4, true,true));
	}
}
