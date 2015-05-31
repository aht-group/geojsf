package org.geojsf.factory.txt;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;

public class TxtSldFactory<L extends UtilsLang,D extends UtilsDescription,TYPE extends UtilsStatus<TYPE,L,D>,STYLE extends UtilsStatus<STYLE,L,D>,SLD extends GeoJsfSld<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,RULE extends GeoJsfSldRule<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,SLDTEMPLATE>>
{	
	public TxtSldFactory()
	{

	}
	
	public String build(SLD sld)
	{
		StringBuffer sb = new StringBuffer();
		int i=1;
		for(RULE rule : sld.getRules())
		{
			if(rule.getLowerBound()!=null){sb.append("lb").append(i).append(":").append(rule.getLowerBound()).append(";");}
			if(rule.getUpperBound()!=null){sb.append("ub").append(i).append(":").append(rule.getUpperBound()).append(";");}
			if(rule.getColor()!=null && rule.getColor().length()>0){sb.append("c").append(i).append(":").append(rule.getColor()).append(";");}
			if(rule.getSize()!=null){sb.append("s").append(i).append(":").append(rule.getSize()).append(";");}
			i++;
		}
		
		if(sb.length()>1){return sb.substring(0,sb.length()-1);}
		else {return sb.toString();}
	}
}