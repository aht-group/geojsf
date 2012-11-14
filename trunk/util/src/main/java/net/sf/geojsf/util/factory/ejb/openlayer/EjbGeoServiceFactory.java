package net.sf.geojsf.util.factory.ejb.openlayer;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoServiceFactory<L extends UtilsLang,
									D extends UtilsDescription,
									LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
									VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
									SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoServiceFactory.class);
	
	final Class<SERVICE> clService;
	
    public EjbGeoServiceFactory(final Class<SERVICE> clService)
    {
        this.clService = clService;
    } 
    
    public static <L extends UtilsLang,
    				D extends UtilsDescription,
    				VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
    				LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
    				SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
    	EjbGeoServiceFactory<L,D,LAYER,VIEW,SERVICE> factory(final Class<SERVICE> clService)
    {
        return new EjbGeoServiceFactory<L,D,LAYER,VIEW,SERVICE>(clService);
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