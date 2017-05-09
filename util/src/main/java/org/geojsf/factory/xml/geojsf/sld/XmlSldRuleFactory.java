package org.geojsf.factory.xml.geojsf.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.SldRule;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.factory.xml.system.symbol.XmlGraphicFactory;
import org.jeesl.factory.xml.system.symbol.XmlSymbolFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.symbol.Graphic;
import net.sf.ahtutils.xml.symbol.Symbol;

public class XmlSldRuleFactory <L extends UtilsLang, D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,FS>, GT extends UtilsStatus<GT,L,D>, FS extends UtilsStatus<FS,L,D>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLD extends GeoJsfSld<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								RULE extends GeoJsfSldRule<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
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
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetLowerBound() && ejb.getLowerBound()!=null){xml.setLowerBound(ejb.getLowerBound());}
		if(q.isSetUpperBound() && ejb.getUpperBound()!=null){xml.setUpperBound(ejb.getUpperBound());}
				
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
		
		Graphic g = XmlGraphicFactory.build();
		g.setSymbol(s);
		g.setType(XmlTypeFactory.create(JeeslGraphicType.Code.symbol.toString()));
		xml.setGraphic(g);
		
		return xml;
	}
}
