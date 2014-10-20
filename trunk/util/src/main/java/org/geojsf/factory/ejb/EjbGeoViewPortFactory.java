package org.geojsf.factory.ejb;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.xml.geojsf.ViewPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoViewPortFactory<L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoViewPortFactory.class);
	
	final Class<VP> cViewPort;
	
    public EjbGeoViewPortFactory(final Class<VP> cViewPort)
    {
        this.cViewPort = cViewPort;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>>
		EjbGeoViewPortFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP> factory(final Class<VP> cViewPort)
    {
        return new EjbGeoViewPortFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP>(cViewPort);
    }
	
    @Deprecated //Remove method
	public VP build2(String[] langKeys)
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
	
	public VP build()
	{
		VP ejb = null;
		try
		{
			ejb = cViewPort.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
        return ejb;
    }
	
	public VP build(ViewPort viewPort)
	{
		VP ejb = build();
		ejb = update(ejb,viewPort);
		return ejb;
	}
	
	public VP update(VP viewPort, ViewPort xmlViewPort)
	{
		viewPort.setScale(xmlViewPort.getScale().getValue());
		
		viewPort.setMarginLeft(xmlViewPort.getLeft());
		viewPort.setMarginRight(xmlViewPort.getRight());
		viewPort.setMarginTop(xmlViewPort.getTop());
		viewPort.setMarginBottom(xmlViewPort.getBottom());
		
		viewPort.setLat(xmlViewPort.getLat());
		viewPort.setLon(xmlViewPort.getLon());
		
		return viewPort;
	}
}