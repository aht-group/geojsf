package org.geojsf.util.wfs;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;

import org.geojsf.interfaces.wfs.WfsGetFeaturePropertyProvider;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfMap;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfService;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfView;
import org.geojsf.model.pojo.openlayers.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldType;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.model.SampleSpatialEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestWfsPointQuery extends AbstractGeoJsfUtilTest
{
	private WfsPointQuery<SampleSpatialEntity,SampleSpatialEntity,DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfSldType,DefaultGeoJsfSldTemplate> wfsPq;
	
	@Before
	public void init() throws UtilsIntegrityException
	{
		DefaultGeoJsfService service = new DefaultGeoJsfService();
		
		DefaultGeoJsfLayer layer = new DefaultGeoJsfLayer(); 
		layer.setService(service);
		
		WfsGetFeaturePropertyProvider wfsPp = new GeoJsfGetFeaturePropertyProvider();
		wfsPq = new WfsPointQuery<SampleSpatialEntity,SampleSpatialEntity,DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfSldType,DefaultGeoJsfSldTemplate>
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
