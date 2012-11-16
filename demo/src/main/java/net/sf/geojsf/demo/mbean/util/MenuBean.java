package net.sf.geojsf.demo.mbean.util;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import net.sf.ahtutils.jsf.menu.MenuFactory;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
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
	
	private static final String rootMain = "root";
	private Map<String,Menu> mapMenu;
	private Map<String,List<MenuItem>> mapBreadcrumb;
	
	private MenuFactory mfMain;

	@PostConstruct
    public void init() throws FileNotFoundException
    {
		
		logger.error("@PostConstruct");

		mapMenu = new Hashtable<String,Menu>();
		mapBreadcrumb = new Hashtable<String,List<MenuItem>>();
		Menu xmlMenuMain = JaxbUtil.loadJAXB(this.getClass().getClassLoader(),"/menu.xml", Menu.class);
		
		if(logger.isTraceEnabled())
		{
			logger.info("main.root="+rootMain);
		}

		mfMain = new MenuFactory(xmlMenuMain,"en",rootMain);
		mfMain.setAlwaysUpToLevel(99);
		mfMain.setContextRoot("geojsf");
    }
	
	private Menu createMenu(String code,MenuFactory mf)
	{
		logger.info("createMenu for "+code);

		Menu menu = mf.build(code);
		return menu;	
	}
	
	public void clear()
	{
		mapMenu.clear();
		mapBreadcrumb.clear();
	}
	
	public Menu build(String code)
	{
		synchronized(mfMain)
		{
			if(!mapMenu.containsKey(code))
			{
				mapMenu.put(code, createMenu(code,mfMain));
			}
			return mapMenu.get(code);
		}
	}
	
	public List<MenuItem> breadcrumb(String code)
	{
		synchronized(mfMain)
		{
			if(!mapBreadcrumb.containsKey(code))
			{
				if(!mapMenu.containsKey(code)){build(code);}
				mapBreadcrumb.put(code,mfMain.breadcrumb(code));
			}
			return mapBreadcrumb.get(code);
		}
	}
}