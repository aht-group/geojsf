package org.geojsf.factory.factory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.factory.ejb.sld.EjbGeoSldFactory;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoSldFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLD extends GeoJsfSld<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
								RULE extends GeoJsfSldRule<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
{
	final static Logger logger = LoggerFactory.getLogger(GeoSldFactoryFactory.class);
	
	final Class<SLD> cSld;
	
    public GeoSldFactoryFactory(final Class<SLD> cSld)
    {
        this.cSld = cSld;
    } 
    
    public static <L extends UtilsLang, D extends UtilsDescription,
			    G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
				F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
				SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
				SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
				SLD extends GeoJsfSld<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
				RULE extends GeoJsfSldRule<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
				>
    	GeoSldFactoryFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> factory(final Class<SLD> cSld)
    {
        return new GeoSldFactoryFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(cSld);
    }
	
	public EjbGeoSldFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE> sld()
	{
		 return new EjbGeoSldFactory<L,D,G,GT,F,FS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(cSld);
    }
}