package org.geojsf.factory.xml.geojsf.sld;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.model.xml.geojsf.Sld;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSldFactory <L extends JeeslLang, D extends JeeslDescription,
							SLD extends GeoJsfSld<L,D,SDX,SDT,SDR,?,?>,
							SDX extends GeoJsfSldXml<L,D,SLD>,
							SDT extends GeoJsfSldType<L,D,SDT,?>,
							SDR extends GeoJsfSldRule<L,D,?>
							>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlSldFactory.class);
	public static final long serialVersionUID=1;
	
	private Sld q;
	
	private XmlSldXmlFactory<L,D,SLD,SDX,SDT> xfTemplate;
	private XmlSldRuleFactory<L,D,SLD,SDX,SDT,SDR> xfRule;
	
//	public XmlSldFactory(Query query) {this(query.getSldTemplate());}
	public XmlSldFactory(Sld q)
	{
		this.q=q;
		if(Objects.nonNull(q.getSldTemplate())) {xfTemplate = new XmlSldXmlFactory<>(q.getSldTemplate());}
		if(ObjectUtils.isNotEmpty(q.getSldRule())) {xfRule = new XmlSldRuleFactory<>(q.getSldRule().get(0));}
	}

	public Sld build (SLD ejb)
	{
		Sld xml = new Sld();
		if(Objects.nonNull(q.getId())) {xml.setId(ejb.getId());}
			
/*		if(q.isSetType())
		{
			XmlTypeFactory f = new XmlTypeFactory(q.getType());
			xml.setType(f.build(ejb.getType()));
		}
*/		
		if(ObjectUtils.allNotNull(q.getSldTemplate(),ejb.getTemplate())) {xml.setSldTemplate(xfTemplate.build(ejb.getTemplate()));}
		
		if(ObjectUtils.isNotEmpty(q.getSldRule()))
		{
			for(SDR rule : ejb.getRules())
			{
				xml.getSldRule().add(xfRule.build(rule));
			}
		}
		
		return xml;
	}
	
	public static Sld id()
	{
		Sld xml = new Sld();
		xml.setId(0l);
		return xml;
	}
}
