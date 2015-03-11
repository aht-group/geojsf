package org.geojsf.factory.txt;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;

public class TxtSldFactory<L extends UtilsLang,
D extends UtilsDescription,
T extends UtilsStatus<T,L,D>,
SLD extends GeoJsfSld<L,D,T,SLD,RULE>,
RULE extends GeoJsfSldRule<L,D,T,SLD,RULE>>
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