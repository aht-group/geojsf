package org.geojsf.factory.ejb.core;

import org.geojsf.interfaces.model.core.GeoJsfService;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoServiceFactory<L extends JeeslLang,D extends JeeslDescription,
								SERVICE extends GeoJsfService<L,D,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoServiceFactory.class);
	
	final Class<SERVICE> clService;
	
    public EjbGeoServiceFactory(final Class<SERVICE> clService)
    {
        this.clService = clService;
    }
	
	public SERVICE build(String code, String wms) throws JeeslConstraintViolationException
	{
		SERVICE ejb;
		try {ejb = clService.newInstance();}
		catch (InstantiationException e) {throw new JeeslConstraintViolationException(e.getMessage());}
		catch (IllegalAccessException e) {throw new JeeslConstraintViolationException(e.getMessage());}
		ejb.setWms(wms);
		ejb.setCode(code);
        return ejb;
    }
}