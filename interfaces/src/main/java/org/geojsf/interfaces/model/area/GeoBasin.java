package org.geojsf.interfaces.model.area;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

import java.io.Serializable;

import org.geojsf.interfaces.model.with.EjbWithGeometry;

import com.vividsolutions.jts.geom.MultiPolygon;

public interface GeoBasin<L extends UtilsLang, D extends UtilsDescription,
							BASIN extends GeoBasin<L,D,BASIN,MODEL>,
							MODEL extends UtilsStatus<MODEL,L,D>>
			extends Serializable,EjbWithId,EjbWithLang<L>,EjbWithDescription<D>,EjbWithGeometry<MultiPolygon>
{
	MODEL getModel();
	void setModel(MODEL model);
	
	double getSize();
	void setSize(double size);
}