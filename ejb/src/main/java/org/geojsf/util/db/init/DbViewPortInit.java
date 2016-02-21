package org.geojsf.util.db.init;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.geojsf.factory.ejb.EjbGeoViewPortFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.ViewPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbViewPortInit <L extends UtilsLang,D extends UtilsDescription,
							G extends UtilsGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>,
							CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
{
	final static Logger logger = LoggerFactory.getLogger(DbViewPortInit.class);
	
    @SuppressWarnings("unused")
	private final Class<VP> cVp;
    
    private UtilsFacade fUtils;
    
    private EjbGeoViewPortFactory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE> efVp;
    
    public DbViewPortInit(final Class<VP> cVp, UtilsFacade fUtils)
	{       
        this.cVp = cVp;
        this.fUtils=fUtils;
		
		efVp = EjbGeoViewPortFactory.factory(cVp);
		
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					G extends UtilsGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>,
					CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
					SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
					SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
					RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>>
		DbViewPortInit<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		factory(final Class<VP> cVp,UtilsFacade fUtils)
	{
		return new DbViewPortInit<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>(cVp,fUtils);
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