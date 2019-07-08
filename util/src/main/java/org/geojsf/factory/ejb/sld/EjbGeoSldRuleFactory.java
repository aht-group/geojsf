package org.geojsf.factory.ejb.sld;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoSldRuleFactory<L extends UtilsLang, D extends UtilsDescription,
									G extends JeeslGraphic<L,D,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
									F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,G>
									>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoSldRuleFactory.class);
	
	final Class<RULE> cRule;
	
    public EjbGeoSldRuleFactory(final Class<RULE> cRule)
    {
        this.cRule = cRule;
    } 
    
    public static <L extends UtilsLang, D extends UtilsDescription,
				    G extends JeeslGraphic<L,D,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					STYLE extends UtilsStatus<STYLE,L,D>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>
					>
    	EjbGeoSldRuleFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> factory(final Class<RULE> cRule)
    {
        return new EjbGeoSldRuleFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(cRule);
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