package org.geojsf.component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.ContextCallback;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.util.GeoJsfJsLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Player")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Player extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Player.class);
	
	private Map map;
	
	private String pattern;
	private String target;
	private String value;
	private Date min;
	private Date max;
	private Date date;
	private Date now;
	private Long delta;
	private Boolean slider;
	private Boolean start;
	private Boolean stop;
	private Boolean reset;
	private Double speed;
	
	private UIComponent found;
	
//	private ArrayList<GeoJsfService> timeLayers = (ArrayList<GeoJsfService>) ((Map) this.getParent()).getServiceList();
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"control.js");
		}
	/*	HtmlCommandButton button = new HtmlCommandButton();
		button.setLabel("Click Me");
		button.setTitle("Click Title");
		this.getParent().getParent().getChildren().add(button);*/
		//map = (Map) FacesContext.getCurrentInstance().getViewRoot().findComponent("map");
		
		super.processEvent(event);
	}
	
	public UIComponent findComponent(final String id){
		FacesContext context = this.getFacesContext();
		context.getViewRoot().invokeOnComponent(context, id,
			      new ContextCallback() {
			         public void invokeContextCallback(FacesContext context,
			                                       UIComponent component) {
			        	 logger.info("Found component: " +id);
			           found = component;
			         }
			      });
		return found;
	}
	
	@Override
	public void encodeAll(FacesContext ctx) throws IOException
	{
		map = (Map) findComponent("testForm:map");
		logger.info("Map found with " +map.getServiceList().size() +" layer definitions. Current time: ");
		for (GeoJsfService service : map.getServiceList())
		{
			logger.info("Does Service has temporal information? " +!MapUtil.hasTemporalLayer(service).isEmpty());
			for (Object o : service.getLayer())
			{
				GeoJsfLayer layer = (GeoJsfLayer) o;
				logger.info("Layer: " +layer.getName());
			}
		}
		
		//Render instructions:
		// Init the timeStep and currentTime
		
		//IMPORTANT! For not running intro conflicts, update the Time info on Map via Ajax!!!
		
		//Play Button:
		// call play function

		ResponseWriter writer = ctx.getResponseWriter();
		//for (GeoJsfService layer : timeLayers)
		//{
		//	writer.writeText("Layer timeLayer = GeoJSF.map.getLayerByName('" +layer.getCode() +"')", null);
		//	writer.writeText("timeLayer.mergeNewParams(time: " +now +")", null);
		//}
		//        writer.writeText("GeoJsfControl.addTimeManager();", null); 
		// attribute for or something to connect to map (instead of "parent").
		// build jsf buttons as html
		// maybe adding htmlbutton as child programatically???
		// put the javascript code in rendering?? or better use ajax and do the rendering in render stage??
		
		
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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Date getMin() {
		return min;
	}

	public void setMin(Date min) {
		this.min = min;
	}

	public Date getMax() {
		return max;
	}

	public void setMax(Date max) {
		this.max = max;
	}

	public Long getDelta() {
		return delta;
	}

	public void setDelta(Long delta) {
		this.delta = delta;
	}

	public Boolean getSlider() {
		return slider;
	}

	public void setSlider(Boolean slider) {
		this.slider = slider;
	}

	public Boolean getStart() {
		return start;
	}

	public void setStart(Boolean start) {
		this.start = start;
	}

	public Boolean getStop() {
		return stop;
	}

	public void setStop(Boolean stop) {
		this.stop = stop;
	}

	public Boolean getReset() {
		return reset;
	}

	public void setReset(Boolean reset) {
		this.reset = reset;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}
}