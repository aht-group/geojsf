package org.geojsf.factory.ejb.core;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbGeoLayerFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,?,?>
								>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoLayerFactory.class);
	
	private EjbLangFactory<L> efLang;
	
	final Class<LAYER> cLayer;
	
    public EjbGeoLayerFactory(final Class<L> cL, final Class<LAYER> cLayer)
    {
    	efLang = EjbLangFactory.factory(cL);
        this.cLayer = cLayer;
    } 
	
	public LAYER build(String code, SERVICE service, CATEGORY category, String[] langKeys) throws JeeslConstraintViolationException
	{
		LAYER ejb;
		try
		{
			ejb = cLayer.newInstance();
			ejb.setVisible(true);
			ejb.setName(efLang.createEmpty(langKeys));
			ejb.setCode(code);
			ejb.setService(service);
			ejb.setCategory(category);
		}
		catch (InstantiationException e) {throw new JeeslConstraintViolationException(e.getMessage());}
		catch (IllegalAccessException e) {throw new JeeslConstraintViolationException(e.getMessage());}

        return ejb;
    }
}