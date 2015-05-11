package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.SldRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSldRuleFactory <L extends UtilsLang,
							D extends UtilsDescription,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLD extends GeoJsfSld<L,D,SLDTYPE,SLD,RULE,SLDTEMPLATE>,
							RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLD,RULE,SLDTEMPLATE>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlSldRuleFactory.class);
	public static final long serialVersionUID=1;
	
	private SldRule q;
	
//	public XmlSldFactory(Query query) {this(query.getSldTemplate());}
	public XmlSldRuleFactory(SldRule q)
	{
		this.q=q;
	}

	public SldRule build (RULE ejb)
	{
		SldRule xml = new SldRule();
		if(q.isSetId()){xml.setId(ejb.getId());}
			
		return xml;
	}
}
