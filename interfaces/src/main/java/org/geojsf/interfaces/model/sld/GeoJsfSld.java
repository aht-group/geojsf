package org.geojsf.interfaces.model.sld;

import java.io.Serializable;

import org.geojsf.interfaces.util.with.EjbWithSldRules;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslAttributes;
import org.jeesl.interfaces.qualifier.rest.option.DownloadJeeslDescription;

@DownloadJeeslDescription
@DownloadJeeslAttributes
public interface GeoJsfSld<L extends JeeslLang, D extends JeeslDescription,
						   SDX extends GeoJsfSldXml<L,D,?>,
						   SDT extends GeoJsfSldType<L,D,SDT,?>,
						   SDR extends GeoJsfSldRule<L,D,?>,
						   LE extends JeeslRevisionEntity<L,D,?,?,LA,?>,
						   LA extends JeeslRevisionAttribute<L,D,LE,?,?>
						   >
			extends Serializable,EjbRemoveable,EjbPersistable,EjbSaveable,
					EjbWithLang<L>,EjbWithDescription<D>,
					EjbWithSldRules<SDR>
{
	public enum Attributes{type,template,library}
	
	SDT getType();
	void setType(SDT type);
	
	Boolean getLibrary();
	void setLibrary(Boolean library);
	
	SDX getTemplate();
	void setTemplate(SDX template);
	
	String getStatusClass();
	void setStatusClass(String statusClass);
	
	String getStatusAttribute();
	void setStatusAttribute(String statusAttribute);
	
	LE getEntity();
	void setEntity(LE entity);
	
	LA getAttribute();
	void setAttribute(LA attribute);
}