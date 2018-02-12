package org.geojsf.interfaces.model.with;

import org.geojsf.interfaces.model.sld.GeoJsfSld;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithSld <SLD extends GeoJsfSld<?,?,?,?,?>> extends EjbWithId
{
	SLD getSld();
	void setSld(SLD sld);
}