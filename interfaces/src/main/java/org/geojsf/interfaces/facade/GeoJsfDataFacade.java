package org.geojsf.interfaces.facade;

import org.geojsf.interfaces.util.geometry.EjbWithMultiPolygon;
import org.geojsf.interfaces.util.geometry.GeoJsfMultiPolygon;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;

public interface GeoJsfDataFacade <MP extends GeoJsfMultiPolygon>
					extends JeeslFacade
{		
//	<W extends EjbWithPolygonContainer<P>> P fPolygon(Class<W> c, W w) throws JeeslNotFoundException;
//	void x();
	
	<OWNER extends EjbWithMultiPolygon<MP>> MP fGeoMultiPolygon(Class<OWNER> cOwner, OWNER owner) throws JeeslNotFoundException;
}