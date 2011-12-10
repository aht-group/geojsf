package net.sf.geojsf.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.geojsf.util.factory.OlLegendFactory;
import net.sf.geojsf.util.json.WmsLayer;
import net.sf.geojsf.xml.openlayers.Layer;
import net.sf.geojsf.xml.openlayers.Layers;
import net.sf.geojsf.xml.openlayers.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OlWmsLayer implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(OlWmsLayer.class);
	
	public static final long serialVersionUID=1;
	
	private Map<String,Layer> mAvailableLayer;
	private Map<String,Boolean> mapViewShow,mapViewLegendShow;
	
	private View view;
	
//	private EdtMultiSelectionControl layerControl;
	private List<Layer> layers;
	private String wmsUrl,wcsUrl;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	

	public OlWmsLayer(Layers availableLayer, View view)
	{
		this.view=view;
		initAvailable(availableLayer.getLayer());
		initViewAttributes();
		
		wmsUrl=availableLayer.getWms();
		wcsUrl=availableLayer.getWcs();
		
//		layerControl = new EdtMultiSelectionControl();
		layers = new ArrayList<Layer>();
		int i=1;
		
		for(Layer layer : view.getLayer())
		{
			layer.setId(i);
			Layer addLayer = mAvailableLayer.get(layer.getCode());
			addLayer.setId(i);
			addLayer.setShowLegend(mapViewLegendShow.get(addLayer.getCode()));
			addLayer.setLegend(OlLegendFactory.createLegend(wmsUrl,addLayer));
			layers.add(addLayer);
			logger.trace("Adding layer: "+addLayer.getCode());
//			layerControl.setSelectedItem(addLayer);
//			if(mapViewShow.get(addLayer.getCode())){layerControl.select(true);}
			i++;
		}
	}
	
	private void initAvailable(List<Layer> lLayer)
	{
		mAvailableLayer = new Hashtable<String,Layer>();
		for(Layer layer : lLayer)
		{
			mAvailableLayer.put(layer.getCode(), layer);
		}
	}
	
	private void initViewAttributes()
	{
		mapViewShow = new Hashtable<String,Boolean>();
		mapViewLegendShow = new Hashtable<String,Boolean>();
		for(Layer viewLayer : view.getLayer())
		{
			boolean layerVisible = false;
			if(viewLayer.isSetVisible() && viewLayer.isVisible()){layerVisible=true;}
			mapViewShow.put(viewLayer.getCode(), layerVisible);
			
			boolean legendShow = true;
			if(viewLayer.isSetShowLegend()){legendShow=viewLayer.isShowLegend();}
			mapViewLegendShow.put(viewLayer.getCode(), legendShow);
		}
	}
	
	// >>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<
	
	public String getWcsForLayer(String layerCode)
	{
		return wcsUrl;
	}
	
	
	public WmsLayer getValue()
	{
		WmsLayer wmsLayer = new WmsLayer();
		wmsLayer.setParams(getParams(wmsLayer));
		wmsLayer.setUrl(wmsUrl);
		wmsLayer.setName("AhtLayer");
//		logger.debug("Requesting WmsLayer: "+wmsLayer.getUrl()+" "+wmsLayer.getParams());
		return wmsLayer;
	}
	
	private WmsLayer.Params getParams(WmsLayer wmsLayer)
	{
		WmsLayer.Params params = wmsLayer.new Params();

		StringBuffer sb = new StringBuffer();
		boolean oneVisible=false;
		
		for(Layer l : view.getLayer())
		{
			long key = l.getId();
			Layer layer = mAvailableLayer.get(l.getCode());
/*			boolean keyContaines = layerControl.getMapSelectedItems().containsKey(key);
			
			if(keyContaines && layerControl.getMapSelectedItems().get(key))
*/			{
				sb.append(layer.getWorkspace());
				sb.append(":");
				sb.append(layer.getCode());
				sb.append(",");
				oneVisible = true;
			}
		}
		if(oneVisible){sb = sb.deleteCharAt(sb.length()-1);}
		params.setLayers(sb.toString());
		params.setFormat("image/png");
		
		return params;
	}
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
	
	public List<Layer> getLayers() {return layers;}
	
//	public EdtMultiSelectionControl getLayerControl() {return layerControl;}
//	public void setLayerControl(EdtMultiSelectionControl layerControl) {this.layerControl = layerControl;}
}
