package org.geojsf.util.db.init;

import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.ejb.meta.EjbGeoViewPortFactory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.model.xml.geojsf.ViewPort;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbViewPortInit <LAYER extends GeoJsfLayer<?,?,?,?,?,VP,?,?>,
							MAP extends GeoJsfMap<?,?,?,?,VP>,
							VP extends GeoJsfViewPort>
{
	final static Logger logger = LoggerFactory.getLogger(DbViewPortInit.class);
	
    @SuppressWarnings("unused")
	private final Class<VP> cVp;
    
    private JeeslFacade fUtils;
    
    private EjbGeoViewPortFactory<VP> efVp;
    
    public DbViewPortInit(GeoMetaFactoryBuilder<?,?,LAYER,?,VP,?,?> fbMeta, final Class<VP> cVp, JeeslFacade fUtils)
	{       
        this.cVp = cVp;
        this.fUtils=fUtils;
		
		efVp = fbMeta.ejbViewPort();
		
	}
	
	public static <LAYER extends GeoJsfLayer<?,?,?,?,?,VP,?,?>,
					MAP extends GeoJsfMap<?,?,?,?,VP>,
					VP extends GeoJsfViewPort>
		DbViewPortInit<LAYER,MAP,VP>
		factory(GeoMetaFactoryBuilder<?,?,LAYER,?,VP,?,?> fbMeta, final Class<VP> cVp,JeeslFacade fUtils)
	{
		return new DbViewPortInit<LAYER,MAP,VP>(fbMeta,cVp,fUtils);
	}
	
	public void iuViewPort(MAP ejbMap, ViewPort viewPort) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(ejbMap.getViewPort()==null)
		{
			VP vp = efVp.build(viewPort);
			vp = fUtils.persist(vp);
			ejbMap.setViewPort(vp);
			fUtils.save(ejbMap);
		}
		else
		{
			VP vp = ejbMap.getViewPort();
			efVp.update(vp,viewPort);
			fUtils.save(vp);
		}
	}
	
	public void iuViewPort(LAYER ejbLayer, ViewPort viewPort) throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(ejbLayer.getViewPort()==null)
		{
			VP vp = efVp.build(viewPort);
			vp = fUtils.persist(vp);
			ejbLayer.setViewPort(vp);
			fUtils.save(ejbLayer);
		}
		else
		{
			VP vp = ejbLayer.getViewPort();
			efVp.update(vp,viewPort);
			fUtils.save(vp);
		}
	}	
}