package net.sf.geojsf.util.factory.xml.openlayers;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.xml.openlayers.Layer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLayerFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlLayerFactory.class);
	
	public static final long serialVersionUID=1;
	
	public XmlLayerFactory()
	{
		
	}

	public <L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>> 
		Layer build (GeoJsfLayer<L,D,LAYER,SERVICE> ejb)
	{
		Layer xml = new Layer();
		
		xml.setCode(ejb.getCode());
		
		return xml;
	}
}
