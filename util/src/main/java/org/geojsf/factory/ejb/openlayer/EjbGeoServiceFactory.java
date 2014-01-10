package org.geojsf.factory.ejb.openlayer;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoServiceFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoServiceFactory.class);
	
	final Class<SERVICE> clService;
	
    public EjbGeoServiceFactory(final Class<SERVICE> clService)
    {
        this.clService = clService;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>>
    	EjbGeoServiceFactory<L,D,SERVICE,LAYER,VIEW,VL> factory(final Class<SERVICE> clService)
    {
        return new EjbGeoServiceFactory<L,D,SERVICE,LAYER,VIEW,VL>(clService);
    }
	
	public SERVICE build(String code, String url) throws UtilsIntegrityException
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