package org.geojsf.factory.ejb;

import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoViewPortFactory<L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,SERVICE,LAYER,MAP,VIEW,VP>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoViewPortFactory.class);
	
	final Class<VP> cViewPort;
	
	private EjbLangFactory<L> fLang;
	
    public EjbGeoViewPortFactory(final Class<L> cLang, final Class<VP> cViewPort)
    {
        this.cViewPort = cViewPort;
        
        fLang = EjbLangFactory.createFactory(cLang);
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,SERVICE,LAYER,MAP,VIEW,VP>>
    	EjbGeoViewPortFactory<L,D,SERVICE,LAYER,MAP,VIEW,VP> factory(final Class<L> cLang, final Class<VP> cViewPort)
    {
        return new EjbGeoViewPortFactory<L,D,SERVICE,LAYER,MAP,VIEW,VP>(cLang,cViewPort);
    }
	
	public VP build(String[] langKeys)
	{
		VP ejb = null;
		try
		{
			ejb = cViewPort.newInstance();
//			ejb.setName(fLang.createEmpty(langKeys));
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
        return ejb;
    }
}