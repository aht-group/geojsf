package org.geojsf.interfaces.model.with;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.symbol.UtilsGraphic;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithSld <L extends UtilsLang,
							D extends UtilsDescription,
							G extends UtilsGraphic<L,D,GT,GS>,
							GT extends UtilsStatus<GT,L,D>,
							GS extends UtilsStatus<GS,L,D>,
							TYPE extends UtilsStatus<TYPE,L,D>,
							STYLE extends UtilsStatus<STYLE,L,D>,
							SLD extends GeoJsfSld<L,D,G,GT,GS,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,
							RULE extends GeoJsfSldRule<L,D,G,GT,GS,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,SLDTEMPLATE>>
						extends EjbWithId
{
	SLD getSld();
	void setSld(SLD sld);
}