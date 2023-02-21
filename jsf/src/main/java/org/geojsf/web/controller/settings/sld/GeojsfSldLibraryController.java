package org.geojsf.web.controller.settings.sld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.factory.ejb.sld.EjbGeoSldFactory;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldXml;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.interfaces.util.qualifier.GeoJsfProvideSldStatus;
import org.geojsf.util.GeoJsfSldConfigurationProvider;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.controller.web.AbstractJeeslWebController;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.position.EjbWithPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.xml.JaxbUtil;

public class GeojsfSldLibraryController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
										SLD extends GeoJsfSld<L,D,SDX,SDT,SDR,LE,LA>,
										SDT extends GeoJsfSldType<L,D,SDT,?>,
										SDX extends GeoJsfSldXml<L,D,SLD>,
										SDR extends GeoJsfSldRule<L,D,?>,
										LE extends JeeslRevisionEntity<L,D,?,?,LA,?>,
										LA extends JeeslRevisionAttribute<L,D,LE,?,?>>
		extends AbstractJeeslWebController<L,D,LOC>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeojsfSldLibraryController.class);
	
	private GeoSldFacade<L,D,SDX,SLD,SDT,SDR> fSld;
	private JeeslIoRevisionFacade<L,D,?,?,?,?,?,LE,?,LA,?,?,?,?> fRevision;
	
	private final GeoSldFactoryBuilder<L,D,?,SDX,SLD,SDT,SDR,LE,LA> fbSld;
	private final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,LE,?,LA,?,?,?,?> fbLabel;
//	private final JeeslIoRevisionFacade<L,D,?,?,?,?,?,LE,?,LA,?,?,?,?> fRevision;

	private GeoJsfSldConfigurationProvider scp; public void setScp(GeoJsfSldConfigurationProvider scp) {this.scp = scp;}
	protected final EjbGeoSldFactory<SDT,SLD> efSld;
	
	private List<SDT> types; public List<SDT> getTypes() {return types;}
	private List<SDX> templates; public List<SDX> getTemplates(){return templates;}
	private List<SLD> slds; public List<SLD> getSlds() {return slds;} public void setSlds(List<SLD> slds) {this.slds = slds;}
	private final List<LE> entities; public List<LE> getEntities() {return entities;}
	private final List<LA> attributes; public List<LA> getAttributes() {return attributes;}
	private final List<EjbWithId> options; public List<EjbWithId> getOptions() {return options;}
	
	private List<String> sldStatusClasses; public List<String> getSldStatusClasses() {return sldStatusClasses;}
	
	private SLD sld; public SLD getSld() {return sld;} public void setSld(SLD sld) {this.sld = sld;}
	private String xml; public String getXml() {return xml;}
	
	public GeojsfSldLibraryController(GeoSldFactoryBuilder<L,D,?,SDX,SLD,SDT,SDR,LE,LA> fbSld,
			IoRevisionFactoryBuilder<L,D,?,?,?,?,?,LE,?,LA,?,?,?,?> fbLabel)
	{
		super(fbSld.getClassL(),fbSld.getClassD());
		this.fbSld=fbSld;
		this.fbLabel=fbLabel;

		efSld = fbSld.ejbSld();
		
		sldStatusClasses = new ArrayList<>();
		entities = new ArrayList<>();
		attributes = new ArrayList<>();
		options = new ArrayList<>();
	}
	
	public void postConstruct(JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessage,
								GeoSldFacade<L,D,SDX,SLD,SDT,SDR> fSld,
								JeeslIoRevisionFacade<L,D,?,?,?,?,?,LE,?,LA,?,?,?,?> fRevision)
	{
		super.postConstructWebController(lp,bMessage);
		this.fSld=fSld;
		this.fRevision=fRevision;
		
		templates = fSld.all(fbSld.getClassTemplate());
		types = fSld.allOrderedPositionVisible(fbSld.getClassSldType());
		entities.addAll(fSld.allOrderedPositionVisible(fbLabel.getClassEntity()));
		this.reloadSlds();
	}
	
	public void cancelSld(){reset(false,true);}
	private void reset(boolean rSlds, boolean rSld)
	{
		if(rSlds) {slds.clear();}
		if(rSld){sld=null;}
	}

	protected void reloadSlds()
	{
		slds = fSld.fLibrarySlds();
	}

	public void selectSld()
	{
		logger.info(AbstractLogMessage.selectEntity(sld));
		efLang.persistMissingLangs(fSld,lp.getLocales(),sld);
		efDescription.persistMissingLangs(fSld,lp.getLocales(),sld);
		sld = fSld.load(sld);
		this.reloadAttributes();
		this.reloadAttribute();
		this.generateXml();
	}
	
	public void addSld()
	{
		logger.info(AbstractLogMessage.createEntity(fbSld.getClassSld()));
		sld = efSld.build(null,true);
		sld.setName(efLang.buildEmpty(lp.getLocales()));
		sld.setDescription(efDescription.buildEmpty(lp.getLocales()));
	}
	
	public void saveSld() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("1: "+sld.getStatusAttribute());
		sld.setType(fSld.find(fbSld.getClassSldType(),sld.getType()));
