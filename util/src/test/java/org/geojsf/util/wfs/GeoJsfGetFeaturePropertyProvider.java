package org.geojsf.util.wfs;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.geojsf.interfaces.wfs.WfsGetFeaturePropertyProvider;
import org.geojsf.test.model.SampleSpatialEntity;
import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfGetFeaturePropertyProvider implements WfsGetFeaturePropertyProvider
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfGetFeaturePropertyProvider.class);
	
	private Map<Class<?>,String> map;
	
	public GeoJsfGetFeaturePropertyProvider()
	{
		map = new Hashtable<Class<?>,String>();
		map.put(SampleSpatialEntity.class, "name");
	}
	
	@Override
	public List<String> getProperties(Class<?> type)
	{
		List<String> list = new ArrayList<String>();
		if(!map.containsKey(type))
		{
			// log some error
		}
		list.add(map.get(type));
		return list;
	}

	@Override
	public String getWorkspace()
	{
		return "geojsf";
	}

	@Override
	public Namespace getNameSpace()
	{
		return Namespace.getNamespace("geojsf", "http://www.geojsf.org");
	}

	@Override
	public String getGeoServerRestUrl()
	{
		return "http://www.geojsf.org/geoserver";
	}
}