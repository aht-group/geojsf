package org.geojsf.util.query;

import java.util.Hashtable;

import net.sf.ahtutils.controller.util.query.StatusQuery;

import org.geojsf.xml.geojsf.Category;
import org.geojsf.xml.geojsf.Layer;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Query;
import org.geojsf.xml.geojsf.Repository;
import org.geojsf.xml.geojsf.Scale;
import org.geojsf.xml.geojsf.Service;
import org.geojsf.xml.geojsf.View;
import org.geojsf.xml.geojsf.ViewPort;

public class GeoJsfQuery
{
	public static enum Key {repositoryService,
							category,service,layer,map,view,viewPort,
							viewLayer}
	
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
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
		return xml;
	}
	
	public static Category category()
	{
		Category xml = new Category();

		xml.setCode("");
		
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
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
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		xml.setViewPort(viewPort());
		return xml;
	}
	
	public static ViewPort viewPort()
	{
		ViewPort xml = new ViewPort();
		xml.setId(0);
		xml.setLat(0);
		xml.setLon(0);
		xml.setLeft(0);
		xml.setRight(0);
		xml.setTop(0);
		xml.setBottom(0);
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
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		xml.setViewPort(viewPort());
		return xml;
	}
}