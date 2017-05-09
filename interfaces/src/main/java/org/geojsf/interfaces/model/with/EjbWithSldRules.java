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

public interface EjbWithSldRules <L extends UtilsLang, D extends UtilsDescription,
							G extends JeeslGraphic<L,D,G,GT,FS>,
							GT extends UtilsStatus<GT,L,D>,
							FS extends UtilsStatus<FS,L,D>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLD extends GeoJsfSld<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							RULE extends GeoJsfSldRule<L,D,G,GT,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
						extends EjbWithId
{
	List<RULE> getRules();
	void setRules(List<RULE> rules);
}