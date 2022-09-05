package org.geojsf.factory.ejb.sld;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoSldRuleFactory<
									SLD extends GeoJsfSld<?,?,?,?,RULE>,
									RULE extends GeoJsfSldRule<?,?,?>
									>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoSldRuleFactory.class);
	
	final Class<RULE> cRule;
	
    public EjbGeoSldRuleFactory(final Class<RULE> cRule)
    {
        this.cRule = cRule;
    }
	
	public RULE build(SLD sld)
	{
		RULE ejb;
		try
		{
			ejb = cRule.newInstance();
//			ejb.setSld(sld);
		}
		catch (InstantiationException e) {e.printStackTrace();throw new RuntimeException(e);}
		catch (IllegalAccessException e) {e.printStackTrace();throw new RuntimeException(e);}

        return ejb;
    }
}