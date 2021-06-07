package org.geojsf.factory.ejb.sld;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoSldTemplateFactory<L extends JeeslLang,D extends JeeslDescription,
										G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
										F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
										CATEGORY extends GeoJsfCategory<L,D,LAYER>,
										SERVICE extends GeoJsfService<L,D,LAYER>,
										LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
										MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
										SCALE extends GeoJsfScale<L,D>, 
										VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
										VP extends GeoJsfViewPort,
										DS extends GeoJsfDataSource<L,D,LAYER>,
										SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
										SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
										SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
										RULE extends GeoJsfSldRule<L,D,G>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoSldTemplateFactory.class);
	
	final Class<SLDTEMPLATE> cTemplate;
	
    public EjbGeoSldTemplateFactory(final Class<SLDTEMPLATE> cTemplate)
    {
        this.cTemplate = cTemplate;
    } 
    
    public static <L extends JeeslLang,D extends JeeslDescription,
				    G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>>
    	EjbGeoSldTemplateFactory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> factory(final Class<SLDTEMPLATE> cTemplate)
    {
        return new EjbGeoSldTemplateFactory<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(cTemplate);
    }
	
	public SLDTEMPLATE build(String code)
	{
		SLDTEMPLATE ejb;
		try
		{
			ejb = cTemplate.newInstance();
			ejb.setCode(code);
		}
		catch (InstantiationException e) {e.printStackTrace();throw new RuntimeException(e);}
		catch (IllegalAccessException e) {e.printStackTrace();throw new RuntimeException(e);}
        return ejb;
    }
}