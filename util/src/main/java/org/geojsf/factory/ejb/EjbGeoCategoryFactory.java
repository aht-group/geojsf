package org.geojsf.factory.ejb;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbGeoCategoryFactory<L extends UtilsLang,D extends UtilsDescription,
									
									CATEGORY extends GeoJsfCategory<L,D,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoCategoryFactory.class);
	
	final Class<CATEGORY> cCategory;
	
    public EjbGeoCategoryFactory(final Class<CATEGORY> cCategory)
    {
        this.cCategory = cCategory;
    }
    
    public static <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,?>>
    	EjbGeoCategoryFactory<L,D,CATEGORY> factory(final Class<CATEGORY> cCategory)
    {
        return new EjbGeoCategoryFactory<L,D,CATEGORY>(cCategory);
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