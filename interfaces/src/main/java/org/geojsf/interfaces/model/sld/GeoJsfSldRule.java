package org.geojsf.interfaces.model.sld;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithPosition;

public interface GeoJsfSldRule<L extends UtilsLang,
						   D extends UtilsDescription,
						   TYPE extends UtilsStatus<TYPE,L,D>,
						   SLD extends GeoJsfSld<L,D,TYPE,SLD,RULE,TEMPLATE>,
						   RULE extends GeoJsfSldRule<L,D,TYPE,SLD,RULE,TEMPLATE>,
						   TEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,TEMPLATE>>
			extends EjbSaveable,EjbRemoveable,EjbWithPosition
{
	public static enum Type{symbol,interval}
	
	SLD getSld();
	void setSld(SLD sld);
	
	Double getLowerBound();
	void setLowerBound(Double lowerBound);
	
	Double getUpperBound();
	void setUpperBound(Double upperBound);
	
	String getColor();
	void setColor(String color);
	
	Integer getSize();
	void setSize(Integer size);
}