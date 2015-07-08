package org.geojsf.factory.ejb;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbGeoCategoryFactory<L extends UtilsLang,
									D extends UtilsDescription,
									CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGeoCategoryFactory.class);
	
	final Class<CATEGORY> cCategory;
	
    public EjbGeoCategoryFactory(final Class<CATEGORY> cCategory)
    {
        this.cCategory = cCategory;
    } 
    
    public static <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>, SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
	EjbGeoCategoryFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> factory(final Class<CATEGORY> cCategory)
    {
        return new EjbGeoCategoryFactory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE>(cCategory);
    }
	
	public CATEGORY build(String code)
	{
		CATEGORY ejb;
		try {ejb = cCategory.newInstance();}
		catch (InstantiationException e) {e.printStackTrace();throw new RuntimeException(e);}
		catch (IllegalAccessException e) {e.printStackTrace();throw new RuntimeException(e);}
		ejb.setCode(code);
        return ejb;
    }
}