package org.geojsf.factory.ejb.sld;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoSldFactory<L extends UtilsLang,D extends UtilsDescription,T extends UtilsStatus<T,L,D>,SLD extends GeoJsfSld<L,D,T,SLD,RULE,SLDTEMPLATE>,RULE extends GeoJsfSldRule<L,D,T,SLD,RULE,SLDTEMPLATE>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,T,SLDTEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoSldFactory.class);
	
	final Class<SLD> cSld;
	
    public EjbGeoSldFactory(final Class<SLD> cSld)
    {
        this.cSld = cSld;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,T extends UtilsStatus<T,L,D>,SLD extends GeoJsfSld<L,D,T,SLD,RULE,SLDTEMPLATE>,RULE extends GeoJsfSldRule<L,D,T,SLD,RULE,SLDTEMPLATE>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,T,SLDTEMPLATE>>
    	EjbGeoSldFactory<L,D,T,SLD,RULE,SLDTEMPLATE> factory(final Class<SLD> cSld)
    {
        return new EjbGeoSldFactory<L,D,T,SLD,RULE,SLDTEMPLATE>(cSld);
    }
	
	public SLD build(SLDTEMPLATE template,T type)
	{
		SLD ejb;
		try {ejb = cSld.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();throw new RuntimeException(e);}
		catch (IllegalAccessException e) {e.printStackTrace();throw new RuntimeException(e);}
		ejb.setTemplate(template);
		ejb.setType(type);
        return ejb;
    }
}