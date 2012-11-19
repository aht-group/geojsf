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
public class CodeBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(CodeBean.class);
	private static final long serialVersionUID = 1L;
	
	private String xml;
	
	public String getXml() {
		return xml;
	}

	@PostConstruct
    public void init() throws FileNotFoundException
    {
		
		xml = new String();
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<test>\n</test>";
    }
	
	
}