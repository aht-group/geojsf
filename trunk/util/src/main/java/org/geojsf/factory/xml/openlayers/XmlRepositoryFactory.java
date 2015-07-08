package org.geojsf.factory.xml.openlayers;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.factory.xml.geojsf.XmlServiceFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRepositoryFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlRepositoryFactory.class);
	
	public static final long serialVersionUID=1;
	
	private Repository q;
	
	public XmlRepositoryFactory(Query q){this(q.getRepository());}
	public XmlRepositoryFactory(Repository q)
	{
		this.q=q;
	}

	public <L extends UtilsLang,
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
		Repository build (List<SERVICE> list)
	{
		Repository xml = new Repository();
		
		if(q.isSetService() && list.size()>0)
		{
			XmlServiceFactory f = new XmlServiceFactory(q.getService().get(0));
			for(GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTYPE,SLDSTYLE,SLDTEMPLATE> service : list)
			{
				xml.getService().add(f.build(service));
			}
		}
		
		return xml;
	}
}
