package org.geojsf.interfaces.model.sld;

import org.geojsf.interfaces.model.with.EjbWithSldRules;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfSld<L extends UtilsLang, D extends UtilsDescription,
						   G extends JeeslGraphic<L,D,G,GT,GS>,
						   GT extends UtilsStatus<GT,L,D>,
						   GS extends UtilsStatus<GS,L,D>,
						   SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,TYPE>,
						   TYPE extends UtilsStatus<TYPE,L,D>,
						   SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,TYPE,SLD,RULE>,
						   RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,TYPE,SLD,RULE>>
			extends EjbSaveable,EjbRemoveable,
					EjbWithLang<L>,EjbWithDescription<D>,
					EjbWithSldRules<L,D,G,GT,GS,SLDTEMPLATE,TYPE,SLD,RULE>
{
	public enum Attributes{type,template,library}
	
	TYPE getType();
	void setType(TYPE type);
	
	SLDTEMPLATE getTemplate();
	void setTemplate(SLDTEMPLATE template);
	
	Boolean getLibrary();
	void setLibrary(Boolean library);
	
	String getStatusClass();
	void setStatusClass(String statusClass);
	
}