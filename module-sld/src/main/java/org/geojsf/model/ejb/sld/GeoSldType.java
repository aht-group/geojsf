package org.geojsf.model.ejb.sld;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.jeesl.interfaces.qualifier.er.EjbErNode;
import org.jeesl.model.ejb.io.graphic.core.IoGraphic;
import org.jeesl.model.ejb.io.locale.IoDescription;
import org.jeesl.model.ejb.io.locale.IoLang;
import org.jeesl.model.ejb.io.locale.IoStatus;

@Entity
@DiscriminatorValue("geoSldType")
@EjbErNode(name="Type",category="station",level=3)
public class GeoSldType extends IoStatus implements GeoJsfSldType<IoLang,IoDescription,GeoSldType,IoGraphic>
{
	public static final long serialVersionUID=1;

	@Override public List<String> getFixedCodes()
	{
		List<String> fixed = new ArrayList<String>();
		for(GeoJsfSldType.Type c : GeoJsfSldType.Type.values()){fixed.add(c.toString());}
		return fixed;
	}

	@Override public boolean equals(Object object) {return (object instanceof GeoSldType) ? id == ((GeoSldType) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(13,5).append(id).toHashCode();}
}