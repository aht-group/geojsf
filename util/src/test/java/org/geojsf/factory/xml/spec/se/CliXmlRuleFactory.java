package org.geojsf.factory.xml.spec.se;

import org.geojsf.factory.xml.specs.ogc.XmlFilterFactory;
import org.geojsf.factory.xml.specs.se.XmlGraphicFactory;
import org.geojsf.factory.xml.specs.se.XmlPointSymbolizerFactory;
import org.geojsf.factory.xml.specs.se.XmlRuleFactory;
import org.geojsf.factory.xml.specs.se.XmlSizeFactory;
import org.geojsf.model.xml.specs.se.Graphic;
import org.geojsf.model.xml.specs.se.PointSymbolizer;
import org.geojsf.model.xml.specs.se.Rule;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.geojsf.test.GeoJsfUtilsTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class CliXmlRuleFactory extends AbstractGeoJsfUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(CliXmlRuleFactory.class);
	
	public void interval()
	{
		Rule rule = XmlRuleFactory.build("myName","myTitle");
		rule.setFilter(XmlFilterFactory.interval("value", "lb1", 1, "ub1", 2));
		
		Graphic g = XmlGraphicFactory.mark("sqare");
		g.setSize(XmlSizeFactory.build("size", 6));
		
		PointSymbolizer ps = XmlPointSymbolizerFactory.build();
		ps.setGraphic(g);
		rule.setPointSymbolizer(ps);
		JaxbUtil.info(rule);
	}
	
	private void external()
	{
		Rule rule = XmlRuleFactory.build("myName","myTitle");
		rule.setFilter(XmlFilterFactory.equal("type_id", "1"));
		
		Graphic g = XmlGraphicFactory.external("burg02.svg");
		g.setSize(XmlSizeFactory.build("size", 12));
		
		PointSymbolizer ps = XmlPointSymbolizerFactory.build();
		ps.setGraphic(g);
		rule.setPointSymbolizer(ps);
		JaxbUtil.info(rule);
	}
	
	public static void main(String[] args)
	{
		GeoJsfUtilsTestBootstrap.init();
		CliXmlRuleFactory cli = new CliXmlRuleFactory();
//		cli.interval();
		cli.external();
	}
}