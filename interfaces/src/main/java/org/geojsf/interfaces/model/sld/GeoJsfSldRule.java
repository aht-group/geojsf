package org.geojsf.interfaces.model.sld;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfSldRule<L extends UtilsLang,
						   D extends UtilsDescription,
						   G extends UtilsGraphic<L,D,GT,GS>,
						   GT extends UtilsStatus<GT,L,D>,
						   GS extends UtilsStatus<GS,L,D>,
						   TYPE extends UtilsStatus<TYPE,L,D>,
						   STYLE extends UtilsStatus<STYLE,L,D>,
						   SLD extends GeoJsfSld<L,D,G,GT,GS,TYPE,STYLE,SLD,RULE,TEMPLATE>,
						   RULE extends GeoJsfSldRule<L,D,G,GT,GS,TYPE,STYLE,SLD,RULE,TEMPLATE>,
						   TEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,TEMPLATE>>
			extends EjbSaveable,EjbRemoveable,EjbWithPosition,EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Type{symbol,interval}
	public static enum Style{square,circle}
	
	SLD getSld();
	void setSld(SLD sld);
		
	Double getLowerBound();
	void setLowerBound(Double lowerBound);
	
	Double getUpperBound();
	void setUpperBound(Double upperBound);
	
	G getGraphic();
	void setGraphic(G graphic);
	
	//DEprecated
	STYLE getStyle();
	void setStyle(STYLE style);
	
	String getColor();
	void setColor(String color);
	
	Integer getSize();
	void setSize(Integer size);
}