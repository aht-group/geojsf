package org.geojsf.interfaces.model.sld;

import org.geojsf.interfaces.model.with.EjbWithSldRules;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfSld<L extends UtilsLang, D extends UtilsDescription,
							G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
							F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
						   SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
						   SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
						   SLD extends GeoJsfSld<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
						   RULE extends GeoJsfSldRule<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
			extends EjbSaveable,EjbRemoveable,
					EjbWithLang<L>,EjbWithDescription<D>,
					EjbWithSldRules<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
{
	public enum Attributes{type,template,library}
	
	SLDTYPE getType();
	void setType(SLDTYPE type);
	
	SLDTEMPLATE getTemplate();
	void setTemplate(SLDTEMPLATE template);
	
	Boolean getLibrary();
	void setLibrary(Boolean library);
	
		
	String getStatusClass();
	void setStatusClass(String statusClass);
	
	String getStatusAttribute();
	void setStatusAttribute(String statusAttribute);
}