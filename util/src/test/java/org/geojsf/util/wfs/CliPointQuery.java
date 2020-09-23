package org.geojsf.util.wfs;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.geojsf.model.pojo.core.DefaultGeoJsfLayer;
import org.geojsf.model.xml.specs.gml.Coordinates;
import org.geojsf.model.xml.specs.ogc.Distance;
import org.geojsf.model.xml.specs.wfs.GetFeature;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.GeoJsfUtilsTestBootstrap;
import org.geojsf.test.model.SampleSpatialEntity;
import org.jeesl.interfaces.facade.JeeslIdFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class CliPointQuery extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(CliPointQuery.class);
	
	private Configuration config;
	private DefaultGeoJsfLayer layer;
	
	public CliPointQuery(Configuration config)
	{
		this.config=config;
	}
	
	private JeeslIdFacade getGeoFacade()
	{
		return null;
	}
	
	public void pointQuery()
	{
		String[] properties = {"NAME"};
		
		Coordinates coordinates = new Coordinates();
		coordinates.setValue("8,17");
		
		// Unit ignored: http://jira.codehaus.org/browse/GEOS-937
		Distance distance = new Distance();
		distance.setUnits("degree");
		distance.setValue("99000");
		
		GetFeature gf = PointQueryFactory.cGetFeature("tiger:poi",properties,"the_geom",coordinates,distance);
		
		JaxbUtil.info(gf);
		
		WfsHttpRequest r = new WfsHttpRequest("http://localhost:8080/geoserver/wcs");
		JDomUtil.debug(r.request(gf));
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void genericPointQuery()
	{
//jeesl.highlight:point
		JeeslIdFacade fGeo = getGeoFacade();
		
		Coordinates coordinates = new Coordinates();
		coordinates.setValue("5.4,6.2");
		
		Distance distance = new Distance();
		distance.setUnits("degree");
		distance.setValue("1.074");
		
		WfsPointQuery pq = new WfsPointQuery(fGeo,new GeoJsfGetFeaturePropertyProvider(),layer,SampleSpatialEntity.class,SampleSpatialEntity.class);
		List<SampleSpatialEntity> list = pq.execute(coordinates,distance);
//jeesl.highlight:point
	}
	
	public static void main(String[] args)
	{
		Configuration config = GeoJsfUtilsTestBootstrap.init();
		CliPointQuery cli = new CliPointQuery(config);
		cli.pointQuery();
	}
}