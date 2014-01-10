package org.geojsf.factory.ejb;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoViewFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoViewFactory.class);
	
	final Class<VIEW> clViewLayer;
	
    public EjbGeoViewFactory(final Class<VIEW> clViewLayer)
    {
        this.clViewLayer = clViewLayer;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
    	EjbGeoViewFactory<L,D,SERVICE,LAYER,MAP,VIEW> factory(final Class<VIEW> clViewLayer)
    {
        return new EjbGeoViewFactory<L,D,SERVICE,LAYER,MAP,VIEW>(clViewLayer);
    }
	
	public VIEW create(MAP view, LAYER layer, int orderNo, boolean visible, boolean legend) throws UtilsIntegrityException
	{
		VIEW ejb;
		try {ejb = clViewLayer.newInstance();}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setMap(view);
		ejb.setLayer(layer);
		ejb.setOrderNo(orderNo);
		ejb.setVisible(visible);
		ejb.setLegend(legend);
        return ejb;
    }
}