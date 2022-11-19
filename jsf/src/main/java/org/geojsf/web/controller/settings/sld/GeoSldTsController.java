package org.geojsf.web.controller.settings.sld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.geojsf.factory.builder.GeoSldFactoryBuilder;
import org.geojsf.interfaces.facade.GeoSldFacade;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.factory.builder.module.TsFactoryBuilder;
import org.jeesl.interfaces.controller.handler.system.locales.JeeslLocaleProvider;
import org.jeesl.interfaces.model.module.ts.config.JeeslTsCategory;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsMultiPoint;
import org.jeesl.interfaces.model.module.ts.core.JeeslTsScope;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.web.controller.AbstractJeeslWebController;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoSldTsController <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											
											SLD extends GeoJsfSld<L,D,?,?,RULE>,
											RULE extends GeoJsfSldRule<L,D,?>,
											
											CAT extends JeeslTsCategory<L,D,CAT,?>,
											SCOPE extends JeeslTsScope<L,D,CAT,?,?,?,?>,
											MP extends JeeslTsMultiPoint<L,D,SCOPE,?>>
		extends AbstractJeeslWebController<L,D,LOC>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(GeoSldTsController.class);
	
	private GeoSldFacade<L,D,?,?,SLD,RULE> fSld;
	
	private final GeoSldFactoryBuilder<L,D,?,?,?,?,SLD,RULE> fbSld;
	
	private final TreeNode tree; public TreeNode getTree() {return tree;}
	private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}
	
	public GeoSldTsController(GeoSldFactoryBuilder<L,D,?,?,?,?,SLD,RULE> fbSld,
			TsFactoryBuilder<L,D,LOC,CAT,SCOPE,?,?,MP,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?> fbTs)
	{
		super(fbSld.getClassL(),fbSld.getClassD());
		this.fbSld=fbSld;
		tree = new DefaultTreeNode("Root", null);
	}
	
	public void postConstruct(
								JeeslLocaleProvider<LOC> lp, JeeslFacesMessageBean bMessage,
								GeoSldFacade<L,D,?,?,SLD,RULE> fSld)
	{
		super.postConstructWebController(lp,bMessage);
		this.fSld=fSld;
	}
	
	protected void reloadTree()
	{
		List<SCOPE> scopes = new ArrayList<>();
		
		
	}

	@SuppressWarnings("unchecked")
	public void onNodeSelect(NodeSelectEvent event)
	{
//		this.reset(true);
		logger.info("Selected "+event.getTreeNode().toString());
//		classification = (C)event.getTreeNode().getData();
//		classification = facade.find(cClassification,classification);
//		classification = efLang.persistMissingLangs(facade,lp.getLocales(),classification);
	}
}