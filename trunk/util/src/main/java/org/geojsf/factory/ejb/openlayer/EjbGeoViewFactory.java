package org.geojsf.factory.ejb.openlayer;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.openlayers.GeoJsfLayer;
import org.geojsf.interfaces.model.openlayers.GeoJsfService;
import org.geojsf.interfaces.model.openlayers.GeoJsfView;
import org.geojsf.interfaces.model.openlayers.GeoJsfViewLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoViewFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoViewFactory.class);
	
	final Class<VIEW> cView;
	private EjbLangFactory<L> fLang;
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
    	EjbGeoViewFactory<L,D,SERVICE,LAYER,VIEW,VL> factory(final Class<L> cLang,final Class<VIEW> cView)
    {
        return new EjbGeoViewFactory<L,D,SERVICE,LAYER,VIEW,VL>(cLang,cView);
    }
    
    public EjbGeoViewFactory(final Class<L> cLang,final Class<VIEW> cView)
    {
        this.cView = cView;
        fLang = EjbLangFactory.createFactory(cLang);
    } 
	
	public VIEW create(String code, int zoom, double x, double y, String[] langKeys) throws UtilsIntegrityException
	{
		VIEW ejb;
		try
		{
			ejb = cView.newInstance();
			ejb.setName(fLang.createEmpty(langKeys));
		}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setCode(code);
		ejb.setZoom(zoom);
		ejb.setX(x);
		ejb.setY(y);
        return ejb;
    }
}