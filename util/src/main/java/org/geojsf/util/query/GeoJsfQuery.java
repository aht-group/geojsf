package org.geojsf.util.query;

import java.util.Hashtable;

import org.geojsf.model.xml.geojsf.Category;
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.Repository;
import org.geojsf.model.xml.geojsf.Scale;
import org.geojsf.model.xml.geojsf.Service;
import org.geojsf.model.xml.geojsf.Sld;
import org.geojsf.model.xml.geojsf.SldRule;
import org.geojsf.model.xml.geojsf.SldTemplate;
import org.geojsf.model.xml.geojsf.View;
import org.geojsf.model.xml.geojsf.ViewPort;
import org.jeesl.factory.xml.io.locale.status.XmlStyleFactory;
import org.jeesl.factory.xml.io.locale.status.XmlTypeFactory;
import org.jeesl.util.query.xml.XmlStatusQuery;

public class GeoJsfQuery
{
	public static enum Key {repositoryService,
							category,service,layer,map,view,viewPort,
							viewLayer,
							sldTemplate}
	
	private static java.util.Map<Key,Query> mQueries;
	
	public static Query get(Key key){return get(key,null);}
	public static Query get(Key key, String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case service: q.setService(service());break;
				case category: q.setCategory(category());break;
				case repositoryService: q.setRepository(repositoryService());break;
				case layer: q.setLayer(layer());break;
				case view: q.setView(view());break;
				case viewLayer: q.setLayer(viewLayer());break;
				case viewPort: q.setViewPort(viewPort());break;
				case map: q.setMap(map());break;
				case sldTemplate: q.setSldTemplate(sldTemplate());break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		return q;
	}
	
	public static Service service()
	{
		Service xml = new Service();

		xml.setCode("");
		xml.setWms("");
		xml.setWcs("");
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		
		return xml;
	}
	
	public static Category category()
	{
		Category xml = new Category();

		xml.setCode("");
		
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		
		return xml;
	}
	
	public static Repository repositoryService()
	{
		Repository xml = new Repository();
		xml.getService().add(service());
		return xml;
	}
	
	public static Layer layer()
	{
		Service service = new Service();
		service.setCode("");
		
		Category category = new Category();
		category.setCode("");

		Layer xml = new Layer();
		xml.setTemporal(false);
		xml.setSql(true);
		xml.setCode("");
		xml.setService(service);
		xml.setCategory(category);
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		xml.setViewPort(viewPort());
		return xml;
	}
	
	public static ViewPort viewPort()
	{
		ViewPort xml = new ViewPort();
		xml.setId(0l);
		xml.setLat(0d);
		xml.setLon(0d);
		xml.setLeft(0d);
		xml.setRight(0d);
		xml.setTop(0d);
		xml.setBottom(0d);
		xml.setScale(scale());
		return xml;
	}
	
	public static Scale scale()
	{
		Scale xml = new Scale();
		xml.setValue(0);
		return xml;
	}
	
	public static Layer viewLayer()
	{	
		Layer xml = new Layer();
		xml.setCode("");
		return xml;
	}
	
	public static View view()
	{	
		View xml = new View();
		xml.setLegend(true);
		xml.setNr(1);
		xml.setVisible(true);
		xml.setLayer(viewLayer());
		return xml;
	}
	
	public static Map map()
	{
		Layer layer = new Layer();
		layer.setCode("");
		
		Map xml = new Map();
		xml.setCode("");
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		xml.setViewPort(viewPort());
		return xml;
	}
	
	public static SldTemplate sldTemplate()
	{	
		SldTemplate xml = new SldTemplate();
		xml.setCode("");
		xml.setType(XmlTypeFactory.create("myType"));
		xml.setLangs(XmlStatusQuery.langs());
		xml.setDescriptions(XmlStatusQuery.descriptions());
		return xml;
	}
	
	public static Sld sldRules()
	{	
		SldRule rule = new SldRule();
		rule.setId(0l);
		rule.setLowerBound(0d);
		rule.setUpperBound(0d);
		rule.setSize(0);
		rule.setColor("");
		rule.setStyle(XmlStyleFactory.build(""));
		rule.setLangs(XmlStatusQuery.langs());
		rule.setDescriptions(XmlStatusQuery.descriptions());
		
		Sld xml = new Sld();
		xml.getSldRule().add(rule);
		return xml;
	}
}