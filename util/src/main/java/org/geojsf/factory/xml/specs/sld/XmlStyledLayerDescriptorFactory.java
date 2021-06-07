package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.StyledLayerDescriptor;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStyledLayerDescriptorFactory <L extends JeeslLang,D extends JeeslDescription,
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
	final static Logger logger = LoggerFactory.getLogger(XmlStyledLayerDescriptorFactory.class);
	public static final long serialVersionUID=1;
	
	private final XmlNamedLayerFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> xfNamedLayer;
	
	public XmlStyledLayerDescriptorFactory(final SldConfigurationProvider sldCp)
	{
		xfNamedLayer = new XmlNamedLayerFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE>(sldCp);
	}
	
	public StyledLayerDescriptor build(LAYER layer) throws UtilsConfigurationException
	{
		List<LAYER> layers = new ArrayList<LAYER>();
		layers.add(layer);
		return build(layers);
	}
	
	private StyledLayerDescriptor build(List<LAYER> layers) throws UtilsConfigurationException
	{
		StyledLayerDescriptor sld = build();
		for(LAYER layer : layers)
		{
			if(layer.getSld()==null){throw new UtilsConfigurationException("Layer "+layer.getCode()+" has no SLD");}
			sld.getNamedLayer().add(xfNamedLayer.build(layer));
		}
		return sld;
	}
	
	public static StyledLayerDescriptor build()
	{
		StyledLayerDescriptor xml = new StyledLayerDescriptor();
		xml.setVersion("1.0.0");
		return xml;
	}
}