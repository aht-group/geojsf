package org.geojsf.factory.txt;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.geojsf.model.xml.geojsf.Scale;
import org.geojsf.model.xml.geojsf.Scales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtScalesFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtScalesFactory.class);
	
	private DecimalFormat df;
	
	public TxtScalesFactory()
	{
		df = new DecimalFormat("#");
		df.setRoundingMode(RoundingMode.HALF_EVEN);
	}
	
	public String build(Scales scales)
	{
		StringBuffer sb = new StringBuffer();
		for(Scale scale : scales.getScale())
		{
			sb.append(build(scale));
			sb.append(", ");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
	
	private String build(Scale scale)
	{
		if(scale.getValue() % 1 == 0)
		{
			return df.format(scale.getValue());
		}
		else
		{
			return ""+scale.getValue();
		}
	}
}