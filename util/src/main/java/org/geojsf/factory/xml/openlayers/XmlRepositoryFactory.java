package org.geojsf.factory.xml.openlayers;

import java.io.Serializable;
import java.util.List;

import org.geojsf.factory.xml.geojsf.XmlServiceFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.Repository;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
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

	public <L extends JeeslLang, D extends JeeslDescription,
			G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
			F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<FS,L,D>,
			CATEGORY extends GeoJsfCategory<L,D,LAYER>,
			SERVICE extends GeoJsfService<L,D,LAYER>,
			LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
			MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
			SCALE extends GeoJsfScale<L,D>, 
			VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
			VP extends GeoJsfViewPort,
			DS extends GeoJsfDataSource<L,D,LAYER>,
			SLDTYPE extends JeeslStatus<SLDTYPE,L,D>,
			SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
			SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
			RULE extends GeoJsfSldRule<L,D,G>>
		Repository build (List<SERVICE> list)
	{
		Repository xml = new Repository();
		
		if(q.isSetService() && list.size()>0)
		{
			XmlServiceFactory f = new XmlServiceFactory(q.getService().get(0));
			for(GeoJsfService<L,D,LAYER> service : list)
			{
				xml.getService().add(f.build(service));
			}
		}
		
		return xml;
	}
}
