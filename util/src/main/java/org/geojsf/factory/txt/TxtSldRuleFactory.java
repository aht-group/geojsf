package org.geojsf.factory.txt;

import java.util.Objects;

import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.model.xml.geojsf.SldRule;

public class TxtSldRuleFactory<RULE extends GeoJsfSldRule<?,?,?>>
{	
	private TxtSldRuleFactory()
	{

	}
	
	public static <RULE extends GeoJsfSldRule<?,?,?>>
		TxtSldRuleFactory<RULE> factory()
	{
		return new TxtSldRuleFactory<RULE>();
	}
	
	public String build(RULE rule)
	{
		return bounds(rule.getLowerBound(),rule.getUpperBound());
	}
	
	public static String bounds(SldRule rule)
	{
		Double lower=null; if(Objects.nonNull(rule.getLowerBound())) {lower=rule.getLowerBound();}
		Double upper=null; if(Objects.nonNull(rule.getUpperBound())) {upper=rule.getUpperBound();}
		return bounds(lower,upper);
	}
	
	private static String bounds(Double lower, Double upper)
	{
		StringBuffer sb = new StringBuffer();
		if(lower!=null && upper!=null)
		{
			sb.append(fmt(lower)).append(" < x <= ").append(fmt(upper));
		}
		else if(lower==null && upper!=null)
		{
			sb.append("x <= ").append(fmt(upper));
		}
		else if(lower!=null && upper==null)
		{
			sb.append(fmt(lower)).append(" < x");
		}
		else {sb.append("!!!");}
		return sb.toString();
	}
	
	public static String fmt(Double dd)
	{
		double d = dd;
	    if(d == (long) d)
	        return String.format("%d",(long)d);
	    else
	        return String.format("%s",d);
	}
}