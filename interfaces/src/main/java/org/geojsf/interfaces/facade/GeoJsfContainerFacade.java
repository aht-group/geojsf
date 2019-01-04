package org.geojsf.interfaces.facade;

import org.geojsf.interfaces.model.with.EjbWithGeometry;
import org.geojsf.interfaces.model.with.container.EjbWithPolygonContainer;

import com.vividsolutions.jts.geom.MultiPolygon;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public interface GeoJsfContainerFacade <P extends EjbWithGeometry<MultiPolygon>>
					extends UtilsFacade
{		
	<W extends EjbWithPolygonContainer<P>> P fPolygon(Class<W> c, W w) throws UtilsNotFoundException;
}