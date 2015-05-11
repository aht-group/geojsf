package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.SldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSldTemplateFactory <L extends UtilsLang,
									D extends UtilsDescription,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
									SLD extends GeoJsfSld<L,D,SLDTYPE,SLD,RULE,SLDTEMPLATE>,
									RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLD,RULE,SLDTEMPLATE>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
		implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlSldTemplateFactory.class);
	public static final long serialVersionUID=1;
	
	private SldTemplate q;
	
	public XmlSldTemplateFactory(Query query) {this(query.getSldTemplate());}
	public XmlSldTemplateFactory(SldTemplate q)
	{
		this.q=q;
	}

	public SldTemplate build (SLDTEMPLATE ejb)
	{
		SldTemplate xml = new SldTemplate();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
			
		if(q.isSetType())
		{
			XmlTypeFactory f = new XmlTypeFactory(q.getType());
			xml.setType(f.build(ejb.getType()));
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
		
		return xml;
	}
}
