package org.geojsf.factory.xml.geojsf.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.SldRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.factory.xml.status.XmlStyleFactory;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.factory.xml.symbol.XmlGraphicFactory;
import net.sf.ahtutils.factory.xml.symbol.XmlSymbolFactory;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphicType;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.symbol.Graphic;
import net.sf.ahtutils.xml.symbol.Symbol;

public class XmlSldRuleFactory <L extends UtilsLang,
								D extends UtilsDescription,
								G extends UtilsGraphic<L,D,GT,GS>,
								GT extends UtilsStatus<GT,L,D>,
								GS extends UtilsStatus<GS,L,D>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
								SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
								RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
							implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlSldRuleFactory.class);
	public static final long serialVersionUID=1;
	
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
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetLowerBound() && ejb.getLowerBound()!=null){xml.setLowerBound(ejb.getLowerBound());}
		if(q.isSetUpperBound() && ejb.getUpperBound()!=null){xml.setUpperBound(ejb.getUpperBound());}
		if(q.isSetSize()){xml.setSize(ejb.getSize());}
		if(q.isSetColor()){xml.setColor(ejb.getColor());}
		
		if(q.isSetStyle())
		{
			XmlStyleFactory<SLDSTYLE,L,D> f = new XmlStyleFactory<SLDSTYLE,L,D>(lang,q.getStyle());
			xml.setStyle(f.build(ejb.getStyle()));
		}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory<D> f = new XmlDescriptionsFactory<D>(q.getDescriptions());
			xml.setDescriptions(f.create(ejb.getDescription()));
		}
		
		
		Symbol s = XmlSymbolFactory.build();
		s.setColor(ejb.getColor());
		s.setSize(ejb.getSize());
		s.setStyle(XmlStyleFactory.build(ejb.getStyle().getCode()));
		
		Graphic g = XmlGraphicFactory.build();
		g.setSymbol(s);
		g.setType(XmlTypeFactory.create(UtilsGraphicType.Code.symbol.toString()));
		xml.setGraphic(g);
		
		return xml;
	}
}
