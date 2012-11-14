package net.sf.geojsf.util.factory.xml.openlayers;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfLayer;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfService;
import net.sf.geojsf.model.interfaces.openlayers.GeoJsfView;
import net.sf.geojsf.xml.openlayers.Repository;
import net.sf.geojsf.xml.openlayers.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRepositoryFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlRepositoryFactory.class);
	
	public static final long serialVersionUID=1;
	
	public XmlRepositoryFactory()
	{
		
	}

	public <L extends UtilsLang,D extends UtilsDescription,LAYER extends GeoJsfLayer<L,D,LAYER,SERVICE>,VIEW extends GeoJsfView<L,D,LAYER,SERVICE>,SERVICE extends GeoJsfService<L,D,LAYER,SERVICE>> 
		Repository build (List<SERVICE> list)
	{
		Repository xml = new Repository();
		
		if(list.size()>0)
		{
			XmlServiceFactory f = new XmlServiceFactory();
			for(GeoJsfService<L,D,LAYER,SERVICE> service : list)
			{
				xml.getService().add(f.build(service));
			}
		}
		
		return xml;
	}
}
