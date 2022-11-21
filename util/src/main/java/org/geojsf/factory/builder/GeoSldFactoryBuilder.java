package org.geojsf.factory.builder;

import org.geojsf.factory.ejb.sld.EjbGeoSldFactory;
import org.geojsf.factory.ejb.sld.EjbGeoSldRuleFactory;
import org.geojsf.factory.ejb.sld.EjbGeoSldTemplateFactory;
import org.geojsf.factory.jdom.specs.sld.JdomStyledLayerDescriptorFactory;
import org.geojsf.factory.xml.specs.sld.XmlStyledLayerDescriptorFactory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoSldFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
									LAYER extends GeoJsfLayer<L,D,?,?,?,?,SLD>,
									TEMPLATE extends GeoJsfSldTemplate<L,D>,
									SLD extends GeoJsfSld<L,D,TEMPLATE,TYPE,RULE>,
									TYPE extends GeoJsfSldType<L,D,TYPE,?>,
									RULE extends GeoJsfSldRule<L,D,?>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoSldFactoryBuilder.class);
	
	private final Class<TEMPLATE> cTemplate; public Class<TEMPLATE> getClassTemplate(){return cTemplate;}
	private final Class<TYPE> cSldType; public Class<TYPE> getClassSldType(){return cSldType;}
	private final Class<SLD> cSld; public Class<SLD> getClassSld(){return cSld;}
	private final Class<RULE> cRule; public Class<RULE> getClassRule(){return cRule;}
	
	public GeoSldFactoryBuilder(final Class<L> cL, final Class<D> cD,
								final Class<TEMPLATE> cTemplate,
								final Class<TYPE> cSldType,
								final Class<SLD> cSld,
								final Class<RULE> cRule)
	{
		super(cL,cD);
		this.cTemplate=cTemplate;
		this.cSldType=cSldType;
		this.cSld=cSld;
		this.cRule=cRule;
	}
	
	public EjbGeoSldFactory<TYPE,SLD> ejbSld() {return new EjbGeoSldFactory<TYPE,SLD>(cSld);}
	public EjbGeoSldRuleFactory<SLD,RULE> ejbRule() {return new EjbGeoSldRuleFactory<>(cRule);}
	public EjbGeoSldTemplateFactory<TEMPLATE> efTemplate() {return new EjbGeoSldTemplateFactory<>(cTemplate);}
	
	public XmlStyledLayerDescriptorFactory<L,LAYER,SLD,RULE> xmlStyledLayerDescriptor(SldConfigurationProvider sldConfigurationProvider) {return new XmlStyledLayerDescriptorFactory<>(sldConfigurationProvider);}
	
	public JdomStyledLayerDescriptorFactory<LAYER,TEMPLATE,TYPE,SLD> jdomStyledLayerDescriptor(SldConfigurationProvider sldConfigurationProvider) {return new JdomStyledLayerDescriptorFactory<>(sldConfigurationProvider);}
}