package org.geojsf.controller.util;

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
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class DummyViewFactory<L extends UtilsLang,D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>,
								CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								SCALE extends GeoJsfScale<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, 
								VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
{
	private EjbGeoCategoryFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fCategory;
	private EjbGeoServiceFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fService;
	private EjbGeoLayerFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fLayer;
	private EjbGeoMapFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fMap;
	private EjbGeoViewFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> fView;
	
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
	
    public static <L extends UtilsLang,D extends UtilsDescription,
    				G extends JeeslGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, SCALE extends GeoJsfScale<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>, 
					VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
					SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
    	DummyViewFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
    	factory(final Class<L> clLang,final Class<D> clDescription,final Class<CATEGORY> cCategory,final Class<SERVICE> clService,final Class<LAYER> clLayer,final Class<MAP> clMap,final Class<VIEW> clView)
    {
        return new DummyViewFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(clLang,clDescription,cCategory,clService,clLayer,clMap,clView);
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
