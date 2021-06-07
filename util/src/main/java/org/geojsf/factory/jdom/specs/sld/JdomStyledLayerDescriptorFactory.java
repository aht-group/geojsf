package org.geojsf.factory.jdom.specs.sld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.xml.specs.sld.XmlStyledLayerDescriptorFactory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class JdomStyledLayerDescriptorFactory <L extends JeeslLang,D extends JeeslDescription,
												G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
												F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
												LAYER extends GeoJsfLayer<L,D,?,?,?,?,SLD>,
												MAP extends GeoJsfMap<L,D,?,?,?>,
												SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
												SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
												SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
												RULE extends GeoJsfSldRule<L,D,G>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(JdomStyledLayerDescriptorFactory.class);
	public static final long serialVersionUID=1;
	
	private final JdomNamedLayerFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> fNamedLayer;
	
	public JdomStyledLayerDescriptorFactory(final SldConfigurationProvider sldCp)
	{
		fNamedLayer = new JdomNamedLayerFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE>(sldCp);
	}
	
	public Document build(LAYER layer) throws UtilsConfigurationException
	{
		List<LAYER> layers = new ArrayList<LAYER>();
		layers.add(layer);
		return build(layers);
	}
	
	private Document build(List<LAYER> layers) throws UtilsConfigurationException
	{
		Element xml = JaxbUtil.toDocument(XmlStyledLayerDescriptorFactory.build()).detachRootElement();
		xml = build();

		for(LAYER layer : layers)
		{
			if(layer.getSld()==null){throw new UtilsConfigurationException("Layer "+layer.getCode()+" has no SLD");}
			xml.addContent(fNamedLayer.build(layer));
		}
		Document doc = new Document();
		doc.setRootElement(xml);
//		JDomUtil.correctNsPrefixes(doc, new GeoJsfNsPrefixMapper());
		return doc;
	}
	
	private Element build()
	{
		Element xml = new Element("StyledLayerDescriptor");
		xml.setNamespace(Namespace.getNamespace("http://www.opengis.net/sld"));
		xml.addNamespaceDeclaration(Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance"));
		xml.addNamespaceDeclaration(Namespace.getNamespace("ogc","http://www.opengis.net/ogc"));
		xml.addNamespaceDeclaration(Namespace.getNamespace("se","http://www.opengis.net/se"));
		xml.addNamespaceDeclaration(Namespace.getNamespace("xlink","http://www.w3.org/1999/xlink"));
		xml.setAttribute("version", "1.0.0");
		xml.setAttribute("schemaLocation", "http://www.opengis.net/sld http://schemas.opengis.net/sld/1.1.0/StyledLayerDescriptor.xsd", Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance"));
		return xml;
	}
}