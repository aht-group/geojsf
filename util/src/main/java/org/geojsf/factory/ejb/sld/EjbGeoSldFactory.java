package org.geojsf.factory.ejb.sld;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoSldFactory <TYPE extends GeoJsfSldType<?,?,TYPE,?>,
								SLD extends GeoJsfSld<?,?,?,TYPE,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoSldFactory.class);
	
	final Class<SLD> cSld;
	
    public EjbGeoSldFactory(final Class<SLD> cSld)
    {
        this.cSld = cSld;
    } 
	
	public SLD build(TYPE type, boolean library)
	{
		SLD ejb;
		try
		{
			ejb = cSld.newInstance();
			ejb.setType(type);
			ejb.setLibrary(library);
		}
		catch (InstantiationException e) {e.printStackTrace();throw new RuntimeException(e);}
		catch (IllegalAccessException e) {e.printStackTrace();throw new RuntimeException(e);}
        return ejb;
    }
}