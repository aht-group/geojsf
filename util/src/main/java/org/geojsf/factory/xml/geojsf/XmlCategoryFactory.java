package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.model.xml.geojsf.Category;
import org.geojsf.model.xml.geojsf.Query;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCategoryFactory <L extends JeeslLang, D extends JeeslDescription,
								CATEGORY extends GeoJsfCategory<L,D,?>>
						implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlCategoryFactory.class);
	public static final long serialVersionUID=1;
	
	private Category q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescription;
	
	public XmlCategoryFactory(Query query) {this(query.getCategory());}
	public XmlCategoryFactory(Category q)
	{
		this.q=q;
		if(q.isSetLangs()) {xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(q.isSetDescriptions()) {xfDescription = new XmlDescriptionsFactory<D>(q.getDescriptions());}
	}

	public Category build (CATEGORY ejb)
	{
		Category xml = new Category();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(ejb.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescription.create(ejb.getDescription()));}
		
		return xml;
	}
}
