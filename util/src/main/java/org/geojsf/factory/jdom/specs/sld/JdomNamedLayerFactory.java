package org.geojsf.factory.jdom.specs.sld;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.geojsf.factory.xml.specs.sld.XmlNameFactory;
import org.geojsf.factory.xml.specs.sld.XmlNamedLayerFactory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.NamedLayer;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class JdomNamedLayerFactory <L extends JeeslLang,D extends JeeslDescription,
									G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
									F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<FS,L,D>,
									LAYER extends GeoJsfLayer<L,D,?,?,?,?,SLD>,
									MAP extends GeoJsfMap<L,D,?,?,?>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
									SLDTYPE extends JeeslStatus<SLDTYPE,L,D>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,G>>
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
					case template:	element.addContent(userStyle(layer.getSld().getTemplate()));break;
					default:		logger.warn("No Handling for "+layer.getSld().getType().getCode());
				}
			}
			catch (IOException e) {logger.error(e.getMessage());}

		}
		return element;
	}
	
	private Element userStyle(SLDTEMPLATE template) throws IOException
	{
		Element source = JDomUtil.load(IOUtils.toInputStream(template.getXml(), "UTF-8")).detachRootElement();
		
		Element namedLayer = source.getChild("NamedLayer", nsSld).detach();
		Element userStyle = namedLayer.getChild("UserStyle", nsSld).detach();
//		return source.getChild("UserStyle");
		return userStyle;
	}	
}