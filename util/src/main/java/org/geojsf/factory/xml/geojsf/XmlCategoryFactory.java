package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;
import java.util.Objects;

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
		if(Objects.nonNull(q.getLangs())) {xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(Objects.nonNull(q.getDescriptions())) {xfDescription = new XmlDescriptionsFactory<D>(q.getDescriptions());}
	}

	public Category build (CATEGORY ejb)
	{
		Category xml = new Category();
		if(Objects.nonNull(q.getCode())) {xml.setCode(ejb.getCode());}
		if(Objects.nonNull(q.getLangs())){xml.setLangs(xfLangs.getUtilsLangs(ejb.getName()));}
		if(Objects.nonNull(q.getDescriptions())){xml.setDescriptions(xfDescription.create(ejb.getDescription()));}
		
		return xml;
	}
}
