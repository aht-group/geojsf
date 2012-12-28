package net.sf.geojsf.demo.mbean.tutorial;

import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import net.sf.geojsf.demo.controller.interfaces.facade.GeoUtilsFacade;
import net.sf.geojsf.demo.model.util.security.SecurityView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @ViewScoped
public class TutorialBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(TutorialBean.class);
	private static final long serialVersionUID = 1L;

	@EJB private GeoUtilsFacade fUtils;
	private boolean dummy;
	
	@PostConstruct
	public void init() throws FileNotFoundException
	{			
		dummy = false;
		fUtils.viewTacker(SecurityView.CodeTutorial.tutorial.toString());
	}
	
	public boolean isDummy() {return dummy;}
}