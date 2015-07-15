package org.geojsf.factory.txt;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.SldRule;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtSldRuleFactory<L extends UtilsLang,D extends UtilsDescription,TYPE extends UtilsStatus<TYPE,L,D>,STYLE extends UtilsStatus<STYLE,L,D>,SLD extends GeoJsfSld<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,RULE extends GeoJsfSldRule<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,SLDTEMPLATE>>
{	
	private TxtSldRuleFactory()
	{

	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,TYPE extends UtilsStatus<TYPE,L,D>,STYLE extends UtilsStatus<STYLE,L,D>,SLD extends GeoJsfSld<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,RULE extends GeoJsfSldRule<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,SLDTEMPLATE>>

		TxtSldRuleFactory<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE> factory()
	{
		return new TxtSldRuleFactory<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>();
	}
	
	public String build(RULE rule)
	{
		return bounds(rule.getLowerBound(),rule.getUpperBound());
	}
	
	public static String bounds(SldRule rule)
	{
		Double lower = null;
		Double upper = null;
		if(rule.isSetLowerBound()){lower=rule.getLowerBound();}
		if(rule.isSetUpperBound()){upper=rule.getUpperBound();}
		return bounds(lower,upper);
	}
	
	private static String bounds(Double lower, Double upper)
	{
		StringBuffer sb = new StringBuffer();
		if(lower!=null && upper!=null)
		{
			sb.append(lower).append(" < x <= ").append(upper);
		}
		else if(lower==null && upper!=null)
		{
			sb.append("x <= ").append(upper);
		}
		else if(lower!=null && upper==null)
		{
			sb.append(lower).append(" < x");
		}
		else {sb.append("!!!");}
		return sb.toString();
	}
}