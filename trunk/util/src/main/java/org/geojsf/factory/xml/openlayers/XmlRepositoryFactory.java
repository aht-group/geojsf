package org.geojsf.factory.xml.openlayers;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.geojsf.factory.xml.geojsf.XmlServiceFactory;
import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.xml.geojsf.Query;
import org.geojsf.xml.geojsf.Repository;
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

	public <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
		Repository build (List<SERVICE> list)
	{
		Repository xml = new Repository();
		
		if(q.isSetService() && list.size()>0)
		{
			XmlServiceFactory f = new XmlServiceFactory(q.getService().get(0));
			for(GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE> service : list)
			{
				xml.getService().add(f.build(service));
			}
		}
		
		return xml;
	}
}
