package org.geojsf.factory.txt;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;

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
		StringBuffer sb = new StringBuffer();
		if(rule.getLowerBound()!=null && rule.getUpperBound()!=null)
		{
			sb.append(rule.getLowerBound()).append(" < x <= ").append(rule.getUpperBound());
		}
		else if(rule.getLowerBound()==null && rule.getUpperBound()!=null)
		{
			sb.append("x <= ").append(rule.getUpperBound());
		}
		else if(rule.getLowerBound()!=null && rule.getUpperBound()==null)
		{
			sb.append(rule.getLowerBound()).append(" < x");
		}
		else {sb.append("!!!");}
		return sb.toString();
	}
}