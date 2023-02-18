package org.geojsf.factory.ejb.sld;

import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoSldTemplateFactory<SDX extends GeoJsfSldXml<?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoSldTemplateFactory.class);
	
	final Class<SDX> cTemplate;
	
    public EjbGeoSldTemplateFactory(final Class<SDX> cTemplate)
    {
        this.cTemplate = cTemplate;
    }
	
	public SDX build(String code)
	{
		SDX ejb;
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