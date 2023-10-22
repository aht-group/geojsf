package org.geojsf.web.controller.settings.meta;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.geojsf.factory.builder.GeoMetaFactoryBuilder;
import org.geojsf.factory.ejb.meta.EjbGeoScaleFactory;
import org.geojsf.interfaces.facade.GeoMetaFacade;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.util.comparator.ejb.GeoScaleComparator;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.controller.web.AbstractJeeslLocaleWebController;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class GeojsfSettingsScaleController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
									SCALE extends GeoJsfScale<L,D>

									>
		extends AbstractJeeslLocaleWebController<L,D,LOC>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeojsfSettingsScaleController.class);
	
	private GeoMetaFacade<L,D,?,?,?,SCALE> fGeo;
	
	private final GeoMetaFactoryBuilder<L,D,?,?,?,SCALE,?> fbMeta;
	
	protected final EjbGeoScaleFactory<SCALE> efScale;
	
	protected List<SCALE> scales; public List<SCALE> getScales(){return scales;}
	protected SCALE scale; public SCALE getScale() {return scale;} public void setScale(SCALE scale) {this.scale = scale;}
	
	private final Comparator<SCALE> cmpScale;
	
	public GeojsfSettingsScaleController(GeoMetaFactoryBuilder<L,D,?,?,?,SCALE,?> fbMeta)
	{
		super(fbMeta.getClassL(),fbMeta.getClassD());
		this.fbMeta=fbMeta;
		cmpScale = fbMeta.cmpScale(GeoScaleComparator.Type.value);
	    efScale = fbMeta.ejbScale();
	}
	
	public void postInit(GeoMetaFacade<L,D,?,?,?,SCALE> fGeo, JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessage)
	{
		super.postConstructLocaleWebController(lp,bMessage);
		this.fGeo=fGeo;
		reloadScales();
	}
	
	protected void reloadScales()
	{
		scales = fGeo.all(fbMeta.getClassScale());
		Collections.sort(scales,cmpScale);
	}
	
	public void addScale()
	{
		logger.info(AbstractLogMessage.createEntity(fbMeta.getClassScale()));
		scale = efScale.build();
		scale.setName(efLang.buildEmpty(lp.getLocales()));
		scale.setDescription(efDescription.buildEmpty(lp.getLocales()));
	}
	
	public void selectTemplate() throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(scale));
		scale = efLang.persistMissingLangs(fGeo,lp.getLocales(),scale);
		scale = efDescription.persistMissingLangs(fGeo,lp.getLocales(),scale);
	}

	
	public void cancelScale(){reset(true);}
	private void reset(boolean rScale)
	{
		if(rScale){scale=null;}
	}
	
	public void selectScale() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.selectEntity(scale));

	}
	
	public void saveScale() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(scale));
		scale = fGeo.save(scale);
		reloadScales();
	}
}