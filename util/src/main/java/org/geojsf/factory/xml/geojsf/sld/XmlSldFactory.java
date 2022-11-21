package org.geojsf.factory.xml.geojsf.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.model.xml.geojsf.Sld;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSldFactory <L extends JeeslLang, D extends JeeslDescription,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D>,
							SLDTYPE extends GeoJsfSldType<L,D,SLDTYPE,?>,
							SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
							RULE extends GeoJsfSldRule<L,D,?>
							>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlSldFactory.class);
	public static final long serialVersionUID=1;
	
	private Sld q;
	
	private XmlSldTemplateFactory<L,D,SLDTEMPLATE,SLDTYPE,SLD,RULE> xfTemplate;
	private XmlSldRuleFactory<L,D,SLDTEMPLATE,SLDTYPE,SLD,RULE> xfRule;
	
//	public XmlSldFactory(Query query) {this(query.getSldTemplate());}
	public XmlSldFactory(Sld q)
	{
		this.q=q;
		if(q.isSetSldTemplate()){xfTemplate = new XmlSldTemplateFactory<>(q.getSldTemplate());}
		if(q.isSetSldRule()) {xfRule = new XmlSldRuleFactory<>(q.getSldRule().get(0));}
	}

	public Sld build (SLD ejb)
	{
		Sld xml = new Sld();
		if(q.isSetId()){xml.setId(ejb.getId());}
			
/*		if(q.isSetType())
		{
			XmlTypeFactory f = new XmlTypeFactory(q.getType());
			xml.setType(f.build(ejb.getType()));
		}
*/		
		if(q.isSetSldTemplate() && ejb.getTemplate()!=null)
		{
			xml.setSldTemplate(xfTemplate.build(ejb.getTemplate()));
		}
		
		if(q.isSetSldRule())
		{
			
			for(RULE rule : ejb.getRules())
			{
				xml.getSldRule().add(xfRule.build(rule));
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
