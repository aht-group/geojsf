package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.factory.xml.specs.sld.rule.XmlRuleStatusFactory;
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
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.FeatureTypeStyle;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlFeatureTypeStyleFactory <L extends UtilsLang,D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(XmlFeatureTypeStyleFactory.class);
	public static final long serialVersionUID=1;
	
	private XmlRuleStatusFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE> xfRuleStatus;
	
	
	public XmlFeatureTypeStyleFactory(final SldConfigurationProvider<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE> sldCp)
	{
		xfRuleStatus = new XmlRuleStatusFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE>(sldCp);
	}
	
	public FeatureTypeStyle build(SLD sld)
	{
		FeatureTypeStyle xml = build();
		xml.setName(XmlNameFactory.build("xx"));
		
		if(sld.getType().getCode().equals(GeoJsfSldType.Type.status.toString()))
		{
			logger.info(sld.toString());
			xml.getRule().addAll(xfRuleStatus.build(sld));
		}
		
		return xml;
	}
	
	public static FeatureTypeStyle build()
	{
		FeatureTypeStyle xml = new FeatureTypeStyle();
		return xml;
	}
}