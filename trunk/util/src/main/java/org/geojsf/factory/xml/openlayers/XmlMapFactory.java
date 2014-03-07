package org.geojsf.factory.xml.openlayers;

import java.io.Serializable;

import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMapFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlMapFactory.class);
	
	public static final long serialVersionUID=1;
	
	private Map q;
	
	public XmlMapFactory(Query query){this(query.getMap());}
	public XmlMapFactory(Map q)
	{
		this.q=q;
	}

	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>>
		Map build (GeoJsfMap<L,D,SERVICE,LAYER,VIEW,VL> ejb)
	{
		Map xml = new Map();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetZoom()){xml.setZoom(ejb.getZoom());}
		if(q.isSetX()){xml.setX(ejb.getX());}
		if(q.isSetY()){xml.setY(ejb.getY());}
		if(q.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		
		
		if(ejb.getViews()!=null && ejb.getViews().size()>0)
		{
			
		}
		
		return xml;
	}
}
