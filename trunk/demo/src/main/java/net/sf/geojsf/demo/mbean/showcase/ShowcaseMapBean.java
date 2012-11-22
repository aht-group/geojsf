package net.sf.geojsf.demo.mbean.showcase;

import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.openfuxml.addon.jsf.factory.JsfComponentFactory;
import org.openfuxml.xml.addon.jsf.Attribute;
import org.openfuxml.xml.addon.jsf.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @SessionScoped
public class ShowcaseMapBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(ShowcaseMapBean.class);
	private static final long serialVersionUID = 1L;
	private Component component;
	
	public Component getComponent() {return component;}

	@PostConstruct
    public void init() throws FileNotFoundException
    {		
		MultiResourceLoader mrl = new MultiResourceLoader();
		logger.info("Available: "+mrl.isAvailable("META-INF/resources/geojsf/map.xhtml"));
		logger.info("Available: "+mrl.isAvailable("/META-INF/resources/geojsf/map.xhtml"));
		
		JsfComponentFactory jcf = new JsfComponentFactory();
		component = jcf.buildComponent("META-INF/resources/geojsf/map.xhtml");
    }
}