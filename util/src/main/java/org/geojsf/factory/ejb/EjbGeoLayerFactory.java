package org.geojsf.factory.ejb;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoLayerFactory<L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoLayerFactory.class);
	
	final Class<LAYER> clLayer;
	private EjbLangFactory<L> fLang;
    
    public static <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
		EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> factory(final Class<L> cLang,final Class<LAYER> clLayer)
    {
        return new EjbGeoLayerFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>(cLang,clLayer);
    }
    
    public EjbGeoLayerFactory(final Class<L> cLang, final Class<LAYER> clLayer)
    {
    	fLang = EjbLangFactory.createFactory(cLang);
        this.clLayer = clLayer;
    } 
	
	public LAYER build(String code, SERVICE service, String[] langKeys) throws UtilsIntegrityException
	{
		LAYER ejb;
		try
		{
			ejb = clLayer.newInstance();
			ejb.setName(fLang.createEmpty(langKeys));
		}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setCode(code);
		ejb.setService(service);
        return ejb;
    }
}