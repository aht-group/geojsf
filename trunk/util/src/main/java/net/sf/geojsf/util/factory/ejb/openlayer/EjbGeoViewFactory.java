package net.sf.geojsf.util.factory.ejb.openlayer;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoViewFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoViewFactory.class);
	
	final Class<VIEW> clView;
	
    public EjbGeoViewFactory(final Class<VIEW> clView)
    {
        this.clView = clView;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
    	EjbGeoViewFactory<L,D,SERVICE,LAYER,VIEW,VL> factory(final Class<VIEW> clView)
    {
        return new EjbGeoViewFactory<L,D,SERVICE,LAYER,VIEW,VL>(clView);
    }
	
	public VIEW create(String code, int zoom, double x, double y) throws UtilsIntegrityException
	{
		VIEW ejb;
		try {ejb = clView.newInstance();}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setCode(code);
		ejb.setZoom(zoom);
		ejb.setX(x);
		ejb.setY(y);
        return ejb;
    }
}