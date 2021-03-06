package org.geojsf.factory.builder;

import org.geojsf.factory.ejb.sld.EjbGeoSldFactory;
import org.geojsf.factory.jdom.specs.sld.JdomStyledLayerDescriptorFactory;
import org.geojsf.factory.xml.specs.sld.XmlStyledLayerDescriptorFactory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoSldFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
									G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
									F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
									LAYER extends GeoJsfLayer<L,D,?,?,?,?,SLD>,
									MAP extends GeoJsfMap<L,D,?,?,?>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
									SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,G>
										>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoSldFactoryBuilder.class);
	
	private final Class<SLDTEMPLATE> cTemplate; public Class<SLDTEMPLATE> getClassTemplate(){return cTemplate;}
	private final Class<SLDTYPE> cSldType; public Class<SLDTYPE> getClassSldType(){return cSldType;}
	private final Class<SLD> cSld; public Class<SLD> getClassSld(){return cSld;}
	
	public GeoSldFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<SLDTEMPLATE> cTemplate, final Class<SLDTYPE> cSldType, final Class<SLD> cSld)
	{
		super(cL,cD);
		this.cTemplate=cTemplate;
		this.cSldType=cSldType;
		this.cSld=cSld;
	}
	
	public EjbGeoSldFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> sld()
	{
		 return new EjbGeoSldFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(cSld);
    }
	
	public XmlStyledLayerDescriptorFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> xmlStyledLayerDescriptor(SldConfigurationProvider sldConfigurationProvider)
	{
		return new XmlStyledLayerDescriptorFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE>(sldConfigurationProvider);
	}
	
	public JdomStyledLayerDescriptorFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> jdomStyledLayerDescriptor(SldConfigurationProvider sldConfigurationProvider)
	{
		return new JdomStyledLayerDescriptorFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE>(sldConfigurationProvider);
	}
	
	
}