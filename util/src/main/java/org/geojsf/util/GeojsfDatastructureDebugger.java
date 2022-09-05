package org.geojsf.util;

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
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicShape;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeojsfDatastructureDebugger 
{
	final static Logger logger = LoggerFactory.getLogger(GeojsfDatastructureDebugger.class);
	
	public static <L extends JeeslLang, D extends JeeslDescription,
					G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
					F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
					SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,SLDSTYLE extends JeeslStatus<L,D,SLDSTYLE>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>>
		void debug(MAP map)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Map.").append(map.getId());
		sb.append(" code=").append(map.getCode());
		logger.info(sb.toString());
		for(VIEW view : map.getViews())
		{
//			debug(1,view);
		}
	}
	
	public static <L extends JeeslLang, D extends JeeslDescription,
					G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
					F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,SLDSTYLE extends JeeslStatus<L,D,SLDSTYLE>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>>
		void debug(VIEW view)
	{
//		debug(0,view);
	}
	public static <L extends JeeslLang, D extends JeeslDescription,
	G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
	F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,SLDSTYLE extends JeeslStatus<L,D,SLDSTYLE>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>>
		void debug(int indent, VIEW view)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(tab(indent));
		sb.append("View.").append(view.getId());
		sb.append(" visible=").append(view.isVisible());
		logger.info(sb.toString());
//		debug(indent+1,view.getLayer());
	}
	
	public static <L extends JeeslLang, D extends JeeslDescription,
					G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
					F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,SLDSTYLE extends JeeslStatus<L,D,SLDSTYLE>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>> 
		void debug(LAYER layer)
	{
//		debug(0,layer);
	}
	
	public static <L extends JeeslLang, D extends JeeslDescription,
	G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
	F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,SLDSTYLE extends JeeslStatus<L,D,SLDSTYLE>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>>
		void debug(int indent, LAYER layer)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(tab(indent));
		sb.append("Layer.").append(layer.getId());
		sb.append(" code=").append(layer.getCode());
		logger.info(sb.toString());
//		debug(indent+1,layer.getService());
	}
	
	public static <L extends JeeslLang, D extends JeeslDescription,
	G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
	F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,SLDSTYLE extends JeeslStatus<L,D,SLDSTYLE>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>>
		void debug(SERVICE service)
	{
//		debug(0,service);
	}
	
	public static <L extends JeeslLang, D extends JeeslDescription,
					G extends JeeslGraphic<GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
					F extends JeeslGraphicComponent<G,GT,F,FS>, FS extends JeeslGraphicShape<L,D,FS,G>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,?>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>>
		void debug(int indent, SERVICE service)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(tab(indent));
		sb.append("Service.").append(service.getId());
		sb.append(" code=").append(service.getCode());
		logger.info(sb.toString());
		for(LAYER layer : service.getLayer())
		{
//			debug(indent+1,layer);
		}
	}
	
	private static String tab(int size)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<size;i++)
		{
			sb.append("\t");
		}
		return sb.toString();
	}
}