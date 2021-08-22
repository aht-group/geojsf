package org.geojsf.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.util.GeoJsfJsLoader;
import org.joda.time.DateTime;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.slider.Slider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@FacesComponent(value="org.geojsf.component.TimeControl")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class TimeControl extends UIComponentBase
{
	final static Logger logger = LoggerFactory.getLogger(TimeControl.class);
	
	private static enum Attribute {targetId, min, max, delta, slider, start, stop, framerate}
	private Boolean initStage = true;

	private Map targetMap;
	private Time targetTime;
	private Date now;
	
	private Date min;
	private Date max;
	private Long step;
	private Double framerate;
	
	private Boolean slider = false;
	private Boolean start  = true;
	private Boolean stop   = true;
	
	Gson gson = new Gson();
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"player.js");
			java.util.Map<String,Object> map = this.getAttributes();
			
			//Connect this component to the requested Time and parent Map component
			String target  = (String) map.get(Attribute.targetId.toString());
			this.targetTime= (Time) this.getFacesContext().getViewRoot().findComponent(target);
			this.targetMap = (Map) targetTime.getParent();
			
			//Assigning time configured in Map to current time
			this.now       = targetTime.getValue();
			
			//Now set all attributes from request
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
		if (initStage)
		{
			ResponseWriter writer = ctx.getResponseWriter();
			Panel playerPanel = new Panel();
			playerPanel.setHeader("Map Time Control");
			playerPanel.setId("mapTimePanel");
			
			HtmlOutputText timeText = new HtmlOutputText();
			timeText.setId("timeInfo");
			timeText.setValue(targetTime.getValue().toGMTString());
			playerPanel.getChildren().add(timeText);
			
			if (start)
			{
				CommandButton button = new CommandButton();
				button.setValue("Start");
				button.setOnclick("GeoJsfPlayer.start()");
				playerPanel.getChildren().add(button);
			}
			
			if (stop)
			{
				CommandButton button = new CommandButton();
				button.setValue("Stop");
				button.setOncomplete("GeoJsfPlayer.stop()");
				playerPanel.getChildren().add(button);
			}
			
			if (slider)
			{
				Slider slider = new Slider();
				slider.setMinValue(new Integer((int) this.getMin().getTime()));
				slider.setMaxValue(new Integer((int) this.getMax().getTime()));
				playerPanel.getChildren().add(slider);
			}
			
			
			this.getChildren().add(playerPanel);
			this.encodeChildren(ctx);
			
			//Get all layers/services that need to be updated when time changes
			ArrayList<String> layers = new ArrayList<String>();
			layers.addAll(targetMap.getTemporalLayerNames());
			
			writer.writeText(System.getProperty("line.separator"), null);
			writer.startElement("script", this);
			
			writer.writeText("GeoJsfPlayer.setTemporalLayers(" +gson.toJson(layers) +");", null);
			writer.writeText(System.getProperty("line.separator"), null);
			writer.writeText("GeoJsfPlayer.init(" +min.getTime() +"," +max.getTime() +", '" +timeText.getClientId()+"')" , null);
			
			writer.endElement("script");
			initStage = false;
		}
	}

	
	@Override public void restoreState(FacesContext context, Object state)
	{
	    Object[] storedState = (Object[]) state;
	    logger.info("Restoring state.");
		initStage   = (Boolean) storedState[0];
	}
	
	@Override public Object saveState(FacesContext context)
	{
	    Object[] rtrn = new Object[1];
	    rtrn[0] = initStage;
	    return rtrn;
	}
	
	

	@Override
	public String getFamily() {return null;}
	
	public void setTargetMap(Map targetMap) {this.targetMap = targetMap;}
	public Map getTargetMap() {return targetMap;}
	
	public void setTargetTime(Time targetTime) {this.targetTime = targetTime;}
	public Time getTargetTime() {return targetTime;}
	
	public void setMin(Date min) {this.min = min;}
	public Date getMin() {return min;}

	public void setMax(Date max) {this.max = max;}
	public Date getMax() {return max;}

	public void setStep(Long step) {this.step = step;}
	public Long getStep() {return step;}

	public void setFramerate(Double framerate) {this.framerate = framerate;}
	public Double getFramerate() {return framerate;}

	public void setSlider(Boolean slider) {this.slider = slider;}
	public Boolean getSlider() {return slider;}

	public void setStart(Boolean start) {this.start = start;}
	public Boolean getStart() {return start;}

	public void setStop(Boolean stop) {this.stop = stop;}
	public Boolean getStop() {return stop;}	

	public Date getNow() {return now;}
	public void setNow(Date now) {this.now = now;
	}
}