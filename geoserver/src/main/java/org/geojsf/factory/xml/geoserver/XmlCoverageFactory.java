package org.geojsf.factory.xml.geoserver;

import java.io.Serializable;

import org.geojsf.geoserver.util.SimpleXmlTranscoder;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCoverageFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlCoverageFactory.class);
	public static final long serialVersionUID=1;
	
	public static String eName = "coverageStore";
	
	public static void transform(Element eCoverage)
	{
		logger.info("Transforming ... ");
		SimpleXmlTranscoder.name(eCoverage);
	}
}
