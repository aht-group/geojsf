package org.geojsf.factory.xml.openlayers;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.xml.geojsf.XmlServiceFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.Repository;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRepositoryFactory <L extends JeeslLang, D extends JeeslDescription,
									CATEGORY extends GeoJsfCategory<L,D,LAYER>,
									SERVICE extends GeoJsfService<L,D,LAYER>,
									LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,?,VP,?,?>,
									VIEW extends GeoJsfView<LAYER,?,VIEW>,
									VP extends GeoJsfViewPort>
									
	implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlRepositoryFactory.class);
	
	public static final long serialVersionUID=1;
	
	private Repository q;
	
	private XmlServiceFactory<L,D,CATEGORY,SERVICE,LAYER,VIEW,VP> xfService;
	
	public XmlRepositoryFactory(Query q){this(q.getRepository());}
	public XmlRepositoryFactory(Repository q)
	{
		this.q=q;
		if(q.isSetService()){xfService = new XmlServiceFactory<>(q.getService().get(0));}
	}

	public Repository build (List<SERVICE> list)
	{
		Repository xml = new Repository();
		
		if(q.isSetService())
		{
			for(SERVICE service : list)
			{
				xml.getService().add(xfService.build(service));
			}
		}
		
		return xml;
	}
}