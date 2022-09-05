package org.geojsf.factory.ejb.sld;

import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoSldTemplateFactory<TEMPLATE extends GeoJsfSldTemplate<?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoSldTemplateFactory.class);
	
	final Class<TEMPLATE> cTemplate;
	
    public EjbGeoSldTemplateFactory(final Class<TEMPLATE> cTemplate)
    {
        this.cTemplate = cTemplate;
    }
	
	public TEMPLATE build(String code)
	{
		TEMPLATE ejb;
		try
		{
			ejb = cTemplate.newInstance();
			ejb.setCode(code);
		}
		catch (InstantiationException e) {e.printStackTrace();throw new RuntimeException(e);}
		catch (IllegalAccessException e) {e.printStackTrace();throw new RuntimeException(e);}
        return ejb;
    }
}