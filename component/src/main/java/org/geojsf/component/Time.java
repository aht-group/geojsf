package org.geojsf.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.util.GeoJsfJsLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Time")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Time extends UIComponentBase
{
	final static Logger logger = LoggerFactory.getLogger(Time.class);
	
	private static enum Attribute {value, rangeFrom, rangeTo, timeList}
	
	private Date value;
	private Date rangeFrom;
	private Date rangeTo;
	private ArrayList<Date> timeList;
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"TimeAgent.js");
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"TimeManager.js");
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"WMS.js");
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"DimensionManager.js");
			Map<String,Object> map = this.getAttributes();
			this.value     = (Date) map.get(Attribute.value.toString());
			this.rangeFrom = (Date) map.get(Attribute.rangeFrom.toString());
			this.rangeTo   = (Date) map.get(Attribute.rangeTo.toString());
			this.timeList  = (ArrayList<Date>) map.get(Attribute.timeList.toString());
		}
		super.processEvent(event);
	}
	
	@Override
	public void encodeAll(FacesContext ctx) throws IOException
	{
		logger.info("Time component info already rendered in Map. This client Id is: " +this.getClientId());
	}

	public Date getValue() {
		return value;
	}

	public Date getRangeFrom() {
		return rangeFrom;
	}

	public Date getRangeTo() {
		return rangeTo;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	public void setRangeFrom(Date rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public void setRangeTo(Date rangeTo) {
		this.rangeTo = rangeTo;
	}

	public ArrayList<Date> getTimeList() {
		return timeList;
	}

	public void setTimeList(ArrayList<Date> timeList) {
		this.timeList = timeList;
	}

	@Override
	public String getFamily() {
		// TODO @hh Is null really correct?
		return null;
	}
}