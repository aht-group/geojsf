package org.geojsf.factory.xml.specs.sld.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.xml.specs.ogc.XmlFilterFactory;
import org.geojsf.factory.xml.specs.sld.XmlGraphicFactory;
import org.geojsf.factory.xml.specs.sld.XmlPointSymbolizerFactory;
import org.geojsf.factory.xml.specs.sld.XmlSizeFactory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.Rule;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlRuleStatusFactory <L extends UtilsLang,D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
								
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,?,SERVICE,VP,DS,SLD>,
								MAP extends GeoJsfMap<L,D,?,VIEW,VP>,
								SCALE extends GeoJsfScale<L,D>,
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								VP extends GeoJsfViewPort,
								DS extends GeoJsfDataSource<L,D,LAYER>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
								RULE extends GeoJsfSldRule<L,D,G>> 
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlRuleStatusFactory.class);
	public static final long serialVersionUID=1;
	
	private final SldConfigurationProvider sldCp;
	
	public XmlRuleStatusFactory(final SldConfigurationProvider sldCp)
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
		xml.getPointSymbolizer().setGraphic(XmlGraphicFactory.external(sldCp.getSymbolizerUrlPrefix()+"12/"+x.getId()));
		xml.getPointSymbolizer().getGraphic().setSize(XmlSizeFactory.build(12));
		return xml;
	}

}