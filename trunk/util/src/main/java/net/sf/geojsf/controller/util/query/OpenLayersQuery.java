package net.sf.geojsf.controller.util.query;

import java.util.Hashtable;
import java.util.Map;

import net.sf.geojsf.xml.geojsf.Query;
import net.sf.geojsf.xml.openlayers.Repository;
import net.sf.geojsf.xml.openlayers.Service;

public class OpenLayersQuery
{
	public static enum Key {service,repositoryService}
	
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
}
