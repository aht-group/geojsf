package org.geojsf.factory.txt;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.SldRule;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtSldRuleFactory<L extends UtilsLang, D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,TYPE>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,TYPE,RULE>,
								RULE extends GeoJsfSldRule<L,D,G>
								>
{	
	private TxtSldRuleFactory()
	{

	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,TYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,TYPE>>
		TxtSldRuleFactory<L,D,G,GT,F,FS,SLDTEMPLATE,TYPE,SLD,RULE> factory()
	{
		return new TxtSldRuleFactory<L,D,G,GT,F,FS,SLDTEMPLATE,TYPE,SLD,RULE>();
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