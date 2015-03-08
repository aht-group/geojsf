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
						   SLD extends GeoJsfSld<L,D,T,SLD>>
			extends EjbSaveable,EjbRemoveable,EjbWithPosition
{
	public static enum Type{symbol,interval}
	
	SLD getSLD();
	void setSLD(SLD sld);
	
	double getLowerBound();
	void setLowerBound(double lowerBound);
	
	double getUpperBound();
	void setUpperBound(double upperBound);
	
	String getColor();
	void setColor(String color);
}