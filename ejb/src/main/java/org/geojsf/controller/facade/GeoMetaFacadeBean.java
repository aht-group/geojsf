package org.geojsf.controller.facade;

import javax.persistence.EntityManager;

import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.interfaces.facade.GeoMetaFacade;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.jeesl.controller.facade.jx.JeeslFacadeBean;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoMetaFacadeBean <L extends JeeslLang, D extends JeeslDescription,
								MAP extends GeoJsfMap<L,D,?,?,VP>,
								DS extends GeoJsfDataSource<L,D,?>,
								VP extends GeoJsfViewPort,
								SCALE extends GeoJsfScale<L,D> 
								>
				extends JeeslFacadeBean
				implements GeoMetaFacade<L,D,MAP,DS,VP,SCALE>
		
{	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeoMetaFacadeBean.class);
	
	private final GeoMetaFactoryBuilder<L,D,?,DS,VP,SCALE,?> fbMeta;
	
	public GeoMetaFacadeBean(EntityManager em, GeoMetaFactoryBuilder<L,D,?,DS,VP,SCALE,?> fbMeta)
	{
		super(em);
		this.fbMeta=fbMeta;
	}

	@Override public DS load(DS ds)
	{
		ds = em.find(fbMeta.getClassDs(), ds.getId());
		ds.getLayers().size();
		return ds;
	}
	
	
}