//		if(!sld.getType().getCode().equals(GeoJsfTYPE.Type.template.toString())){sld.setTemplate(null);}
//		if(!sld.getType().getCode().equals(GeoJsfTYPE.Type.status.toString())){sld.setStatusClass(null);}
//		
		if(sld.getTemplate()!=null){sld.setTemplate(fSld.find(fbSld.getClassTemplate(),sld.getTemplate()));}
		
		logger.info(AbstractLogMessage.saveEntity(sld));
		sld = fSld.save(sld);
		logger.info("2: "+sld.getStatusAttribute());
		this.reloadSlds(); 
		this.reloadAttributes();
		this.reloadAttribute();
		this.generateXml();
	}
	
	public void deleteSld() throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(sld));
		fSld.rm(sld);
		reset(true,true);
		this.reloadSlds();
	}
	
	public void changeType()
	{
		sld.setType(fSld.find(fbSld.getClassSldType(), sld.getType()));
		logger.info(AbstractLogMessage.selectOneMenuChange(sld.getType()));
	}
	
	public <C extends GeoJsfProvideSldStatus> void activateSldType(Class<C> c)
	{
		sldStatusClasses.add(c.getName());
	}
	
	public void reloadAttributes()
	{
		attributes.clear();
		if(Objects.nonNull(sld.getEntity()))
		{
			sld.setEntity(fRevision.find(fbLabel.getClassEntity(), sld.getEntity()));
			sld.setEntity(fRevision.load(fbLabel.getClassEntity(), sld.getEntity()));
			attributes.addAll(sld.getEntity().getAttributes());
			attributes.removeIf(x -> (Objects.isNull(x.getRelation())));
		}
		logger.info(AbstractLogMessage.reloaded(fbLabel.getClassAttribute(),attributes));
	}
	
	@SuppressWarnings("unchecked")
	public void reloadAttribute()
	{
		options.clear();
		if(Objects.nonNull(sld.getAttribute()))
		{
			sld.setAttribute(fRevision.find(fbLabel.getClassAttribute(), sld.getAttribute()));
			
			try
			{
				Class<EjbWithPosition> cOption = (Class<EjbWithPosition>)Class.forName(sld.getAttribute().getEntity().getCode()).asSubclass(EjbWithPosition.class);
				options.addAll(fRevision.allOrderedPosition(cOption));
			}
			catch (ClassNotFoundException e) {e.printStackTrace();}
//			
		}
	}
	
	private void generateXml()
	{
		xml = null;
		if(Objects.isNull(scp)){logger.warn("No "+GeoJsfSldConfigurationProvider.class.getSimpleName()+" configured!"); return;}
		if(sld.getType().getCode().equals(GeoJsfSldType.Type.status.toString()))
		{
			xml = JaxbUtil.toString(fbSld.xmlStyledLayerDescriptor(scp).build(sld));
		}
	}
}