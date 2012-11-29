package net.sf.geojsf.util.factory.xml.openlayers;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfViewLayer;
import net.sf.geojsf.xml.openlayers.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewFactory.class);
	
	public static final long serialVersionUID=1;
	
	public XmlViewFactory()
	{
		
	}

	public <L extends UtilsLang,D extends UtilsDescription,SERVICE extends GeoJsfService<L,D,SERVICE,LAYER,VIEW,VL>, LAYER extends GeoJsfLayer<L,D,SERVICE,LAYER,VIEW,VL>,VIEW extends GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL>, VL extends GeoJsfViewLayer<L,D,SERVICE,LAYER,VIEW,VL>>
		View build (GeoJsfView<L,D,SERVICE,LAYER,VIEW,VL> ejb)
	{
		View xml = new View();
		
		if(ejb.getLayer()!=null && ejb.getLayer().size()>0)
		{
			
		}
		
		return xml;
	}
}
