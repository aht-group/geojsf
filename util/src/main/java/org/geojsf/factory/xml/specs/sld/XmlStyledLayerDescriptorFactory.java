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
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlStyledLayerDescriptorFactory <L extends UtilsLang,D extends UtilsDescription,
												G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
												F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
												LAYER extends GeoJsfLayer<L,D,?,?,?,?,SLD>,
												MAP extends GeoJsfMap<L,D,?,?,?>,
												SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
												SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
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