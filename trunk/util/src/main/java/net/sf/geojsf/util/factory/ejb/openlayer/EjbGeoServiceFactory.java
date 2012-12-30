package net.sf.geojsf.util.factory.ejb.openlayer;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoServiceFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,LT extends UtilsStatus<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoServiceFactory.class);
	
	final Class<SERVICE> clService;
	
    public EjbGeoServiceFactory(final Class<SERVICE> clService)
    {
        this.clService = clService;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,LT extends UtilsStatus<L,D>>
    	EjbGeoServiceFactory<L,D,SERVICE,LAYER,VIEW,VL,LT> factory(final Class<SERVICE> clService)
    {
        return new EjbGeoServiceFactory<L,D,SERVICE,LAYER,VIEW,VL,LT>(clService);
    }
	
	public SERVICE create(String code, String url) throws UtilsIntegrityException
	{
		SERVICE ejb;
		try {ejb = clService.newInstance();}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setUrl(url);
		ejb.setCode(code);
        return ejb;
    }
}