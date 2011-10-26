package net.sf.geojsf.util.factory;

import java.io.Serializable;

import net.sf.geojsf.xml.openlayers.Layer;
import net.sf.geojsf.xml.openlayers.Legend;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OlLegendFactory implements Serializable
{
	static Log logger = LogFactory.getLog(OlLegendFactory.class);
	
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
