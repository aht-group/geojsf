package org.geojsf.interfaces.model.with;

import java.util.List;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithSldRules <L extends UtilsLang,
							D extends UtilsDescription,
							G extends JeeslGraphic<L,D,G,GT,GS>,
							GT extends UtilsStatus<GT,L,D>,
							GS extends UtilsStatus<GS,L,D>,
							TEMPLATE extends GeoJsfSldTemplate<L,D,TEMPLATE,TYPE>,
							TYPE extends UtilsStatus<TYPE,L,D>,
							SLD extends GeoJsfSld<L,D,G,GT,GS,TEMPLATE,TYPE,SLD,RULE>,
							RULE extends GeoJsfSldRule<L,D,G,GT,GS,TEMPLATE,TYPE,SLD,RULE>>
						extends EjbWithId
{
	List<RULE> getRules();
	void setRules(List<RULE> rules);
}