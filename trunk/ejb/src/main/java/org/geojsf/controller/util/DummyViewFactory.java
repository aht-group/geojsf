package org.geojsf.controller.util;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.geojsf.factory.ejb.EjbGeoCategoryFactory;
import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.EjbGeoMapFactory;
import org.geojsf.factory.ejb.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.EjbGeoViewFactory;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;

public class DummyViewFactory<L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
{
	private EjbGeoCategoryFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> fCategory;
	private EjbGeoServiceFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> fService;
	private EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> fLayer;
	private EjbGeoMapFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> fMap;
	private EjbGeoViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> fView;
	
	public SERVICE serviceOsm,serviceAht;
	public SERVICE getServiceOsm() {return serviceOsm;}
	public SERVICE getServiceAht() {return serviceAht;}
	
	private CATEGORY category;

	private LAYER layerOsmBasic,layerAhtRoads,layerAhtStreams,layerAhtRestricted;
	
	private MAP map;
	public MAP getMap() {return map;}
	
	final Class<L> clLang;
	final Class<D> clDescription;
	final Class<CATEGORY> cCategory;
	final Class<SERVICE> clService;
	final Class<LAYER> clLayer;
	final Class<MAP> clMap;
	final Class<VIEW> clView;
	
	private final String[] langs = {"en","de"};
	
    public static 	<L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
    	DummyViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> factory(final Class<L> clLang,final Class<D> clDescription,final Class<CATEGORY> cCategory,final Class<SERVICE> clService,final Class<LAYER> clLayer,final Class<MAP> clMap,final Class<VIEW> clView)
    {
        return new DummyViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>(clLang,clDescription,cCategory,clService,clLayer,clMap,clView);
    }
	
    public DummyViewFactory(final Class<L> clLang,final Class<D> clDescription,final Class<CATEGORY> cCategory,final Class<SERVICE> clService,final Class<LAYER> clLayer,final Class<MAP> clMap,final Class<VIEW> clView)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.cCategory = cCategory;
        this.clService = clService;
        this.clLayer = clLayer;
        this.clMap = clMap;
        this.clView = clView;
        
        fCategory = EjbGeoCategoryFactory.factory(cCategory);
        fService = EjbGeoServiceFactory.factory(clService);
		fLayer = EjbGeoLayerFactory.factory(clLang,clLayer);
		fMap = EjbGeoMapFactory.factory(clLang,clMap);
		fView = EjbGeoViewFactory.factory(clView);
		
		try
		{
			initServices();
			initCategory();
			initLayer();
			initViews();
		}
		catch (UtilsContraintViolationException e) {e.printStackTrace();}
    } 
 
    private void initCategory() throws UtilsContraintViolationException
	{
		if(category==null){category = fCategory.build("cat");serviceOsm.setId(0);}
	}
    
	private void initServices() throws UtilsContraintViolationException
	{
		if(serviceOsm==null){serviceOsm = fService.build("osm","http://vmap0.tiles.osgeo.org/wms/vmap0");serviceOsm.setId(0);}
		if(serviceAht==null){serviceAht = fService.build("aht","https://www.aht-group.com/geoserver/sf/wms");serviceAht.setId(1);}
	}
	
	private void initLayer() throws UtilsContraintViolationException
	{
		layerOsmBasic = fLayer.build("basic", serviceOsm,category,langs);layerOsmBasic.setId(1);
		layerAhtRoads = fLayer.build("roads",serviceAht,category,langs);layerAhtRoads.setId(2);
		layerAhtStreams = fLayer.build("streams",serviceAht,category,langs);layerAhtStreams.setId(3);
		layerAhtRestricted = fLayer.build("restricted",serviceAht,category,langs);layerAhtRestricted.setId(4);
	}
	
	private void initViews() throws UtilsContraintViolationException
	{
		map = fMap.create("defaultMap",langs);map.setId(1);
		map.getViews().add(fView.create(map, layerAhtRoads, 1, true,true));
		map.getViews().add(fView.create(map, layerAhtStreams, 2, true,true));
		map.getViews().add(fView.create(map, layerAhtRestricted, 3, true,true));
		map.getViews().add(fView.create(map, layerOsmBasic, 4, true,true));
	}
}
