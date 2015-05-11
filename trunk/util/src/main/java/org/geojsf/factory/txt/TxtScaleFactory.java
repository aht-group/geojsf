package org.geojsf.factory.txt;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.geojsf.model.xml.geojsf.Scale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtScaleFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtScaleFactory.class);
	
	private DecimalFormat df;
	
	public TxtScaleFactory()
	{
		df = new DecimalFormat("#.0");
		df.setRoundingMode(RoundingMode.HALF_EVEN);
	}
	
	public String build(Scale scale)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" scale: ").append(df.format(scale.getValue()));
		sb.append(" [").append(scale.getUnit()).append("]");
		return sb.toString();
	}
}