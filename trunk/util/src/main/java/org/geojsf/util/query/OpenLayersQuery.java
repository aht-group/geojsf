package org.geojsf.util.query;

import java.util.Hashtable;

import net.sf.ahtutils.controller.util.query.StatusQuery;

import org.geojsf.xml.geojsf.Layer;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Query;
import org.geojsf.xml.geojsf.Repository;
import org.geojsf.xml.geojsf.Service;
import org.geojsf.xml.geojsf.View;

public class OpenLayersQuery
{
	public static enum Key {repositoryService,
							service,layer,map,view,
							viewLayer}
	
	private static java.util.Map<Key,Query> mQueries;
	
	public static Query get(Key key, String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case service: q.setService(service());break;
				case repositoryService: q.setRepository(repositoryService());break;
				case layer: q.setLayer(layer());break;
				case view: q.setView(view());break;
				case viewLayer: q.setLayer(viewLayer());break;
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
		xml.setUrl("");
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

		Layer xml = new Layer();
		xml.setTemporal(false);
		xml.setCode("");
		xml.setService(service);
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
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
		xml.setZoom(0);
		xml.setX(0);
		xml.setY(0);
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		return xml;
	}
}