package org.geojsf.util.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.geojsf.model.xml.geojsf.Scale;
import org.geojsf.model.xml.geojsf.Scales;

public class GeoJsfScalesUtil
{
	private List<Integer> list;
	
	public GeoJsfScalesUtil(Scales scales)
	{	
		// Prepare the list of values for retrieving min and max values
		list = new ArrayList<Integer>();
		for(Scale scale : scales.getScale())
		{
			list.add(scale.getValue());
		}
		Collections.sort(list);
	}
	
	public String getScaleList()
	{
		StringBuffer sb = new StringBuffer();
		for(Integer d : list)
		{
			sb.append(d);
			sb.append(", ");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
	
	public String getMax()
	{
		return list.get(0).toString();
	}
	
	public String getMin()
	{
		return list.get(list.size()-1).toString();
	}
}