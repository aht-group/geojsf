package org.geojsf.factory.xml.specs.sld.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.xml.specs.ogc.XmlFilterFactory;
import org.geojsf.factory.xml.specs.sld.XmlGraphicFactory;
import org.geojsf.factory.xml.specs.sld.XmlPointSymbolizerFactory;
import org.geojsf.factory.xml.specs.sld.XmlSizeFactory;
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
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.Rule;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlRuleStatusFactory <L extends UtilsLang,D extends UtilsDescription,
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
	final static Logger logger = LoggerFactory.getLogger(XmlRuleStatusFactory.class);
	public static final long serialVersionUID=1;
	
	private final SldConfigurationProvider<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE> sldCp;
	
	public XmlRuleStatusFactory(final SldConfigurationProvider<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,TEMPLATE,TYPE,SLD,RULE> sldCp)
	{
		this.sldCp=sldCp;
	}
	
	@SuppressWarnings("unchecked")
	public <X extends UtilsStatus<X,L,D>> List<Rule> build(SLD sld)
	{
		List<Rule> rules = new ArrayList<Rule>();
		try
		{
			if(sldCp.getJeeslFacade()==null){throw new UtilsConfigurationException(UtilsFacade.class.getSimpleName()+" not available in "+sldCp.getClass().getSimpleName());}
			if(sld.getStatusClass()==null){throw new UtilsConfigurationException("Attribute statusClass not available in "+sld.getClass().getSimpleName());}
			if(sld.getStatusClass()==null){throw new UtilsConfigurationException("Attribute statusAttribute not available in "+sld.getClass().getSimpleName());}
			Class<X> cStatus = (Class<X>)Class.forName(sld.getStatusClass()).asSubclass(UtilsStatus.class);
			for(X x :sldCp.getJeeslFacade().allOrderedPositionVisible(cStatus))
			{
				rules.add(build(sld,x));
			}
		}
		catch (ClassNotFoundException e){logger.error(e.getMessage());}
		catch (UtilsConfigurationException e) {logger.error(e.getMessage());}
		return rules;
	}
	
	private <X extends UtilsStatus<X,L,D>> Rule build(SLD sld, X x)
	{
		Rule xml = XmlRuleFactory.build(x.getName().get(sldCp.getLocaleCode()).getLang());
		xml.setFilter(XmlFilterFactory.equal(sld.getStatusAttribute(), x.getId()));
		xml.setPointSymbolizer(XmlPointSymbolizerFactory.build());
		xml.getPointSymbolizer().setGraphic(XmlGraphicFactory.external("http://lis.aht-group.net/lis/image/symbolizer/status/12/"+x.getId()));
		xml.getPointSymbolizer().getGraphic().setSize(XmlSizeFactory.build(12));
		return xml;
	}

}