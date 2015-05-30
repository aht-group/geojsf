package org.geojsf.interfaces.facade;

import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface GeoJsfUtilsFacade <L extends UtilsLang,
									D extends UtilsDescription,
									CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
									SLD extends GeoJsfSld<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
									RULE extends GeoJsfSldRule<L,D,SLDTYPE,SLDSTYLE,SLD,RULE,SLDTEMPLATE>,
									SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
									SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
		extends GeoJsfFacade<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLD,RULE,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,UtilsFacade
{		
	
}