package org.geojsf.demo.mbean.util.app;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;


import org.geojsf.demo.controller.interfaces.facade.GeoUtilsFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @ViewScoped
public class TrackingBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(TrackingBean.class);
	private static final long serialVersionUID = 1L;
	
	@EJB private GeoUtilsFacade fUtils;
	
	private boolean tracked;
	//******* Methods *******************************
	
	@PostConstruct
    public void init()
    {
		tracked=false;
    }
	
	public boolean track(String code)
	{
		if(!tracked)
		{
			fUtils.viewTacker(code);
			tracked=true;
		}
		return false;
	}
}