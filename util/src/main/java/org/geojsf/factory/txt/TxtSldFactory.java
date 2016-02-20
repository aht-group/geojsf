package org.geojsf.factory.txt;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;

import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtSldFactory<L extends UtilsLang,
							D extends UtilsDescription,
							G extends UtilsGraphic<L,D,G,GT,GS>,
							GT extends UtilsStatus<GT,L,D>,
							GS extends UtilsStatus<GS,L,D>,
							TYPE extends UtilsStatus<TYPE,L,D>,
							SLD extends GeoJsfSld<L,D,G,GT,GS,TYPE,SLD,RULE,SLDTEMPLATE>,
							RULE extends GeoJsfSldRule<L,D,G,GT,GS,TYPE,SLD,RULE,SLDTEMPLATE>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,SLDTEMPLATE>>
{	
	public TxtSldFactory()
	{

	}
	
	public String build(SLD sld)
	{
		GeoJsfSldType.Type type  = GeoJsfSldType.Type.valueOf(sld.getTemplate().getType().getCode());
		switch(type)
		{
			case interval: return interval(sld);
			default: return "";
		}
	}
	
	private String interval(SLD sld)
	{
		StringBuffer sb = new StringBuffer();
		int i=1;
		for(RULE rule : sld.getRules())
		{
			if(rule.getLowerBound()!=null){sb.append("lb").append(i).append(":").append(rule.getLowerBound()).append(";");}
			if(rule.getUpperBound()!=null){sb.append("ub").append(i).append(":").append(rule.getUpperBound()).append(";");}
			if(rule.getGraphic().getColor()!=null && rule.getGraphic().getColor().length()>0){sb.append("c").append(i).append(":#").append(rule.getGraphic().getColor()).append(";");}
			if(rule.getGraphic().getSize()!=null){sb.append("s").append(i).append(":").append(rule.getGraphic().getSize()).append(";");}
			i++;
		}
		
		if(sb.length()>1){return sb.substring(0,sb.length()-1);}
		else {return sb.toString();}
	}
}