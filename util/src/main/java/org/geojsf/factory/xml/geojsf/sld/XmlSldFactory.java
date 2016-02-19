package org.geojsf.factory.xml.geojsf.sld;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Sld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSldFactory <L extends UtilsLang,
							D extends UtilsDescription,
							G extends UtilsGraphic<L,D,G,GT,GS>,
							GT extends UtilsStatus<GT,L,D>,
							GS extends UtilsStatus<GS,L,D>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE>,
							RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlSldFactory.class);
	public static final long serialVersionUID=1;
	
	private Sld q;
	
//	public XmlSldFactory(Query query) {this(query.getSldTemplate());}
	public XmlSldFactory(Sld q)
	{
		this.q=q;
	}

	public Sld build (SLD ejb)
	{
		Sld xml = new Sld();
		if(q.isSetId()){xml.setId(ejb.getId());}
			
		if(q.isSetSldTemplate() && ejb.getTemplate()!=null)
		{
			XmlSldTemplateFactory<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE> f = new XmlSldTemplateFactory<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE>(q.getSldTemplate());
			xml.setSldTemplate(f.build(ejb.getTemplate()));
		}
		
		if(q.isSetSldRule())
		{
			XmlSldRuleFactory<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE> f = new XmlSldRuleFactory<L,D,G,GT,GS,SLDTYPE,SLD,RULE,SLDTEMPLATE>(q.getSldRule().get(0));
			for(RULE rule : ejb.getRules())
			{
				xml.getSldRule().add(f.build(rule));
			}
		}
		
		return xml;
	}
	
	public static Sld id()
	{
		Sld xml = new Sld();
		xml.setId(0);
		return xml;
	}
}
