package org.geojsf.factory.xml.geojsf.sld;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.model.xml.geojsf.SldRule;
import org.jeesl.factory.xml.io.locale.status.XmlTypeFactory;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.symbol.XmlGraphicFactory;
import org.jeesl.factory.xml.system.symbol.XmlSymbolFactory;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.model.xml.io.graphic.Graphic;
import org.jeesl.model.xml.io.graphic.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSldRuleFactory <L extends JeeslLang, D extends JeeslDescription,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE,?,?>,
								SLDTEMPLATE extends GeoJsfSldXml<L,D,SLD>,
								SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
								
								RULE extends GeoJsfSldRule<L,D,?>
								>
							implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlSldRuleFactory.class);
	public static final long serialVersionUID=1;
	
	@SuppressWarnings("unused")
	private String lang;
	private SldRule q;
	
//	public XmlSldFactory(Query query) {this(query.getSldTemplate());}
	public XmlSldRuleFactory(SldRule q)
	{
		this(null,q);
	}
	public XmlSldRuleFactory(String lang,SldRule q)
	{
		this.lang=lang;
		this.q=q;
	}

	public SldRule build (RULE ejb)
	{
		SldRule xml = new SldRule();
		if(Objects.nonNull(q.getId())) {xml.setId(ejb.getId());}
		if(ObjectUtils.allNotNull(q.getLowerBound(),ejb.getLowerBound())) {xml.setLowerBound(ejb.getLowerBound());}
		if(ObjectUtils.allNotNull(q.getUpperBound(),ejb.getUpperBound())) {xml.setUpperBound(ejb.getUpperBound());}
				
		if(Objects.nonNull(q.getLangs()))
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		
		if(Objects.nonNull(q.getDescriptions()))
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(ejb.getDescription()));
		}
		
		Symbol s = XmlSymbolFactory.build();
		
		Graphic g = XmlGraphicFactory.build();
		g.setSymbol(s);
		g.setType(XmlTypeFactory.create(JeeslGraphicType.Code.symbol.toString()));
		xml.setGraphic(g);
		
		return xml;
	}
}
