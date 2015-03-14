package org.geojsf.interfaces.model.with;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;

public interface EjbWithSld <L extends UtilsLang,
							D extends UtilsDescription,
							TYPE extends UtilsStatus<TYPE,L,D>,
							SLD extends GeoJsfSld<L,D,TYPE,SLD,RULE,SLDTEMPLATE>,
							RULE extends GeoJsfSldRule<L,D,TYPE,SLD,RULE,SLDTEMPLATE>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,SLDTEMPLATE>>
						extends EjbWithId
{
	SLD getSld();
	void setSld(SLD sld);
}