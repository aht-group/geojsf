package net.sf.geojsf.demo.mbean.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
public class GeoDemoBean implements Serializable {
	
	private static final long serialVersionUID = -4869182190759908416L;
	final static Logger logger = LoggerFactory.getLogger(GeoDemoBean.class);
	private String text;
	
	@PostConstruct
	public void init()
	{
		text = "Hello";
		System.out.println("Init!");
		logger.info("Init!");
	}
	
	public void listen(AjaxBehaviorEvent evt)
	{
		logger.info("Received event: " +evt.toString());
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
