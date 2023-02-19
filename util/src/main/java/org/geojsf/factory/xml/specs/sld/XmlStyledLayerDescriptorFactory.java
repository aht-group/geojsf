package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.model.xml.specs.sld.StyledLayerDescriptor;
import org.geojsf.util.SldConfigurationProvider;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStyledLayerDescriptorFactory <L extends JeeslLang,
												LAYER extends GeoJsfLayer<L,?,?,?,?,?,?,SLD>,
												SLD extends GeoJsfSld<L,?,?,?,RULE,LE,LA>,
												RULE extends GeoJsfSldRule<L,?,?>,
												LE extends JeeslRevisionEntity<L,?,?,?,LA,?>,
												LA extends JeeslRevisionAttribute<L,?,LE,?,?>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlStyledLayerDescriptorFactory.class);
	public static final long serialVersionUID=1;
	
	private final XmlNamedLayerFactory<L,LAYER,SLD,RULE,LE,LA> xfNamedLayer;
	
	public XmlStyledLayerDescriptorFactory(final SldConfigurationProvider sldCp)
	{
		xfNamedLayer = new XmlNamedLayerFactory<>(sldCp);
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
	
	public StyledLayerDescriptor build(SLD sld)
	{
		StyledLayerDescriptor xml = build();
		
		return xml;
	}
	
	public static StyledLayerDescriptor build()
	{
		StyledLayerDescriptor xml = new StyledLayerDescriptor();
		xml.setVersion("1.0.0");
		return xml;
	}
}