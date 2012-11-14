package net.sf.geojsf.util.factory.ejb.openlayer;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoViewFactory<L extends UtilsLang,
								D extends UtilsDescription,
								LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
								VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
								SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoViewFactory.class);
	
	final Class<VIEW> clView;
	
    public EjbGeoViewFactory(final Class<VIEW> clView)
    {
        this.clView = clView;
    } 
    
    public static <L extends UtilsLang,
    				D extends UtilsDescription,
    				LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
    				VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
    				SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
    	EjbGeoViewFactory<L,D,LAYER,VIEW,SERVICE> factory(final Class<VIEW> clView)
    {
        return new EjbGeoViewFactory<L,D,LAYER,VIEW,SERVICE>(clView);
    }
	
	public VIEW create(String code) throws UtilsIntegrityException
	{
		VIEW ejb;
		try {ejb = clView.newInstance();}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setCode(code);
        return ejb;
    }
}