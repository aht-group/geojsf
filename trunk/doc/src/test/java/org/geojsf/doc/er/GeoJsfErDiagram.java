package org.geojsf.doc.er;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.configuration.Configuration;
import org.geojsf.test.GeoJsfDocTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.er.AbstractErDiagram;
import net.sf.exlp.interfaces.util.ConfigKey;

public class GeoJsfErDiagram extends AbstractErDiagram
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfErDiagram.class);
	
	public GeoJsfErDiagram(File fTmp)
	{
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
		create("viewport");
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = GeoJsfDocTestBootstrap.init();
		
		File fTmp = new File(config.getString(ConfigKey.dirTmp));
		logger.info("Using Tmp: "+fTmp);
		
		GeoJsfErDiagram er = new GeoJsfErDiagram(fTmp);
		er.create();
	}
}
