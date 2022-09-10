package org.geojsf.factory.ejb.meta;

import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoScaleFactory<SCALE extends GeoJsfScale<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoScaleFactory.class);
	
	private final Class<SCALE> cScale;
        
    public EjbGeoScaleFactory(final Class<SCALE> cScale)
    {

        this.cScale = cScale;
    }

	
	public SCALE build()
	{
		SCALE ejb = null;
		try
		{
			ejb = cScale.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
        return ejb;
    }
}