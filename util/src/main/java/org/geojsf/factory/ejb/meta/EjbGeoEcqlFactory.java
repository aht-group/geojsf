package org.geojsf.factory.ejb.meta;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.meta.GeoJsfEcql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoEcqlFactory<LAYER extends GeoJsfLayer<?,?,?,?,?,?,?,?>,
								ECQL extends GeoJsfEcql<LAYER,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoEcqlFactory.class);
	
	final Class<ECQL> cEcql;
	
    public EjbGeoEcqlFactory(final Class<ECQL> cEcql)
    {
        this.cEcql = cEcql;
    }
		
	public ECQL build(LAYER layer)
	{
		ECQL ejb = null;
		try
		{
			ejb = cEcql.newInstance();
			ejb.setLayer(layer);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
        return ejb;
    }
}