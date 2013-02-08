package org.geojsf.demo.mbean.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class GeoDemoBean implements Serializable {
	
	private static final long serialVersionUID = -4869182190759908416L;
	final static Logger logger = LoggerFactory.getLogger(GeoDemoBean.class);
	private String text;
	
	@PostConstruct
	public void init()
	{
		text = "Hello";
	//	System.out.println("Init!");
		logger.info("Init!");
	}
	
	public void listener(javax.faces.event.ValueChangeEvent evt)
	{
		logger.error("Received event: " +evt.toString());
	}

	public void ajaxListener(AjaxBehaviorEvent evt)
	{
		logger.error("AjaxListener received: " +evt.toString());
		UIInput text = (UIInput) evt.getComponent();
		logger.error("Value:" +(String)text.getValue());
	}
	
	
	public void actionL(ActionEvent event){
		logger.info("Action!");

    	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
