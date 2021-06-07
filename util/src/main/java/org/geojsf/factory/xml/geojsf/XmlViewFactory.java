package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.View;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewFactory <L extends JeeslLang,D extends JeeslDescription,
								G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,?>,
								MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
								SCALE extends GeoJsfScale<L,D>, 
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								VP extends GeoJsfViewPort,
								DS extends GeoJsfDataSource<L,D,LAYER>>
						implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewFactory.class);
	
	public static final long serialVersionUID=1;
	
	private View q;
	
	public XmlViewFactory(Query query){this(query.getView());}
	public XmlViewFactory(View q)
	{
		this.q=q;
	}

	public 
		View build (GeoJsfView<LAYER,MAP,VIEW> ejb)
	{
		View xml = new View();
		
		if(q.isSetVisible()){xml.setVisible(ejb.isVisible());}
		if(q.isSetLegend()){xml.setLegend(ejb.getLegend());}
		if(q.isSetNr()){xml.setNr(ejb.getOrderNo());}
		
		if(q.isSetLayer())
		{
			XmlLayerFactory f = new XmlLayerFactory(q.getLayer());
			xml.setLayer(f.build(ejb.getLayer()));
		}
			
		return xml;
	}
	
	public static View build(int nr, boolean visible, boolean legend, Layer layer)
	{
		View xml = new View();
		xml.setNr(nr);
		xml.setLegend(legend);
		xml.setVisible(true);
		xml.setLayer(layer);
		return xml;
	}
}
