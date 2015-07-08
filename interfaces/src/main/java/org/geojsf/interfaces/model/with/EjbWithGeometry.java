package org.geojsf.interfaces.model.with;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithGeometry <T extends Object> extends EjbWithId
{
	T getGeometry();
	void setGeometry(T geometry);
}