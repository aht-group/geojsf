package org.geojsf.factory.ejb.core;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoCategoryFactory<L extends JeeslLang,D extends JeeslDescription,
									
									CATEGORY extends GeoJsfCategory<L,D,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoCategoryFactory.class);
	
	final Class<CATEGORY> cCategory;
	
    public EjbGeoCategoryFactory(final Class<CATEGORY> cCategory)
    {
        this.cCategory = cCategory;
    }
	
	public CATEGORY build(String code)
	{
		CATEGORY ejb;
		try
		{
			ejb = cCategory.newInstance();
			ejb.setVisible(true);
			ejb.setCode(code);
		}
		catch (InstantiationException e) {e.printStackTrace();throw new RuntimeException(e);}
		catch (IllegalAccessException e) {e.printStackTrace();throw new RuntimeException(e);}
        return ejb;
    }
}