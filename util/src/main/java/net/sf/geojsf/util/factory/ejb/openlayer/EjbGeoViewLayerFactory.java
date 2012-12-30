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

public class EjbGeoViewLayerFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,LT extends UtilsStatus<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoViewLayerFactory.class);
	
	final Class<VL> clViewLayer;
	
    public EjbGeoViewLayerFactory(final Class<VL> clViewLayer)
    {
        this.clViewLayer = clViewLayer;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL,LT>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL,LT>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL,LT>,LT extends UtilsStatus<L,D>>
    	EjbGeoViewLayerFactory<L,D,SERVICE,LAYER,VIEW,VL,LT> factory(final Class<VL> clViewLayer)
    {
        return new EjbGeoViewLayerFactory<L,D,SERVICE,LAYER,VIEW,VL,LT>(clViewLayer);
    }
	
	public VL create(VIEW view, LAYER layer, int orderNo, boolean visible, boolean legend) throws UtilsIntegrityException
	{
		VL ejb;
		try {ejb = clViewLayer.newInstance();}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setView(view);
		ejb.setLayer(layer);
		ejb.setOrderNo(orderNo);
		ejb.setVisible(visible);
		ejb.setLegend(legend);
        return ejb;
    }
}