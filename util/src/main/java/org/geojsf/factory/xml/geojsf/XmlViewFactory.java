package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

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
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewFactory.class);
	
	public static final long serialVersionUID=1;
	
	private View q;
	
	public XmlViewFactory(Query query){this(query.getView());}
	public XmlViewFactory(View q)
	{
		this.q=q;
	}

	public <L extends UtilsLang,
			D extends UtilsDescription,
			CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE>,
			SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE>,
			LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE>,
			MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE>,
			VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE>,
			VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE>,
			DS extends GeoJsfDataSource<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE>,
			SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
			SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>>  
		View build (GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE> ejb)
	{
		View xml = new View();
		
		if(q.isSetVisible()){xml.setVisible(ejb.isVisible());}
		if(q.isSetLegend()){xml.setLegend(ejb.getLegend());}
		if(q.isSetNr()){xml.setNr(ejb.getOrderNo());}
		
		if(q.isSetLayer())
		{
			XmlLayerFactory<D> f = new XmlLayerFactory<D>(q.getLayer());
			xml.setLayer(f.build(ejb.getLayer()));
		}
			
		return xml;
	}
	
	public static View build(int nr, boolean visible, boolean legend, Layer layer)
	{
		View xml = new View();
		xml.setNr(nr);
		xml.setLegend(legend);
		xml.setVisible(true);
		xml.setLayer(layer);
		return xml;
	}
}
