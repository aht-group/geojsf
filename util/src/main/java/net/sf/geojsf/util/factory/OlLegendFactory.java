package net.sf.geojsf.util.factory;

import java.io.Serializable;


import org.geojsf.xml.openlayers.Layer;
import org.geojsf.xml.openlayers.Legend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OlLegendFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(OlLegendFactory.class);
	
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	

	public static Legend createLegend(String wmsUrl, Layer layer)
	{
		Legend legend = new Legend();
		
		StringBuffer url = new StringBuffer();
		url.append(wmsUrl);
		url.append("REQUEST=GetLegendGraphic");
		url.append("&VERSION=1.0.0");
		url.append("&FORMAT=image/png");
		url.append("&WIDTH=20");
		url.append("&HEIGHT=20");
		url.append("&LAYER="+layer.getWorkspace()+":"+layer.getCode());
		legend.setUrl(url.toString());
		
		return legend;
	}
}
