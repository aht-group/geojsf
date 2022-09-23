package org.geojsf.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.geojsf.controller.util.DummyViewFactory;
import org.geojsf.model.json.MapConfiguration;
import org.geojsf.model.json.MapData;
import org.geojsf.model.json.WmsLayer;
import org.geojsf.model.json.WmsService;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfCategory;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfLayer;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfMap;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfService;
import org.geojsf.model.pojo.geojsf.core.DefaultGeoJsfView;
import org.geojsf.model.pojo.geojsf.meta.DefaultGeoJsfDataSource;
import org.geojsf.model.pojo.geojsf.meta.DefaultGeoJsfScale;
import org.geojsf.model.pojo.geojsf.meta.DefaultGeoJsfViewPort;
import org.geojsf.model.pojo.geojsf.sld.DefaultGeoJsfSld;
import org.geojsf.model.pojo.geojsf.sld.DefaultGeoJsfSldRule;
import org.geojsf.model.pojo.geojsf.sld.DefaultGeoJsfSldTemplate;
import org.geojsf.model.pojo.geojsf.sld.DefaultGeoJsfSldType;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 *
 * @author helgehemmer
 */
public class TestJavaScript
{
	final static Logger logger = LoggerFactory.getLogger(TestJavaScript.class);
    String javaScriptLibraryPath = "src/main/resources/META-INF/resources/js.geojsf/";
    
    public TestJavaScript()
    {
    }
    
    @Test
    public void test() throws IOException, ScriptException
    {
        // Initialize the Mapper that reads/writes JSON
        ObjectMapper mapper = new ObjectMapper();
        
        // Create a dummy MapData object
        MapData mD = new MapData();
        
        // Create a dummy MapConfiguration and fill
        // in a value for testing
        MapConfiguration mC = new MapConfiguration();
        mC.setDivContainer("TestDIV");
        mC.setHeight(256);
        mC.setWidth(256);
        
        // Create a WmsService and add a WmsLayer
        WmsService wS = new WmsService();
        LinkedHashMap<Long, Boolean> layerVisibility = new LinkedHashMap<Long, Boolean>();
        layerVisibility.put(new Long(1), Boolean.TRUE);
        layerVisibility.put(new Long(2), Boolean.TRUE);
        wS.setLayerVisibility(layerVisibility);
        wS.setCode("wmsServiceCode");
        wS.setId(new Long(1));
        wS.setUrl("http://foo.bar");
        
        WmsLayer   wL = new WmsLayer();
        wL.setId(new Long(1));
        wL.setName("testLayer");
        wL.setVisible(Boolean.TRUE);
        
        // Set this to be the configuration, layer and service 
        // in the dummy MapData
        mD.setConfiguration(mC);
        
        ArrayList<WmsService> services = new ArrayList<WmsService>();
        services.add(wS);
        mD.setServices(services);
        
        ArrayList<WmsLayer>   layers   = new ArrayList<WmsLayer>();
        layers.add(wL);
        mD.setLayers(layers);
        
        // Show the resulting JSON on console
        mapper.writeValue(System.out, mD);
        
        // Add one blank line
        System.out.println("");
        System.out.println("");
        
        // Create a JavaScript engine
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
    
        // Put in the created mapData as JSON String
        engine.put("mapData", mapper.writeValueAsString(mD));
        
        // Show the configurations divContainer on console using
        // JavaScript
        engine.eval("var mapObj = JSON.parse(mapData);");
        engine.eval("print('JavaScript mapObj.configuration.divContainer says that divContainer of mapData is: ' +mapObj.configuration.divContainer)");
        
        // Add one blank line
        System.out.println("");
        System.out.println("");
        
        // Iterate layer visibility
        // This shows how to access the three data structures
        // within the JSON object (Array, Key-Value list)
        engine.eval("for (var layer in mapObj.services[0].layerVisibility) "
                + "{print('Layer ' +layer +'s visibility is currently '"
                + "+mapObj.services[0].layerVisibility[layer]);}");
        
        // Now, lets load a JavaScript File
        // But wait! We dont have window and document, so lets skip that!
        // Reader reader = new FileReader(javaScriptLibraryPath +"OpenLayers.js");
        // engine.eval(reader);
        
        // Better create the GeoJSF datamodel
        DummyViewFactory<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfCategory,DefaultGeoJsfService,DefaultGeoJsfLayer,DefaultGeoJsfMap,DefaultGeoJsfScale,DefaultGeoJsfView,DefaultGeoJsfViewPort,DefaultGeoJsfDataSource,DefaultGeoJsfSldTemplate,DefaultGeoJsfSldType,DefaultGeoJsfSld,DefaultGeoJsfSldRule> dvf;
        DefaultGeoJsfMap map;
        
        logger.error("NYI, we need a FactoryProvider here!");
//        dvf = DummyViewFactory.factory(DefaultGeoJsfLang.class,DefaultGeoJsfDescription.class,DefaultGeoJsfCategory.class,DefaultGeoJsfService.class,DefaultGeoJsfLayer.class,DefaultGeoJsfMap.class,DefaultGeoJsfView.class);
 //       map = dvf.getMap();
        Gson gson = new Gson();
 //       engine.put("dmMap", map);
        engine.eval("print('Map ID: ' +dmMap.id);");
    }
    
}
