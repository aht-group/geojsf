package org.geojsf.controller.util;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.factory.ejb.EjbGeoCategoryFactory;
import org.geojsf.factory.ejb.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.EjbGeoMapFactory;
import org.geojsf.factory.ejb.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.EjbGeoViewFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;

public class DummyViewFactory<L extends UtilsLang,
								D extends UtilsDescription,
								CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
{
	private EjbGeoCategoryFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fCategory;
	private EjbGeoServiceFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fService;
	private EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fLayer;
	private EjbGeoMapFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fMap;
	private EjbGeoViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> fView;
	
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
	
    public static <L extends UtilsLang,
					D extends UtilsDescription,
					CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
    	DummyViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>
    	factory(final Class<L> clLang,final Class<D> clDescription,final Class<CATEGORY> cCategory,final Class<SERVICE> clService,final Class<LAYER> clLayer,final Class<MAP> clMap,final Class<VIEW> clView)
    {
        return new DummyViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>(clLang,clDescription,cCategory,clService,clLayer,clMap,clView);
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
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
    } 
 
    private void initCategory() throws UtilsConstraintViolationException
	{
		if(category==null){category = fCategory.build("cat");serviceOsm.setId(0);}
	}
    
	private void initServices() throws UtilsConstraintViolationException
	{
		if(serviceOsm==null){serviceOsm = fService.build("osm","http://vmap0.tiles.osgeo.org/wms/vmap0");serviceOsm.setId(0);}
		if(serviceAht==null){serviceAht = fService.build("aht","https://www.aht-group.com/geoserver/sf/wms");serviceAht.setId(1);}
	}
	
	private void initLayer() throws UtilsConstraintViolationException
	{
		layerOsmBasic = fLayer.build("basic", serviceOsm,category,langs);layerOsmBasic.setId(1);
		layerAhtRoads = fLayer.build("roads",serviceAht,category,langs);layerAhtRoads.setId(2);
		layerAhtStreams = fLayer.build("streams",serviceAht,category,langs);layerAhtStreams.setId(3);
		layerAhtRestricted = fLayer.build("restricted",serviceAht,category,langs);layerAhtRestricted.setId(4);
	}
	
	private void initViews() throws UtilsConstraintViolationException
	{
		map = fMap.create("defaultMap",langs);map.setId(1);
		map.getViews().add(fView.create(map, layerAhtRoads, 1, true,true));
		map.getViews().add(fView.create(map, layerAhtStreams, 2, true,true));
		map.getViews().add(fView.create(map, layerAhtRestricted, 3, true,true));
		map.getViews().add(fView.create(map, layerOsmBasic, 4, true,true));
	}
}
