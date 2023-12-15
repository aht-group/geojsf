package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;
import java.util.Objects;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.View;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewFactory <L extends JeeslLang,D extends JeeslDescription,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,?,?>,
								VIEW extends GeoJsfView<LAYER,?,VIEW>,
								VP extends GeoJsfViewPort>
						implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewFactory.class);
	
	public static final long serialVersionUID=1;
	
	private View q;
	
	private XmlLayerFactory<L,D,CATEGORY,SERVICE,LAYER,VIEW,VP> xfLayer;
	
	public XmlViewFactory(Query query){this(query.getView());}
	public XmlViewFactory(View q)
	{
		this.q=q;
		if(q.isSetLayer()) {xfLayer = new XmlLayerFactory<>(q.getLayer());}
	}

	public View build (VIEW ejb)
	{
		View xml = new View();
		
		if(Objects.nonNull(q.isVisible())) {xml.setVisible(ejb.isVisible());}
		if(q.isSetLegend()){xml.setLegend(ejb.getLegend());}
		if(q.isSetNr()){xml.setNr(ejb.getOrderNo());}
		
		if(q.isSetLayer()) {xml.setLayer(xfLayer.build(ejb.getLayer()));}
			
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