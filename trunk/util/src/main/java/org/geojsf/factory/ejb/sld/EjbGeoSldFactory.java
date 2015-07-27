package org.geojsf.factory.ejb.sld;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.symbol.UtilsGraphic;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoSldFactory<L extends UtilsLang,
								D extends UtilsDescription,
								G extends UtilsGraphic<L,D,GT,GS>,
								GT extends UtilsStatus<GT,L,D>,
								GS extends UtilsStatus<GS,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								STYLE extends UtilsStatus<STYLE,L,D>,
								SLD extends GeoJsfSld<L,D,G,GT,GS,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,
								RULE extends GeoJsfSldRule<L,D,G,GT,GS,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,SLDTEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoSldFactory.class);
	
	final Class<SLD> cSld;
	
    public EjbGeoSldFactory(final Class<SLD> cSld)
    {
        this.cSld = cSld;
    } 
    
    public static <L extends UtilsLang,
				D extends UtilsDescription,
				G extends UtilsGraphic<L,D,GT,GS>,
				GT extends UtilsStatus<GT,L,D>,
				GS extends UtilsStatus<GS,L,D>,
				TYPE extends UtilsStatus<TYPE,L,D>,
				STYLE extends UtilsStatus<STYLE,L,D>,
				SLD extends GeoJsfSld<L,D,G,GT,GS,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,
				RULE extends GeoJsfSldRule<L,D,G,GT,GS,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>,
				SLDTEMPLATE extends GeoJsfSldTemplate<L,D,TYPE,STYLE,SLDTEMPLATE>>
    	EjbGeoSldFactory<L,D,G,GT,GS,TYPE,STYLE,SLD,RULE,SLDTEMPLATE> factory(final Class<SLD> cSld)
    {
        return new EjbGeoSldFactory<L,D,G,GT,GS,TYPE,STYLE,SLD,RULE,SLDTEMPLATE>(cSld);
    }
	
	public SLD build(SLDTEMPLATE template,TYPE type)
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