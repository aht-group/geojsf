package org.geojsf.controller.util;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.ejb.core.EjbGeoCategoryFactory;
import org.geojsf.factory.ejb.core.EjbGeoLayerFactory;
import org.geojsf.factory.ejb.core.EjbGeoMapFactory;
import org.geojsf.factory.ejb.core.EjbGeoServiceFactory;
import org.geojsf.factory.ejb.core.EjbGeoViewFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

public class DummyViewFactory<L extends JeeslLang,D extends JeeslDescription,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,SLD>,
								MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
								SCALE extends GeoJsfScale<L,D>, 
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								VP extends GeoJsfViewPort,
								DS extends GeoJsfDataSource<L,D,LAYER>,
								SLDTEMPLATE extends GeoJsfSldXml<L,D,SLD>,
								SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE,?,?>,
								RULE extends GeoJsfSldRule<L,D,?>>
{
	private EjbGeoCategoryFactory<L,D,CATEGORY> fCategory;
	private EjbGeoServiceFactory<L,D,SERVICE> fService;
	private EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER> fLayer;
	private EjbGeoMapFactory<L,D,MAP> fMap;
	private EjbGeoViewFactory<L,D,LAYER,MAP,VIEW> fView;
	
	public SERVICE serviceOsm,serviceAht;
	public SERVICE getServiceOsm() {return serviceOsm;}
	public SERVICE getServiceAht() {return serviceAht;}
	
	private CATEGORY category;

	private LAYER layerOsmBasic,layerAhtRoads,layerAhtStreams,layerAhtRestricted;
	
	private MAP map;
	public MAP getMap() {return map;}
	
	final Class<LAYER> clLayer;
	final Class<MAP> clMap;
	final Class<VIEW> clView;
	
	private final String[] langs = {"en","de"};
	
    public static <L extends JeeslLang,D extends JeeslDescription,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
					SLDTEMPLATE extends GeoJsfSldXml<L,D,SLD>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE,?,?>,
					RULE extends GeoJsfSldRule<L,D,?>>
    	DummyViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
    	factory(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore,
    			final Class<LAYER> clLayer,final Class<MAP> clMap,final Class<VIEW> clView)
    {
        return new DummyViewFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(fbCore,clLayer,clMap,clView);
    }
	
    public DummyViewFactory(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,?,MAP,VIEW> fbCore,
    						final Class<LAYER> clLayer,final Class<MAP> clMap,final Class<VIEW> clView)
    {
        this.clLayer = clLayer;
        this.clMap = clMap;
        this.clView = clView;
        
        fCategory = fbCore.ejbCategory();
        fService = fbCore.ejbService();
		fLayer = fbCore.ejbLayer();
		fMap = fbCore.ejbMap();
		fView = fbCore.ejbView();
		
		try
		{
			initServices();
			initCategory();
			initLayer();
			initViews();
		}
		catch (JeeslConstraintViolationException e) {e.printStackTrace();}
    } 
 
    private void initCategory() throws JeeslConstraintViolationException
	{
		if(category==null){category = fCategory.build("cat");serviceOsm.setId(0);}
	}
    
	private void initServices() throws JeeslConstraintViolationException
	{
		if(serviceOsm==null){serviceOsm = fService.build("osm","http://vmap0.tiles.osgeo.org/wms/vmap0");serviceOsm.setId(0);}
		if(serviceAht==null){serviceAht = fService.build("aht","https://www.aht-group.com/geoserver/sf/wms");serviceAht.setId(1);}
	}
	
	private void initLayer() throws JeeslConstraintViolationException
	{
		layerOsmBasic = fLayer.build("basic", serviceOsm,category,langs);layerOsmBasic.setId(1);
		layerAhtRoads = fLayer.build("roads",serviceAht,category,langs);layerAhtRoads.setId(2);
		layerAhtStreams = fLayer.build("streams",serviceAht,category,langs);layerAhtStreams.setId(3);
		layerAhtRestricted = fLayer.build("restricted",serviceAht,category,langs);layerAhtRestricted.setId(4);
	}
	
	private void initViews() throws JeeslConstraintViolationException
	{
		map = fMap.create("defaultMap",langs);map.setId(1);
		map.getViews().add(fView.create(map, layerAhtRoads, 1, true,true));
		map.getViews().add(fView.create(map, layerAhtStreams, 2, true,true));
		map.getViews().add(fView.create(map, layerAhtRestricted, 3, true,true));
		map.getViews().add(fView.create(map, layerOsmBasic, 4, true,true));
	}
}
