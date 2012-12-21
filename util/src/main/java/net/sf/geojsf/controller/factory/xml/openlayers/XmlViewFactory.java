package net.sf.geojsf.controller.factory.xml.openlayers;

import java.io.Serializable;

import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import org.geojsf.model.interfaces.openlayers.GeoJsfService;
import org.geojsf.model.interfaces.openlayers.GeoJsfView;
import org.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import org.geojsf.xml.geojsf.Query;
import org.geojsf.xml.openlayers.View;
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

	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
		View build (GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL> ejb)
	{
		View xml = new View();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetZoom()){xml.setZoom(ejb.getZoom());}
		if(q.isSetX()){xml.setX(ejb.getX());}
		if(q.isSetY()){xml.setY(ejb.getY());}
		if(q.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		
		
		if(ejb.getLayer()!=null && ejb.getLayer().size()>0)
		{
			
		}
		
		return xml;
	}
}
