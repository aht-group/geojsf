package org.geojsf.factory.builder;

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
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class GeoCoreFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,?,?>,
								MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
								SCALE extends GeoJsfScale<L,D>,
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								VP extends GeoJsfViewPort

										>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoCoreFactoryBuilder.class);
	
	private final Class<CATEGORY> cCategory; public Class<CATEGORY> getClassCategory() {return cCategory;}
	private final Class<SERVICE> cService; public Class<SERVICE> getClassService() {return cService;}
	private final Class<LAYER> cLayer; public Class<LAYER> getClassLayer() {return cLayer;}
	private final Class<MAP> cMap; public Class<MAP> getClassMap() {return cMap;}
	private final Class<SCALE> cScale; public Class<SCALE> getClassScale() {return cScale;}
	private final Class<VIEW> cView; public Class<VIEW> getClassView() {return cView;}
	private final Class<VP> cViewPort; public Class<VP> getClassViewPort() {return cViewPort;}
	
	public GeoCoreFactoryBuilder(final Class<L> cL, final Class<D> cD,
									final Class<CATEGORY> cCategory,
									final Class<SERVICE> cService, final Class<LAYER> cLayer, final Class<MAP> cMap,
									final Class<SCALE> cScale,
									final Class<VIEW> cView, final Class<VP> cViewPort)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cService=cService;
		this.cLayer=cLayer;
		this.cMap=cMap;
		this.cScale=cScale;
		this.cView=cView;
		this.cViewPort=cViewPort;
	}
	
	public EjbGeoCategoryFactory<L,D,CATEGORY> ejbCategory()
	{
	    return new EjbGeoCategoryFactory<L,D,CATEGORY>(cCategory);
	}
	
    public EjbGeoServiceFactory<L,D,SERVICE> ejbService()
	{
    	return new EjbGeoServiceFactory<L,D,SERVICE>(cService);
	}
    
    public EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER> ejbLayer()
	{
    	return new EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER>(cL,cLayer);
	}
    
    public EjbGeoMapFactory<L,D,MAP> ejbMap()
    {
        return new EjbGeoMapFactory<L,D,MAP>(cL,cMap);
    }
    
    public EjbGeoViewFactory<L,D,LAYER,MAP,VIEW> ejbView()
	{
    	return new EjbGeoViewFactory<L,D,LAYER,MAP,VIEW>(cView);
	}
    
    
}