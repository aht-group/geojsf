package org.geojsf.factory.ejb.meta;

import org.geojsf.interfaces.model.json.GeoJsfJsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoJsonDataFactory<JSON extends GeoJsfJsonData<?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoJsonDataFactory.class);
	
	private final Class<JSON> cJson;
        
    public EjbGeoJsonDataFactory(final Class<JSON> cJson)
    {
        this.cJson = cJson;
    }
	
	public JSON build()
	{
		JSON ejb = null;
		try
		{
			ejb = cJson.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
        return ejb;
    }
}