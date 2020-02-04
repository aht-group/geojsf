package org.geojsf.interfaces.model.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.with.EjbWithSldRules;
import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;

import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfSld<L extends JeeslLang, D extends JeeslDescription,
						   SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
						   SLDTYPE extends JeeslStatus<SLDTYPE,L,D>,
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