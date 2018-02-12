package org.geojsf.interfaces.model.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.with.EjbWithSldRules;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfSld<L extends UtilsLang, D extends UtilsDescription,
						   SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
						   SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
						   RULE extends GeoJsfSldRule<L,D,?>
						   >
			extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,
					EjbWithLang<L>,EjbWithDescription<D>,
					EjbWithSldRules<RULE>
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