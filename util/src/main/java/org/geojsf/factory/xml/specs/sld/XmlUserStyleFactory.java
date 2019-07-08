package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.UserStyle;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlUserStyleFactory <L extends UtilsLang,D extends UtilsDescription,
									G extends JeeslGraphic<L,D,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
									F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
									LAYER extends GeoJsfLayer<L,D,?,?,?,?,SLD>,
									MAP extends GeoJsfMap<L,D,?,?,?>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,G>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlUserStyleFactory.class);
	public static final long serialVersionUID=1;
	
	private final SldConfigurationProvider sldCp;
	private XmlFeatureTypeStyleFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> xfFeatureTypeStyle;
	
	public XmlUserStyleFactory(final SldConfigurationProvider sldCp)
	{
		this.sldCp=sldCp;
		xfFeatureTypeStyle = new XmlFeatureTypeStyleFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE>(sldCp);
	}
	
	public UserStyle build(SLD sld)
	{
		UserStyle xml = build();
		xml.setName(XmlNameFactory.build(sld.getName().get(sldCp.getLocaleCode()).getLang()));
		xml.setFeatureTypeStyle(xfFeatureTypeStyle.build(sld));
		return xml;
	}
	
	public static UserStyle build()
	{
		UserStyle xml = new UserStyle();
		return xml;
	}
}
