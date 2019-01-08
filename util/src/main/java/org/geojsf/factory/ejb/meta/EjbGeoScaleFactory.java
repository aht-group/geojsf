package org.geojsf.factory.ejb.meta;

import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbGeoScaleFactory<L extends UtilsLang,D extends UtilsDescription,
								SCALE extends GeoJsfScale<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoScaleFactory.class);
	
	private final Class<SCALE> cScale;
	private EjbLangFactory<L> fLang;
	private EjbDescriptionFactory<D> efDescription;
        
    public EjbGeoScaleFactory(final Class<L> cL, final Class<D> cD, final Class<SCALE> cScale)
    {
    	fLang = EjbLangFactory.factory(cL);
    	efDescription = EjbDescriptionFactory.factory(cD);
        this.cScale = cScale;
    }
	
	public SCALE build(String[] langKeys)
	{
		SCALE ejb = null;
		try
		{
			ejb = cScale.newInstance();
			ejb.setName(fLang.createEmpty(langKeys));
			ejb.setDescription(efDescription.createEmpty(langKeys));
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
        return ejb;
    }
}