package org.geojsf.factory.xml.openlayers;

import java.io.Serializable;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.factory.xml.geojsf.meta.XmlViewPortFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMapFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlMapFactory.class);
	
	public static final long serialVersionUID=1;
	
	private Map q;
	
	public XmlMapFactory(Query query){this(query.getMap());}
	public XmlMapFactory(Map q)
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
		Map build (GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE> ejb)
	{
		Map xml = new Map();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
				
		if(ejb.getViews()!=null && ejb.getViews().size()>0)
		{
			
		}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(q.getDescriptions());
			xml.setDescriptions(f.create(ejb.getDescription()));
		}
		
		if(q.isSetViewPort() && ejb.getViewPort()!=null)
		{
			XmlViewPortFactory f = new XmlViewPortFactory(q.getViewPort());
			xml.setViewPort(f.build(ejb.getViewPort()));
		}
		
		return xml;
	}
}
