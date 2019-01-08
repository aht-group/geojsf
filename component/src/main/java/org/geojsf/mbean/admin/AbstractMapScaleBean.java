package org.geojsf.mbean.admin;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.geojsf.factory.builder.GeoCoreFactoryBuilder;
import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.builder.GeoSldFactoryBuilder;
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
import org.geojsf.util.comparator.ejb.GeoScaleComparator;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractMapScaleBean <L extends UtilsLang, D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
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
									RULE extends GeoJsfSldRule<L,D,G>
									>
		extends AbstractGeoJsfBean<L,D,LOC,G,GT,F,FS,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMapScaleBean.class);
	
	protected List<SCALE> scales; public List<SCALE> getScales(){return scales;}
	protected SCALE scale; public SCALE getScale() {return scale;} public void setScale(SCALE scale) {this.scale = scale;}
	
	private final Comparator<SCALE> cmpScale;
	
	public AbstractMapScaleBean(GeoCoreFactoryBuilder<L,D,CATEGORY,SERVICE,LAYER,MAP,SCALE,VIEW,VP> fbCore,
								GeoMetaFactoryBuilder<L,D,DS,VP> fbMeta,
								GeoSldFactoryBuilder<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> fbSld)
	{
		super(fbCore,fbMeta,fbSld);
		cmpScale = fbCore.cmpScale(GeoScaleComparator.Type.value);
	}
	
	protected void postInit(){reloadScales();}
	
	protected void reloadScales()
	{
		scales = fGeo.all(fbCore.getClassScale());
		Collections.sort(scales,cmpScale);
	}
	
	public void selectTemplate() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(scale));
		missingLangsMap();
	}
	protected void missingLangsMap(){}
	
	public void addScale()
	{
		logger.info(AbstractLogMessage.addEntity(fbSld.getClassTemplate()));
		scale = efScale.build(langKeys);
	}
	
	public void cancelScale(){reset(true);}
	private void reset(boolean rScale)
	{
		if(rScale){scale=null;}
	}
	
	public void saveScale() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(scale));
		scale = fGeo.save(scale);
		reloadScales();
	}
}