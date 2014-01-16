package org.geojsf.util.wfs;

import java.util.List;

import net.sf.ahtutils.interfaces.facade.UtilsIdFacade;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.GeoJsfUtilsTestBootstrap;
import org.geojsf.test.model.SampleSpatialEntity;
import org.geojsf.util.GeoServerConfigKeys;
import org.geojsf.xml.gml.Coordinates;
import org.geojsf.xml.ogc.Distance;
import org.geojsf.xml.wfs.GetFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliPointQuery extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(CliPointQuery.class);
	
	private Configuration config;
	private DefaultGeoJsfLayer layer;
	
	public CliPointQuery(Configuration config)
	{
		this.config=config;
	}
	
	private UtilsIdFacade getGeoFacade()
	{
		return null;
	}
	
	public void pointQuery()
	{
		String[] properties = {"city_name"};
		
		Coordinates coordinates = new Coordinates();
		coordinates.setValue("18.337941894531248,13.19704345703125");
		
		Distance distance = new Distance();
		distance.setUnits("degree");
		distance.setValue("1.074");
		
		GetFeature gf = PointQueryFactory.cGetFeature(config.getString("geoserver.test.pointquery.layer"),properties,"the_geom",coordinates,distance);
		
		WfsHttpRequest r = new WfsHttpRequest(config.getString(GeoServerConfigKeys.restUrl)+"/wcs");
		JDomUtil.debug(r.request(gf));
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void genericPointQuery()
	{
//ahtutils.highlight:point
		UtilsIdFacade fGeo = getGeoFacade();
		
		Coordinates coordinates = new Coordinates();
		coordinates.setValue("5.4,6.2");
		
		Distance distance = new Distance();
		distance.setUnits("degree");
		distance.setValue("1.074");
		
		WfsPointQuery pq = new WfsPointQuery(fGeo,new GeoJsfGetFeaturePropertyProvider(),layer,SampleSpatialEntity.class);
		List<SampleSpatialEntity> list = pq.execute(coordinates,distance);
//ahtutils.highlight:point
	}
	
	public static void main(String[] args)
	{
		Configuration config = GeoJsfUtilsTestBootstrap.init();
		CliPointQuery cli = new CliPointQuery(config);
		cli.pointQuery();
	}
}