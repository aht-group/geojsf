package org.geojsf.interfaces.model.sld;

import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfSld<L extends UtilsLang,
						   D extends UtilsDescription,
						   G extends UtilsGraphic<L,D,GT,GS>,
						   GT extends UtilsStatus<GT,L,D>,
						   GS extends UtilsStatus<GS,L,D>,
						   SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
						   STYLE extends UtilsStatus<STYLE,L,D>,
						   SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTYPE,STYLE,SLD,RULE,SLDTEMPLATE>,
						   RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTYPE,STYLE,SLD,RULE,SLDTEMPLATE>,
						   SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,STYLE,SLDTEMPLATE>>
			extends EjbSaveable,EjbRemoveable,EjbWithLang<L>,EjbWithDescription<D>
{		
	SLDTEMPLATE getTemplate();
	void setTemplate(SLDTEMPLATE template);
	
	SLDTYPE getType();
	void setType(SLDTYPE type);
	
	List<RULE> getRules();
	void setRules(List<RULE> rules);
}