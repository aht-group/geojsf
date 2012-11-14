package net.sf.geojsf.util.factory.ejb.openlayer;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoLayerFactory<L extends UtilsLang,
								D extends UtilsDescription,
								LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
								VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
								SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoLayerFactory.class);
	
	final Class<LAYER> clLayer;
	
    public EjbGeoLayerFactory(final Class<LAYER> clLayer)
    {
        this.clLayer = clLayer;
    } 
    
    public static <L extends UtilsLang,
    				D extends UtilsDescription,
    				LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,
    				VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,
    				SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
    	EjbGeoLayerFactory<L,D,LAYER,VIEW,SERVICE> factory(final Class<LAYER> clLayer)
    {
        return new EjbGeoLayerFactory<L,D,LAYER,VIEW,SERVICE>(clLayer);
    }
	
	public LAYER create(String code, SERVICE service) throws UtilsIntegrityException
	{
		LAYER ejb;
		try {ejb = clLayer.newInstance();}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setCode(code);
		ejb.setService(service);
        return ejb;
    }
}