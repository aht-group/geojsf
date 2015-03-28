package org.geojsf.util.wfs;

import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestDefaultWfsFeatureIdResolver extends AbstractGeoJsfUtilTest
{
	private Element e1,e2;
	private String layerCode = "dams";
	private Namespace ns;
	private long id = 20;
	
	@Before
	public void init()
	{
		ns = Namespace.getNamespace("my.Prefix", "http://my.ns.org");
		
		layerCode = "dams";
		e1 = new Element("entity",ns);
		e1.setAttribute("fid",layerCode+"."+id);
	}
	
	@Test
	public void geoserver2()
	{
		DefaultWfsFeatureIdResolver idr = new DefaultWfsFeatureIdResolver(layerCode);
		Assert.assertEquals(id, idr.getId(e1));
	}
}
