package org.geojsf.factory.txt;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;

public class TxtSldFactory<L extends JeeslLang, D extends JeeslDescription,
							G extends JeeslGraphic<?,GC,?>, GC extends JeeslGraphicComponent<G,GC,?>,
							SDX extends GeoJsfSldXml<L,D,SLD>,
							TYPE extends GeoJsfSldType<L,D,TYPE,G>,
							SLD extends GeoJsfSld<L,D,SDX,TYPE,RULE,?,?>,
							RULE extends GeoJsfSldRule<L,D,G>
							>
{	
	public TxtSldFactory()
	{

	}
	
	public String build(SLD sld)
	{
		return interval(sld);
	}
	
	private String interval(SLD sld)
	{
		StringBuffer sb = new StringBuffer();
		int i=1;
		for(RULE rule : sld.getRules())
		{
			sb.append("lb").append(i).append(":");
			if(rule.getLowerBound()!=null){sb.append(rule.getLowerBound());}
			else {sb.append("-99999999");}
			sb.append(";");
			
			sb.append("ub").append(i).append(":");
			if(rule.getUpperBound()!=null){sb.append(rule.getUpperBound());}
			else {sb.append("99999999");}
			sb.append(";");
			
			if(rule.getGraphic().getColor()!=null && rule.getGraphic().getColor().length()>0){sb.append("c").append(i).append(":#").append(rule.getGraphic().getColor()).append(";");}
			if(rule.getGraphic().getSize()!=null){sb.append("s").append(i).append(":").append(rule.getGraphic().getSize()).append(";");}
			i++;
		}
		
		if(sb.length()>1){return sb.substring(0,sb.length()-1);}
		else {return sb.toString();}
	}
}