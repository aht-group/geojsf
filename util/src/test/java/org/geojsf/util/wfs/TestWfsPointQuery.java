package org.geojsf.util.wfs;

import org.geojsf.interfaces.wfs.WfsGetFeaturePropertyProvider;
import org.geojsf.model.pojo.core.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.core.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.core.DefaultGeoJsfMap;
import org.geojsf.model.pojo.core.DefaultGeoJsfService;
import org.geojsf.model.pojo.core.DefaultGeoJsfView;
import org.geojsf.model.pojo.meta.DefaultGeoJsfDataSource;
import org.geojsf.model.pojo.meta.DefaultGeoJsfScale;
import org.geojsf.model.pojo.meta.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSld;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldRule;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldType;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphic;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicFigure;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicStyle;
import org.geojsf.model.pojo.util.symbol.DefaultGeoJsfGraphicType;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.model.SampleSpatialEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestWfsPointQuery extends AbstractGeoJsfUtilTest
{
	private WfsPointQuery<SampleSpatialEntity,SampleSpatialEntity,DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfGraphic,DefaultGeoJsfGraphicType,DefaultGeoJsfGraphicFigure,DefaultGeoJsfGraphicStyle,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldType,DefaultGeoJsfSldTemplate,DefaultGeoJsfSld,DefaultGeoJsfSldRule> wfsPq;
	
	@Before
	public void init()
	{
		DefaultGeoJsfService service = new DefaultGeoJsfService();
		
		DefaultGeoJsfLayer layer = new DefaultGeoJsfLayer(); 
		layer.setService(service);
		
		WfsGetFeaturePropertyProvider wfsPp = new GeoJsfGetFeaturePropertyProvider();
		wfsPq = new WfsPointQuery<SampleSpatialEntity,SampleSpatialEntity,DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfGraphic,DefaultGeoJsfGraphicType,DefaultGeoJsfGraphicFigure,DefaultGeoJsfGraphicStyle,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldType,DefaultGeoJsfSldTemplate,DefaultGeoJsfSld,DefaultGeoJsfSldRule>
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
