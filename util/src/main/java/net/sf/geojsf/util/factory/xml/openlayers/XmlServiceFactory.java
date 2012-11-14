package net.sf.geojsf.util.factory.xml.openlayers;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.xml.openlayers.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlServiceFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlServiceFactory.class);
	
	public static final long serialVersionUID=1;
	
	public XmlServiceFactory()
	{
		
	}

	public <L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>>
		Service build (GeoJsfService<L,D,LAYER,SERVICE> ejb)
	{
		Service xml = new Service();
		xml.setUrl(ejb.getUrl());
		
		if(ejb.getLayer()!=null && ejb.getLayer().size()>0)
		{
			XmlLayerFactory f = new XmlLayerFactory();
			for(GeoJsfLayer<L,D,LAYER,SERVICE> layer : ejb.getLayer())
			{
				xml.getLayer().add(f.build(layer));
			}
		}
		
		return xml;
	}
}
