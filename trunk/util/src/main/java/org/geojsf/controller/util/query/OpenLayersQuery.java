package org.geojsf.controller.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.controller.util.query.StatusQuery;

import org.geojsf.xml.geojsf.Query;
import org.geojsf.xml.openlayers.Layer;
import org.geojsf.xml.openlayers.Repository;
import org.geojsf.xml.openlayers.Service;
import org.geojsf.xml.openlayers.View;

public class OpenLayersQuery
{
	public static enum Key {service,repositoryService,
							layer,viewLayer,
							view}
	
	private static Map<Key,Query> mQueries;
	
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
				case viewLayer: q.setLayer(viewLayer());break;
				case view: q.setView(view());break;
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
		xml.setCode("");
		xml.setService(service);
		xml.setLangs(StatusQuery.langs());
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
		Layer layer = new Layer();
		layer.setCode("");
		
		View xml = new View();
		xml.setCode("");
		xml.setZoom(0);
		xml.setX(0);
		xml.setY(0);
		xml.setLangs(StatusQuery.langs());
		return xml;
	}
}
