package net.sf.geojsf.demo.mbean.user;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @SessionScoped
public class UserBean implements Serializable
{
	private static final long serialVersionUID = 1L;	
	final static Logger logger = LoggerFactory.getLogger(UserBean.class);
	
	private String localeCode;

	@PostConstruct
    public void init() throws FileNotFoundException
    {	
		localeCode = "en";
    }
	
	public void setLocale(String localeCode) throws UtilsContraintViolationException, UtilsLockingException
	{
		this.localeCode=localeCode;
		
		Locale locale = null;
		if(localeCode.equals("de")){locale = Locale.GERMAN;}
		else if(localeCode.equals("en")){locale = Locale.ENGLISH;}
		else if(localeCode.equals("fr")){locale = Locale.FRENCH;}
		
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}

	@Produces @Named
	public String getLocaleCode() {return localeCode;}
}