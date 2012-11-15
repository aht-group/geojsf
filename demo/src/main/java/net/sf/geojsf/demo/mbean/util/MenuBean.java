package net.sf.geojsf.demo.mbean.util;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import net.sf.ahtutils.jsf.menu.MenuFactory;
import net.sf.ahtutils.web.mbean.util.AbstractIconBean;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @SessionScoped
public class MenuBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(MenuBean.class);
	private static final long serialVersionUID = 1L;
	
	protected Menu menu;
	
//	@Inject private Identity identity;
//	@Inject private String localeCode;
	
	private static final String rootMain = "rootMain";
	private Map<String,Menu> mapMenu;
	
	private MenuFactory mfMain;
	
	@PostConstruct
    public void init() throws FileNotFoundException
    {
		logger.info("@PostConstruct");

		mapMenu = new Hashtable<String,Menu>();
		Access xmlAccess = JaxbUtil.loadJAXB(this.getClass().getClassLoader(),"/views.xml", Access.class);
		Menu xmlMenuMain = JaxbUtil.loadJAXB(this.getClass().getClassLoader(),"/menu.xml", Menu.class);
		
		if(logger.isInfoEnabled())
		{
			logger.info("main.root="+rootMain);
		}

		mfMain = new MenuFactory(xmlMenuMain,xmlAccess,"en",rootMain);
		mfMain.setAlwaysUpToLevel(99);
    }
	
	private Menu createMenu(String code,MenuFactory mf)
	{
		logger.info("createMenu for "+code);

		Menu menu = mf.build(code);
		JaxbUtil.info(menu);
		return menu;	
	}
	
	public void clear()
	{
		mapMenu.clear();
	}
	
	public Menu build(String code)
	{
		if(!mapMenu.containsKey(code))
		{
			mapMenu.put(code, createMenu(code,mfMain));
		}
		return mapMenu.get(code);
	}
}