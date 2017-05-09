package org.geojsf.factory.factory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.factory.ejb.sld.EjbGeoSldFactory;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoSldFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,FS>, GT extends UtilsStatus<GT,L,D>, FS extends UtilsStatus<FS,L,D>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,TYPE>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								SLD extends GeoJsfSld<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>,
								RULE extends GeoJsfSldRule<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>>
{
	final static Logger logger = LoggerFactory.getLogger(GeoSldFactoryFactory.class);
	
	final Class<SLD> cSld;
	
    public GeoSldFactoryFactory(final Class<SLD> cSld)
    {
        this.cSld = cSld;
    } 
    
    public static <L extends UtilsLang, D extends UtilsDescription,
				G extends JeeslGraphic<L,D,G,GT,FS>, GT extends UtilsStatus<GT,L,D>, FS extends UtilsStatus<FS,L,D>,
				SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,TYPE>,
				TYPE extends UtilsStatus<TYPE,L,D>,
				SLD extends GeoJsfSld<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>,
				RULE extends GeoJsfSldRule<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>
				>
    	GeoSldFactoryFactory<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE> factory(final Class<SLD> cSld)
    {
        return new GeoSldFactoryFactory<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>(cSld);
    }
	
	public EjbGeoSldFactory<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE> sld()
	{
		 return new EjbGeoSldFactory<L,D,G,GT,FS,SLDTEMPLATE,TYPE,SLD,RULE>(cSld);
    }
}