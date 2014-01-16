package org.geojsf.util.wfs;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;

import org.geojsf.interfaces.wfs.WfsGetFeaturePropertyProvider;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.model.SampleSpatialEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestWfsPointQuery extends AbstractGeoJsfUtilTest
{
	private WfsPointQuery<SampleSpatialEntity,DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView> wfsPq;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		WfsGetFeaturePropertyProvider wfsPp = new GeoJsfGetFeaturePropertyProvider();
		wfsPq = new WfsPointQuery<SampleSpatialEntity,DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView>
								(null,wfsPp,null,SampleSpatialEntity.class);
	}
	
	@Test
	public void test()
	{
		String expected = "the_geom";
		String actual = wfsPq.getGeometryColumnName(SampleSpatialEntity.class);
		Assert.assertEquals(expected, actual);
	}
}
