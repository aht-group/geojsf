package org.geojsf.factory.svg;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FileUtils;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSld;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldRule;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldStyle;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.sld.DefaultGeoJsfSldType;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.geojsf.test.AbstractGeoJsfUtilTest;
import org.openfuxml.media.transcode.Svg2PngTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSvgSldRuleFactory
{
	final static Logger logger = LoggerFactory.getLogger(TestSvgSldRuleFactory.class);

	private SvgSldRuleFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfSldType,DefaultGeoJsfSldStyle,DefaultGeoJsfSld,DefaultGeoJsfSldRule,DefaultGeoJsfSldTemplate> svgF;
	
	public TestSvgSldRuleFactory()
	{
		svgF = SvgSldRuleFactory.factory();
	}
	
	public void test() throws IOException, TranscoderException
	{
		DefaultGeoJsfSldRule rule = new DefaultGeoJsfSldRule();
		for(int i=0;i<=12;i++)
		{
			rule.setSize(i);
			SVGGraphics2D g = svgF.build(12,rule);
			byte[] bytes = Svg2PngTranscoder.transcode(g);
			FileUtils.writeByteArrayToFile(new File("/Volumes/ramdisk/"+i+".png"), bytes);
		}
	}
	
	public static void main(String[] args) throws TranscoderException, IOException, ParserConfigurationException
	{
	  AbstractGeoJsfUtilTest.initLogger();
	  TestSvgSldRuleFactory test = new TestSvgSldRuleFactory();
	  test.test();
	}
}