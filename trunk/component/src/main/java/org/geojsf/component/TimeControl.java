package org.geojsf.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.util.GeoJsfJsLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@FacesComponent(value="org.geojsf.component.TimeControl")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class TimeControl extends UIComponentBase
{
	final static Logger logger = LoggerFactory.getLogger(TimeControl.class);
	
	private static enum Attribute {targetId, min, max, delta, slider, start, stop, framerate}
	
	private Map targetMap;
	private Time targetTime;
	private Date now;
	
	private Date min;
	private Date max;
	private Long step;
	private Double framerate;
	
	private Boolean slider;
	private Boolean start;
	private Boolean stop;
	
	Gson gson = new Gson();
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
		//	GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"TimeAgent.js");
		//	GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"TimeManager.js");
		//	GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"WMS.js");
		//	GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"DimensionManager.js");
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"player.js");
			java.util.Map<String,Object> map = this.getAttributes();
			
			//Connect this component to the requested Time component
			String target  = (String) map.get(Attribute.targetId.toString());
			logger.info("Searching for " +target);
			this.targetTime= (Time) this.getFacesContext().getViewRoot().findComponent(target);
			logger.info("Time: " +targetTime.getId());
			logger.info("Map:  " +targetTime.getParent().getClass().getName());
			this.targetMap = (Map) targetTime.getParent();
			this.now       = targetTime.getValue();
			
			this.min       = (Date) map.get(Attribute.min.toString());
			this.max       = (Date) map.get(Attribute.max.toString());
			this.step      = (Long) map.get(Attribute.delta.toString());
			this.framerate = (Double) map.get(Attribute.framerate.toString());
			
			this.slider    = (Boolean) map.get(Attribute.slider.toString());
			this.start     = (Boolean) map.get(Attribute.start.toString());
			this.stop      = (Boolean) map.get(Attribute.stop.toString());
		}
		super.processEvent(event);
	}
	
	@Override
	public void encodeAll(FacesContext ctx) throws IOException
	{
		ResponseWriter writer = ctx.getResponseWriter();
		writer.writeText(System.getProperty("line.separator"), null);
		writer.writeText("GeoJSFPlayer.setTemporalLayer(" +gson.toJson(targetMap.getTemporalLayerNames()) +");", null);
		writer.writeText(System.getProperty("line.separator"), null);
		writer.writeText("GeoJSFPlayer.setTime(" +now.getTime() +");", null);
	}

	

	@Override
	public String getFamily() {
		return null;
	}

	public Map getTargetMap() {
		return targetMap;
	}

	public Time getTargetTime() {
		return targetTime;
	}

	public Date getMin() {
		return min;
	}

	public Date getMax() {
		return max;
	}

	public Long getStep() {
		return step;
	}

	public Double getFramerate() {
		return framerate;
	}

	public Boolean getSlider() {
		return slider;
	}

	public Boolean getStart() {
		return start;
	}

	public Boolean getStop() {
		return stop;
	}

	public void setTargetMap(Map targetMap) {
		this.targetMap = targetMap;
	}

	public void setTargetTime(Time targetTime) {
		this.targetTime = targetTime;
	}

	public void setMin(Date min) {
		this.min = min;
	}

	public void setMax(Date max) {
		this.max = max;
	}

	public void setStep(Long step) {
		this.step = step;
	}

	public void setFramerate(Double framerate) {
		this.framerate = framerate;
	}

	public void setSlider(Boolean slider) {
		this.slider = slider;
	}

	public void setStart(Boolean start) {
		this.start = start;
	}

	public void setStop(Boolean stop) {
		this.stop = stop;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}
}