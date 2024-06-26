package org.geojsf.interfaces.model.domain.area;

import java.io.Serializable;

import org.geojsf.interfaces.util.with.EjbWithGeometry;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

import com.vividsolutions.jts.geom.MultiPolygon;

public interface GeoBasin<L extends JeeslLang, D extends JeeslDescription,
							BASIN extends GeoBasin<L,D,BASIN,MODEL>,
							MODEL extends JeeslStatus<L,D,MODEL>>
			extends Serializable,EjbSaveable,
						EjbWithId,EjbWithLang<L>,EjbWithDescription<D>,EjbWithGeometry<MultiPolygon>
{
	MODEL getModel();
	void setModel(MODEL model);
	
	double getSize();
	void setSize(double size);
}