package org.geojsf.factory.ejb.sld;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoSldRuleFactory<L extends UtilsLang,D extends UtilsDescription,TYPE extends UtilsStatus<TYPE,L,D>,STYLE extends UtilsStatus<STYLE,L,D>,SLD extends GeoJsfSld<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,RULE extends GeoJsfSldRule<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,SLDTEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoSldRuleFactory.class);
	
	final Class<RULE> cRule;
	
    public EjbGeoSldRuleFactory(final Class<RULE> cRule)
    {
        this.cRule = cRule;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,TYPE extends UtilsStatus<TYPE,L,D>,STYLE extends UtilsStatus<STYLE,L,D>,SLD extends GeoJsfSld<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,RULE extends GeoJsfSldRule<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,SLDTEMPLATE>>
    	EjbGeoSldRuleFactory<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE> factory(final Class<RULE> cRule)
    {
        return new EjbGeoSldRuleFactory<L,D,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>(cRule);
    }
	
	public RULE build(SLD sld)
	{
		RULE ejb;
		try {ejb = cRule.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();throw new RuntimeException(e);}
		catch (IllegalAccessException e) {e.printStackTrace();throw new RuntimeException(e);}
		ejb.setSld(sld);
        return ejb;
    }
}