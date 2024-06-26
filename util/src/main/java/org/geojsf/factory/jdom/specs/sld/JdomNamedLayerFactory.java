package org.geojsf.factory.jdom.specs.sld;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.factory.xml.specs.sld.XmlNameFactory;
import org.geojsf.factory.xml.specs.sld.XmlNamedLayerFactory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.model.xml.specs.sld.NamedLayer;
import org.geojsf.util.SldConfigurationProvider;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;

public class JdomNamedLayerFactory <LAYER extends GeoJsfLayer<?,?,?,?,?,?,?,SLD>,
									SDX extends GeoJsfSldXml<?,?,SLD>,
									SDT extends GeoJsfSldType<?,?,SDT,?>,
									SLD extends GeoJsfSld<?,?,SDX,SDT,?,?,?>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(JdomNamedLayerFactory.class);
	public static final long serialVersionUID=1;
	
	private Namespace nsSld = Namespace.getNamespace("sld", "http://www.opengis.net/sld");
	
	public JdomNamedLayerFactory(final SldConfigurationProvider sldCp)
	{
		
	}
	
	public Element build(LAYER layer)
	{
		NamedLayer xml = XmlNamedLayerFactory.build();
		xml.setName(XmlNameFactory.build(layer.getService().getCode()+":"+layer.getCode()));
		
		Element element = JaxbUtil.toDocument(xml).detachRootElement();
		if(layer.getSld()!=null)
		{
			
			logger.info("Building SLD for type="+layer.getSld().getType().getCode());
			try
			{
				switch(GeoJsfSldType.Type.valueOf(layer.getSld().getType().getCode()))
				{
					case xml:	element.addContent(userStyle(layer.getSld().getTemplate()));break;
					default:		logger.warn("No Handling for "+layer.getSld().getType().getCode());
				}
			}
			catch (IOException e) {logger.error(e.getMessage());}

		}
		return element;
	}
	
	private Element userStyle(SDX template) throws IOException
	{
		Element source = JDomUtil.load(IOUtils.toInputStream(template.getXml(), "UTF-8")).detachRootElement();
		
		Element namedLayer = source.getChild("NamedLayer", nsSld).detach();
		Element userStyle = namedLayer.getChild("UserStyle", nsSld).detach();
//		return source.getChild("UserStyle");
		return userStyle;
	}	
}