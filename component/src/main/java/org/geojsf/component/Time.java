package org.geojsf.component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Time")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Time extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Time.class);
	
	private String value;
	private String pattern;
	private Date date;
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		super.processEvent(event);
	}
	
	@Override
	public void encodeAll(FacesContext ctx) throws IOException
	{
		try {
			logger.info("Using time information: " +getDate().toGMTString());
		} catch (ParseException e) {
			logger.error("Could not parse date: " +e.getMessage());
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Date getDate() throws ParseException {
		DateFormat df = new SimpleDateFormat(pattern);
		date =  df.parse(value);  
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}