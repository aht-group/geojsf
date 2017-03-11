package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.specs.sld.StyledLayerDescriptor;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlStyledLayerDescriptorFactory <L extends UtilsLang,D extends UtilsDescription,
												G extends JeeslGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>,
												CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
												SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
												LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
												MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
												VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
												VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
												DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>,
												TEMPLATE extends GeoJsfSldTemplate<L,D,TEMPLATE,TYPE>,
												TYPE extends UtilsStatus<TYPE,L,D>,
												SLD extends GeoJsfSld<L,D,G,GT,GS,TEMPLATE,TYPE,SLD,RULE>,
												RULE extends GeoJsfSldRule<L,D,G,GT,GS,TEMPLATE,TYPE,SLD,RULE>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlStyledLayerDescriptorFactory.class);
	public static final long serialVersionUID=1;
	
	private XmlNamedLayerFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE> xfNl;
	
	public XmlStyledLayerDescriptorFactory()
	{
		xfNl = new XmlNamedLayerFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>();
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
			if(layer.getSld()==null){throw new UtilsConfigurationException("Layer "+layer.getCode()+" has now SLD");}
//			sld.getNamedLayer().g
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
