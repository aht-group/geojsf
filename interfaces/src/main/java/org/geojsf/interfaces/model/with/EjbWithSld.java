package org.geojsf.interfaces.model.with;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithSld <SLD extends GeoJsfSld<?,?,?,?,?>> extends EjbWithId
{
	public enum Attributes{sld}
	
	SLD getSld();
	void setSld(SLD sld);
}