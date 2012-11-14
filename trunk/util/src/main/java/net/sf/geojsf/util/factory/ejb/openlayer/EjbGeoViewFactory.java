package net.sf.geojsf.util.factory.ejb.openlayer;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoViewFactory<L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoViewFactory.class);
	
	final Class<VIEW> clService;
	
    public EjbGeoViewFactory(final Class<VIEW> clService)
    {
        this.clService = clService;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
    	EjbGeoViewFactory<L,D,LAYER,VIEW,SERVICE> factory(final Class<VIEW> clService)
    {
        return new EjbGeoViewFactory<L,D,LAYER,VIEW,SERVICE>(clService);
    }
	
	public VIEW create(String code) throws UtilsIntegrityException
	{
		VIEW ejb;
		try {ejb = clService.newInstance();}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setCode(code);
        return ejb;
    }
}