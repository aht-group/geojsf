package org.geojsf.interfaces.model.area;

import java.io.Serializable;

import org.geojsf.interfaces.model.with.EjbWithGeometry;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.system.locale.EjbWithDescription;
import org.jeesl.interfaces.model.with.system.locale.EjbWithLang;

import com.vividsolutions.jts.geom.MultiPolygon;

public interface GeoBasin<L extends JeeslLang, D extends JeeslDescription,
							BASIN extends GeoBasin<L,D,BASIN,MODEL>,
							MODEL extends JeeslStatus<MODEL,L,D>>
			extends Serializable,EjbWithId,EjbWithLang<L>,EjbWithDescription<D>,EjbWithGeometry<MultiPolygon>
{
	MODEL getModel();
	void setModel(MODEL model);
	
	double getSize();
	void setSize(double size);
}