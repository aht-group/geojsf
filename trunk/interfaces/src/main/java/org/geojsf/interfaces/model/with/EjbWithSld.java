package org.geojsf.interfaces.model.with;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

import org.geojsf.interfaces.model.sld.GeoJsfSld;

public interface EjbWithSld <L extends UtilsLang,
							D extends UtilsDescription,
							TYPE extends UtilsStatus<TYPE,L,D>,
							SLD extends GeoJsfSld<L,D,TYPE>> extends EjbWithId
{
	SLD getSld();
	void setSld(SLD sld);
}