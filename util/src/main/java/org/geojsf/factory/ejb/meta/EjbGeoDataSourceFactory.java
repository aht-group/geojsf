package org.geojsf.factory.ejb.meta;

import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoDataSourceFactory<L extends JeeslLang,D extends JeeslDescription,
								
								DS extends GeoJsfDataSource<L,D,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoDataSourceFactory.class);
	
	final Class<DS> cDs;
	private EjbLangFactory<L> fLang;
	private EjbDescriptionFactory<D> efDescription;
        
    public EjbGeoDataSourceFactory(final Class<L> cL, final Class<D> cD, final Class<DS> cDs)
    {
    	fLang = EjbLangFactory.factory(cL);
    	efDescription = EjbDescriptionFactory.factory(cD);
        this.cDs = cDs;
    }
	
	public DS build(String[] langKeys) throws JeeslConstraintViolationException
	{
		DS ejb;
		try
		{
			ejb = cDs.newInstance();
			ejb.setName(fLang.createEmpty(langKeys));
			ejb.setDescription(efDescription.createEmpty(langKeys));
		}
		catch (InstantiationException e) {throw new JeeslConstraintViolationException(e.getMessage());}
		catch (IllegalAccessException e) {throw new JeeslConstraintViolationException(e.getMessage());}
        return ejb;
    }
}