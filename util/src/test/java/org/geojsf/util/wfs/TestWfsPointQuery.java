package org.geojsf.util.wfs;

import org.geojsf.interfaces.util.wfs.WfsGetFeaturePropertyProvider;
import org.geojsf.model.pojo.geojsf.core.DfGeoJsfLayer;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfService;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.model.SampleSpatialEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestWfsPointQuery extends AbstractGeoJsfUtilTest
{
	private WfsPointQuery<SampleSpatialEntity,SampleSpatialEntity,DfGeoJsfLayer> wfsPq;
	
	@Before
	public void init()
	{
		DefaultGeoJsfService service = new DefaultGeoJsfService();
		
		DfGeoJsfLayer layer = new DfGeoJsfLayer(); 
		layer.setService(service);
		
		WfsGetFeaturePropertyProvider wfsPp = new GeoJsfGetFeaturePropertyProvider();
		wfsPq = new WfsPointQuery<SampleSpatialEntity,SampleSpatialEntity,DfGeoJsfLayer>
								(null,wfsPp,layer,SampleSpatialEntity.class,SampleSpatialEntity.class);
	}
	
	@Ignore @Test
	public void geometryColumn()
	{
		String expected = "the_geom";
		String actual = wfsPq.getGeometryColumnName(SampleSpatialEntity.class);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void propertyFields()
	{
		String[] expecteds = {"withoutColumn","withColumnName"};
		String[] actuals = wfsPq.getPropertyColumnNames(SampleSpatialEntity.class);
		Assert.assertEquals(2, actuals.length);
		Assert.assertArrayEquals(expecteds, actuals);
	}
}
