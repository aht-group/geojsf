package org.geojsf.interfaces.model;

import java.util.List;

import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface GeoJsfService<L extends UtilsLang,
								D extends UtilsDescription,
								CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDSTYLE,SLDTEMPLATE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDSTYLE,SLDTEMPLATE>>
			extends EjbSaveable,EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>,EjbRemoveable
{	
	String getWms();
	void setWms(String wms);
	
	String getWcs();
	void setWcs(String wcs);
	
	List<LAYER> getLayer();
	void setLayer(List<LAYER> layer);
	
}