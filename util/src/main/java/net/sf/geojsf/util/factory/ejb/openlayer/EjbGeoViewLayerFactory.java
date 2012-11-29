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

public class EjbGeoViewLayerFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoViewLayerFactory.class);
	
	final Class<VL> clViewLayer;
	
    public EjbGeoViewLayerFactory(final Class<VL> clViewLayer)
    {
        this.clViewLayer = clViewLayer;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
    	EjbGeoViewLayerFactory<L,D,SERVICE,LAYER,VIEW,VL> factory(final Class<VL> clViewLayer)
    {
        return new EjbGeoViewLayerFactory<L,D,SERVICE,LAYER,VIEW,VL>(clViewLayer);
    }
	
	public VL create(VIEW view, LAYER layer, int orderNo, boolean visible) throws UtilsIntegrityException
	{
		VL ejb;
		try {ejb = clViewLayer.newInstance();}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setView(view);
		ejb.setLayer(layer);
		ejb.setOrderNo(orderNo);
		ejb.setVisible(visible);
        return ejb;
    }
}