package net.sf.geojsf.controller.factory.xml.openlayers;

import java.io.Serializable;

import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import net.sf.geojsf.xml.geojsf.Query;
import net.sf.geojsf.xml.openlayers.Layer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLayerFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlLayerFactory.class);
	
	public static final long serialVersionUID=1;
	
	private Layer q;
	
	public XmlLayerFactory(Query query){this(query.getLayer());}
	public XmlLayerFactory(Layer q)
	{
		this.q=q;
	}

	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
		Layer build (GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL> ejb)
	{
		Layer xml = new Layer();
		
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		
		if(q.isSetService())
		{
			XmlServiceFactory f = new XmlServiceFactory(q.getService());
			xml.setService(f.build(ejb.getService()));
		}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		
		return xml;
	}
}
