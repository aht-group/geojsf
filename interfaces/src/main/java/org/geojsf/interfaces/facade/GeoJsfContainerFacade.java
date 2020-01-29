package org.geojsf.interfaces.facade;

import org.geojsf.interfaces.model.with.EjbWithGeometry;
import org.geojsf.interfaces.model.with.container.EjbWithPolygonContainer;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;

import com.vividsolutions.jts.geom.MultiPolygon;

public interface GeoJsfContainerFacade <P extends EjbWithGeometry<MultiPolygon>>
					extends JeeslFacade
{		
	<W extends EjbWithPolygonContainer<P>> P fPolygon(Class<W> c, W w) throws JeeslNotFoundException;
}