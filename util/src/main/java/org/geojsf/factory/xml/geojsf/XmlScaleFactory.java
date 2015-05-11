package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Scale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlScaleFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlScaleFactory.class);
	
	public static final long serialVersionUID=1;
	
	public static <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
		Scale build(VP ejb)
	{
		Scale scale = new Scale();
		scale.setValue((new Double(ejb.getScale()).intValue()));
		return scale;
	}
	
	public static Scale build(double scale)
	{
		Scale xml = new Scale();
		xml.setValue((new Double(scale).intValue()));
		return xml;
	}
}
