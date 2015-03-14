package org.geojsf.interfaces.model.sld;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithPosition;

public interface GeoJsfSldRule<L extends UtilsLang,
						   D extends UtilsDescription,
						   T extends UtilsStatus<T,L,D>,
						   SLD extends GeoJsfSld<L,D,T,SLD,RULE,SLDTEMPLATE>,
						   RULE extends GeoJsfSldRule<L,D,T,SLD,RULE,SLDTEMPLATE>,
						   SLDTEMPLATE extends GeoJsfSldTemplate<L,D,T,SLDTEMPLATE>>
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