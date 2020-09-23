package org.geojsf.helpers;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.geojsf.component.MapUtil;
import org.geojsf.model.xml.geojsf.Scales;
import org.geojsf.util.component.GeoJsfScalesUtil;

public class ControlManager {
    
    public static void processScale(UIComponent map, FacesContext ctx) throws IOException
    {
	// Process scales definition
	Scales scales = MapUtil.searchScale(map); 
	ResponseWriter writer = ctx.getResponseWriter();
	if (scales!=null)
	{
	    // Define all allowed levels
	    String scaleDefinitions = "options.scales = [";
	    GeoJsfScalesUtil scalesUtil = new GeoJsfScalesUtil(scales);

	    scaleDefinitions += scalesUtil.getScaleList();
	    scaleDefinitions += "];" +System.getProperty("line.separator");
	    writer.writeText(scaleDefinitions, null);

	    // Define minimum and maximum values
	    writer.writeText("options.maxScale = "+scalesUtil.getMax() +";" +System.getProperty("line.separator"), null);
	    writer.writeText("options.minScale = "+scalesUtil.getMin() +";" +System.getProperty("line.separator"), null);

	    // Set the unit
	    writer.writeText("options.units = '" +scales.getUnit() +"';" +System.getProperty("line.separator"), null);
	}
    }
}
