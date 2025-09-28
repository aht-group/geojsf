package org.geojsf.web.controller.settings.sld;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.interfaces.controller.web.GeoSldTimeSeriesCallback;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.interfaces.util.with.EjbWithSld;
import org.geojsf.jsf.handler.SldRuleHandler;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.controller.web.AbstractJeeslLocaleWebController;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.factory.builder.system.SvgFactoryBuilder;
import org.jeesl.factory.txt.system.status.TxtStatusFactory;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.module.ts.config.JeeslTsCategory;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicShape;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoSldTimeSeriesWebController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											G extends JeeslGraphic<GT,?,GS>,
											GT extends JeeslGraphicType<L,D,GT,G>,
											GS extends JeeslGraphicShape<L,D,GS,G>,
											SLD extends GeoJsfSld<L,D,?,TYPE,RULE,?,?>,
											TYPE extends GeoJsfSldType<L,D,TYPE,G>,
											RULE extends GeoJsfSldRule<L,D,G>,
											CAT extends JeeslTsCategory<L,D,CAT,?>,
											SCOPE extends JeeslTsScope<L,D,CAT,?,?,?,?>,
											MP extends JeeslTsMultiPoint<L,D,SCOPE,?,?>>
		extends AbstractJeeslLocaleWebController<L,D,LOC>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeoSldTimeSeriesWebController.class);
	
	private GeoSldFacade<L,D,?,SLD,TYPE,RULE> fSld;
	
	private SvgFactoryBuilder<L,D,G,GT,?,GS> fbSvg;
	private final GeoSldFactoryBuilder<L,D,?,?,SLD,TYPE,RULE,?,?> fbSld;
	private final TsFactoryBuilder<L,D,LOC,CAT,SCOPE,?,?,MP,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?> fbTs;
	
	private final GeoSldTimeSeriesCallback<SLD> callback;
	private SldRuleHandler<L,D,G,GT,GS,SLD,RULE> ruleHandler; public SldRuleHandler<L,D,G,GT,GS,SLD,RULE> getRuleHandler() {return ruleHandler;}
	
	private final TreeNode tree; public TreeNode getTree() {return tree;}
	private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}
	
	private SCOPE scope; public SCOPE getScope() {return scope;}
	private MP mp; public MP getMp() {return mp;}
	private SLD sld; public SLD getSld() {return sld;} public void setSld(SLD sld) {this.sld = sld;}

	public GeoSldTimeSeriesWebController(GeoSldTimeSeriesCallback<SLD> callback,
										SvgFactoryBuilder<L,D,G,GT,?,GS> fbSvg,
										GeoSldFactoryBuilder<L,D,?,?,SLD,TYPE,RULE,?,?> fbSld,
										TsFactoryBuilder<L,D,LOC,CAT,SCOPE,?,?,MP,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?> fbTs)
	{
		super(fbSld.getClassL(),fbSld.getClassD());
		this.callback=callback;
		this.fbSvg=fbSvg;
		this.fbSld=fbSld;
		this.fbTs=fbTs;
		
		tree = new DefaultTreeNode("Root", null);
	}
	
	public void postConstruct(JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessage,
								GeoSldFacade<L,D,?,SLD,TYPE,RULE> fSld)
	{
		super.postConstructLocaleWebController(lp,bMessage);
		this.fSld=fSld;
		this.reloadTree();
		
		String[] langs = TxtStatusFactory.toCodes(lp.getLocales()).toArray(new String[0]);
		ruleHandler = new SldRuleHandler<>(fbSld,fbSvg,fSld,langs);
	}
	
	protected void reloadTree()
	{
		Map<CAT,List<SCOPE>> mapScope = fbTs.ejbScope().toMapCategory(fSld.allOrderedPosition(fbTs.getClassScope()));
		Map<SCOPE,List<MP>> mapMp = fbTs.ejbMultiPoint().toMapScope(fSld.allOrderedPosition(fbTs.getClassMp()));
		
		for(CAT category : fSld.allOrderedPositionVisible(fbTs.getClassCategory()))
		{
			if(mapScope.containsKey(category))
			{
				TreeNode nCategory = new DefaultTreeNode(category,tree);
				for(SCOPE scope : mapScope.get(category))
				{
					TreeNode nScope = new DefaultTreeNode(scope,nCategory);
					if(mapMp.containsKey(scope))
					{
						List<MP> mps = mapMp.get(scope);
						for(MP mp : mps)
						{
							new DefaultTreeNode(mp,nScope);
						}
					}
				}
			}
		}
		
	}
	
	public void cancelSelectgion() {this.reset();}
	private void reset()
	{
		scope=null;
		mp=null;
		sld=null;
		ruleHandler.setRule(null);
	}

	public void onNodeSelect(NodeSelectEvent event) throws JeeslConstraintViolationException, JeeslLockingException {this.nodeSelected(event.getTreeNode());}
	@SuppressWarnings("unchecked")
	public void nodeSelected(TreeNode node) throws JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info("Selected "+node.toString());
		this.reset();
		EjbWithSld<SLD> wSld=null;
		if(node.getData() instanceof JeeslTsScope)
		{
			scope = (SCOPE)node.getData();
			if(scope instanceof EjbWithSld) {wSld = (EjbWithSld<SLD>)scope;}
		}
		else if(node.getData() instanceof JeeslTsMultiPoint)
		{
			mp = (MP)node.getData();
			if(mp instanceof EjbWithSld) {wSld = (EjbWithSld<SLD>)mp;}
		}
		if(wSld!=null) {initSld(wSld);}
	}
	
	private void initSld(EjbWithSld<SLD> wSld) throws JeeslConstraintViolationException, JeeslLockingException
	{
		sld = callback.fcSld(wSld);
		logger.info("initSld for "+wSld.toString()+" with "+sld.toString());
		ruleHandler.setSld(sld);
		ruleHandler.reloadSld();
		ruleHandler.setRule(null);
	}
}