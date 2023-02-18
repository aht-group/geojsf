package org.geojsf.factory.xml.geojsf.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.SldTemplate;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSldXmlFactory <L extends JeeslLang, D extends JeeslDescription,
									SLD extends GeoJsfSld<L,D,SDX,SDT,?,?,?>,
									SDX extends GeoJsfSldXml<L,D,SLD>,
									SDT extends GeoJsfSldType<L,D,SDT,?>
									>
							implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlSldXmlFactory.class);
	public static final long serialVersionUID=1;
	
	private SldTemplate q;
	
	public XmlSldXmlFactory(Query query) {this(query.getSldTemplate());}
	public XmlSldXmlFactory(SldTemplate q)
	{
		this.q=q;
	}

	public SldTemplate build (SDX ejb)
	{
		SldTemplate xml = new SldTemplate();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
			

		
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
		
		return xml;
	}
}
