package org.geojsf.util.wfs;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.configuration.Configuration;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.GeoJsfUtilsTestBootstrap;
import org.geojsf.util.GeoServerConfigKeys;
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
		OpenLayers ol = new OpenLayers(config,"Dam_sites");
		ol.getSearchRadius().setPixel(8);
		
		List<String> properties = new ArrayList<String>();
		properties.add("dam_name");
		String layerCode = "dam_sites";
		GetFeature gf = PointQueryFactory.cGetFeature("LCBC:"+layerCode,properties,ol.getClick().toGmlCoordinates(),ol.createSearchDistance());	
//		JaxbUtil.debug(gf, new LisNsPrefixMapper());
		WfsHttpRequest r = new WfsHttpRequest(ol.getWmsLayer().getWcsForLayer(layerCode));
		List<Integer> lIds = r.ejbIdRequest(gf,"LCBC",layerCode);
	}
	
	public void wfsRequest()
	{
		WfsHttpRequest wfs = new WfsHttpRequest(config.getString(GeoServerConfigKeys.restHost));
	}
	
	public static void main(String[] args)
	{
		Configuration config = GeoJsfUtilsTestBootstrap.init();
		CliPointQuery cli = new CliPointQuery(config);
//		cli.pointQuery();
		cli.wfsRequest();
	}
}