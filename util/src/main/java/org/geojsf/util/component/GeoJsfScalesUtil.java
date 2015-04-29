package org.geojsf.util.component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.geojsf.xml.geojsf.Scale;
import org.geojsf.xml.geojsf.Scales;

public class GeoJsfScalesUtil
{
	private DecimalFormat df;
	private List<Double> list;
	
	public GeoJsfScalesUtil(Scales scales)
	{
		list = new ArrayList<Double>();
		for(Scale scale : scales.getScale())
		{
			list.add(scale.getValue());
		}
		Collections.sort(list);
	}
	
	public String getScaleList()
	{
		StringBuffer sb = new StringBuffer();
		for(double d : list)
		{
			sb.append(d);
			sb.append(", ");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
	
	public String getMin()
	{
		return df.format(list.get(0));
	}
	
	public String getMax()
	{
		return df.format(list.get(list.size()-1));
	}
}
