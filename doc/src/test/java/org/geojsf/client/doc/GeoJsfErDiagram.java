package org.geojsf.client.doc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.configuration.Configuration;
import org.geojsf.test.GeoJsfDocTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.er.AbstractErDiagram;

public class GeoJsfErDiagram extends AbstractErDiagram
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfErDiagram.class);
	
	public GeoJsfErDiagram(Configuration config)
	{
		super(config,null);
		fDot = new File(fTmp,"er.dot");
		fSrc = new File("../entities/src/main/java");
		fSvg = new File("src/main/resources/svg.geojsf/er");
		dPdf = null;//new File("src/main/latex/common/pdf/admin/development/er");
		
		packages = "org/geojsf/model/pojo";
		colorScheme = "config.geojsf-doc.test/er-colorScheme.xml";
	}
	
	public void create() throws IOException, ClassNotFoundException, TranscoderException
	{
		buildSvg("neato",new ArrayList<String>(),new File(fSvg,"all.svg"),null);
		create("core");
		create("datasource");
		create("viewport");
		create("sld");
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = GeoJsfDocTestBootstrap.init();
		
		GeoJsfErDiagram er = new GeoJsfErDiagram(config);
		er.create();
	}
}