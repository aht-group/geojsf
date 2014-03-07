package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.factory.xml.openlayers.XmlLayerFactory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.xml.geojsf.Query;
import org.geojsf.xml.geojsf.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewFactory.class);
	
	public static final long serialVersionUID=1;
	
	private View q;
	
	public XmlViewFactory(Query query){this(query.getView());}
	public XmlViewFactory(View q)
	{
		this.q=q;
	}

	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,MAP,VIEW>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,MAP,VIEW>,MAP extends GeoJsfMap<L,D,SERVICE,LAYER,MAP,VIEW>, VIEW extends GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW>>
		View build (GeoJsfView<L,D,SERVICE,LAYER,MAP,VIEW> ejb)
	{
		View xml = new View();
		
		if(q.isSetVisible()){xml.setVisible(ejb.isVisible());}
		if(q.isSetLegend()){xml.setLegend(ejb.isLegend());}
		
		if(q.isSetLayer())
		{
			XmlLayerFactory f = new XmlLayerFactory(q.getLayer());
			xml.setLayer(f.build(ejb.getLayer()));
		}
			
		return xml;
	}
}
