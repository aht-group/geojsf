package org.geojsf.factory.json;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.geojsf.factory.wkt.SridFactory;
import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

public class GeoJsonFeatureCollectionFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsonFeatureCollectionFactory.class);
	public static final long serialVersionUID=1;
	
	private final Map<String,String> propertyCustom;
	private final Set<String> propertyFiler;
	
	public static GeoJsonFeatureCollectionFactory instance() {return new GeoJsonFeatureCollectionFactory();}
	public GeoJsonFeatureCollectionFactory()
	{
		propertyCustom = new HashMap<>();
		propertyFiler = new HashSet<>();
	}
	
	public GeoJsonFeatureCollectionFactory withPropertyFilter(List<String> list)
	{
		propertyFiler.addAll(list);
		return this;
	}
	public GeoJsonFeatureCollectionFactory withProperty(String key, String value)
	{
		propertyCustom.put(key, value);
		return this;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public org.geojson.FeatureCollection build(File f) throws IOException
	{
		org.geojson.FeatureCollection json = new org.geojson.FeatureCollection();
		
		Map connect = new HashMap();
		connect.put("url", f.toURI().toURL());

		DataStore ds = DataStoreFinder.getDataStore(connect);
		String[] typeNames = ds.getTypeNames();		
		
		String typeName = typeNames[0];

		FeatureSource featureSource = ds.getFeatureSource(typeName);
		FeatureCollection collection = featureSource.getFeatures();
		FeatureIterator iterator = collection.features();

		try
		{
			while (iterator.hasNext())
			{
				Feature feature = iterator.next();
				GeometryAttribute ga = feature.getDefaultGeometryProperty();
				
				MultiPolygon mp = (MultiPolygon)ga.getValue();
				mp.setSRID(SridFactory.toSrid(ga.getType().getCoordinateReferenceSystem()));
				
				Map<String,Object> map = new HashMap<>();
				
				for(String key : propertyCustom.keySet())
				{
					map.put(key,propertyCustom.get(key));
				}
				
				for(Property p : feature.getProperties())
				{
//					logger.info(p.getName().toString());
					if(propertyFiler.contains(p.getName().toString()))
					{
						map.put(p.getName().toString(),p.getValue().toString());
					}
				}
				
				GeoJsonObject go = GeoJsonGeometryFactory.build(mp);
				org.geojson.Feature jsonFeature = new org.geojson.Feature();
				
				jsonFeature.setProperties(map);
				jsonFeature.setGeometry(go);
				
				json.getFeatures().add(jsonFeature);
			}
		}
		finally {iterator.close();}
		return json;
	}
	
	public static GeoJsonObject build(MultiPolygon polygon)
	{
		org.geojson.Polygon json = new org.geojson.Polygon();
		
//		Geometry p = TopologyPreservingSimplifier.simplify(polygon, 100000000);
//		logger.info(p.getGeometryType());
		
		List<LngLatAlt> list = new ArrayList<LngLatAlt>();
		for(Coordinate c : polygon.getCoordinates())
		{
			list.add(GeoJsonCoordinateFactory.build(c));
		}
		
		json.add(list);
		
		return json;
	}
	
	public static GeoJsonObject build(Point point)
	{
		org.geojson.Point json = new org.geojson.Point();
		
		json.setCoordinates(GeoJsonCoordinateFactory.build(point));
		
		return json;
	}
}