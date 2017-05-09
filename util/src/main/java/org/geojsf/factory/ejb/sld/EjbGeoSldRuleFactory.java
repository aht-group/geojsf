package org.geojsf.factory.ejb.sld;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoSldRuleFactory<L extends UtilsLang, D extends UtilsDescription,
									G extends JeeslGraphic<L,D,G,GT,FS>, GT extends UtilsStatus<GT,L,D>, FS extends UtilsStatus<FS,L,D>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									SLD extends GeoJsfSld<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>,
									RULE extends GeoJsfSldRule<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>
									>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoSldRuleFactory.class);
	
	final Class<RULE> cRule;
	
    public EjbGeoSldRuleFactory(final Class<RULE> cRule)
    {
        this.cRule = cRule;
    } 
    
    public static <L extends UtilsLang, D extends UtilsDescription,
					G extends JeeslGraphic<L,D,G,GT,FS>, GT extends UtilsStatus<GT,L,D>, FS extends UtilsStatus<FS,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,TYPE>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					STYLE extends UtilsStatus<STYLE,L,D>,
					SLD extends GeoJsfSld<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>,
					RULE extends GeoJsfSldRule<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>
					>
    	EjbGeoSldRuleFactory<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE> factory(final Class<RULE> cRule)
    {
        return new EjbGeoSldRuleFactory<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>(cRule);
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