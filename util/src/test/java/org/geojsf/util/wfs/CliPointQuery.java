package org.geojsf.util.wfs;

import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.GeoJsfUtilsTestBootstrap;
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
	
	public CliPointQuery(Configuration config)
	{
		this.config=config;

	}
	
	public void pointQuery()
	{
		List<String> properties = new ArrayList<String>();
		properties.add("city_name");
		
		Coordinates coordinates = new Coordinates();
		coordinates.setValue("18.337941894531248,13.19704345703125");
		
		Distance distance = new Distance();
		distance.setUnits("degree");
		distance.setValue("1.074");
		
		GetFeature gf = PointQueryFactory.cGetFeature(config.getString("geoserver.test.pointquery.layer"),properties,coordinates,distance);
		
		WfsHttpRequest r = new WfsHttpRequest(config.getString(GeoServerConfigKeys.restHost)+"/wcs");
		JDomUtil.debug(r.request(gf));
	}
	
	public void wfsRequest()
	{
		WfsHttpRequest wfs = new WfsHttpRequest(config.getString(GeoServerConfigKeys.restHost));
	}
	
	public static void main(String[] args)
	{
		Configuration config = GeoJsfUtilsTestBootstrap.init();
		CliPointQuery cli = new CliPointQuery(config);
		cli.pointQuery();
//		cli.wfsRequest();
	}
}