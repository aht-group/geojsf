package org.geojsf.util.db.init;

import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.ejb.meta.EjbGeoViewPortFactory;
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
import org.geojsf.model.xml.geojsf.ViewPort;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class DbViewPortInit <L extends UtilsLang,D extends UtilsDescription,
							G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
							F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
							CATEGORY extends GeoJsfCategory<L,D,LAYER>,
							SERVICE extends GeoJsfService<L,D,LAYER>,
							LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
							MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
							SCALE extends GeoJsfScale<L,D>, 
							VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
							VP extends GeoJsfViewPort,
							DS extends GeoJsfDataSource<L,D,LAYER>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
							RULE extends GeoJsfSldRule<L,D,G>>
{
	final static Logger logger = LoggerFactory.getLogger(DbViewPortInit.class);
	
    @SuppressWarnings("unused")
	private final Class<VP> cVp;
    
    private UtilsFacade fUtils;
    
    private EjbGeoViewPortFactory<VP> efVp;
    
    public DbViewPortInit(GeoMetaFactoryBuilder<L,D,DS,VP,?,?,?> fbMeta, final Class<VP> cVp, UtilsFacade fUtils)
	{       
        this.cVp = cVp;
        this.fUtils=fUtils;
		
		efVp = fbMeta.ejbViewPort();
		
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,LAYER>,
					SERVICE extends GeoJsfService<L,D,LAYER>,
					LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
					MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>, SCALE extends GeoJsfScale<L,D>, 
					VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
					VP extends GeoJsfViewPort,
					DS extends GeoJsfDataSource<L,D,LAYER>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
					SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
					RULE extends GeoJsfSldRule<L,D,G>>
		DbViewPortInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		factory(GeoMetaFactoryBuilder<L,D,DS,VP,?,?,?> fbMeta, final Class<VP> cVp,UtilsFacade fUtils)
	{
		return new DbViewPortInit<L,D,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(fbMeta,cVp,fUtils);
	}
	
	public void iuViewPort(MAP ejbMap, ViewPort viewPort) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(ejbMap.getViewPort()==null)
		{
			VP vp = efVp.build(viewPort);
			vp = fUtils.persist(vp);
			ejbMap.setViewPort(vp);
			fUtils.update(ejbMap);
		}
		else
		{
			VP vp = ejbMap.getViewPort();
			efVp.update(vp,viewPort);
			fUtils.update(vp);
		}
	}
	
	public void iuViewPort(LAYER ejbLayer, ViewPort viewPort) throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		if(ejbLayer.getViewPort()==null)
		{
			VP vp = efVp.build(viewPort);
			vp = fUtils.persist(vp);
			ejbLayer.setViewPort(vp);
			fUtils.update(ejbLayer);
		}
		else
		{
			VP vp = ejbLayer.getViewPort();
			efVp.update(vp,viewPort);
			fUtils.update(vp);
		}
	}
		
}