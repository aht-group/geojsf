package org.geojsf.interfaces.model;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithGeometry <T extends Object> extends EjbWithId
{
	T getGeometry();
	void setGeometry(T geometry);
}