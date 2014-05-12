package org.geojsf.factory.ejb;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoMapFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoMapFactory.class);
	
	final Class<MAP> cView;
	private EjbLangFactory<L> fLang;
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
    	EjbGeoMapFactory<L,D,SERVICE,LAYER,MAP,VIEW> factory(final Class<L> cLang,final Class<MAP> cView)
    {
        return new EjbGeoMapFactory<L,D,SERVICE,LAYER,MAP,VIEW>(cLang,cView);
    }
    
    public EjbGeoMapFactory(final Class<L> cLang,final Class<MAP> cView)
    {
        this.cView = cView;
        fLang = EjbLangFactory.createFactory(cLang);
    } 
	
	public MAP create(String code, int zoom, double x, double y, String[] langKeys) throws UtilsIntegrityException
	{
		MAP ejb;
		try
		{
			ejb = cView.newInstance();
			ejb.setName(fLang.createEmpty(langKeys));
		}
		catch (InstantiationException e) {throw new UtilsIntegrityException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsIntegrityException(e.getMessage());}
		ejb.setCode(code);
		ejb.setZoom(zoom);
		ejb.setLat(x);
		ejb.setLon(y);
        return ejb;
    }
}