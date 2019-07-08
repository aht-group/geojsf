package org.geojsf.factory.xml.geojsf.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Sld;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class XmlSldFactory <L extends UtilsLang, D extends UtilsDescription,
							G extends JeeslGraphic<L,D,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
							F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
							RULE extends GeoJsfSldRule<L,D,G>
							>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlSldFactory.class);
	public static final long serialVersionUID=1;
	
	private Sld q;
	
	private XmlSldTemplateFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> xfTemplate;
	
//	public XmlSldFactory(Query query) {this(query.getSldTemplate());}
	public XmlSldFactory(Sld q)
	{
		this.q=q;
		if(q.isSetSldTemplate()){xfTemplate = new XmlSldTemplateFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(q.getSldTemplate());}
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
			XmlSldRuleFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> f = new XmlSldRuleFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(q.getSldRule().get(0));
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
