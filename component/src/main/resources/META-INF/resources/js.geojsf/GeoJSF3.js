/**
 * Protect window.console method calls, e.g. console is not defined on IE
 * unless dev tools are open, and IE doesn't define console.debug
 */
(function() {
  if (!window.console) {
    window.console = {};
  }
  // union of Chrome, FF, IE, and Safari console methods
  var m = [
    "log", "info", "warn", "error", "debug", "trace", "dir", "group",
    "groupCollapsed", "groupEnd", "time", "timeEnd", "profile", "profileEnd",
    "dirxml", "assert", "count", "markTimeline", "timeStamp", "clear"
  ];
  // define undefined methods as noops to prevent errors
  for (var i = 0; i < m.length; i++) {
    if (!window.console[m[i]]) {
      window.console[m[i]] = function() {};
    }    
  } 
})();

var GeoJSF = {	
		map: null,
		switcher: null,
		commandCorrect: true,
		updateOnClick: null,
		updateOnMove: null,
		id: null,
		lastLayer: null,
		baseLayer: null,
		scaleValues: null,
		
		setAjaxUpdates : function(updateClicks, updateMove)
		{
			this.updateOnClick = updateClicks;
			this.updateOnMove  = updateMove;
		},
		
		setScaleValues : function(values)
		{
			GeoJSF.scaleValues = values;
			console.log('Allowed scale values are: ' +this.scaleValues);
		},
		
		getCurrentLocation : function()
		{
			navigator.geolocation.getCurrentPosition(GeoJSF.processCurrentLocation);
		},
		
		processCurrentLocation : function(location)
		{
		    console.log("You are at: (lat/lon) (" +location.coords.latitude +"/" +location.coords.longitude +") with an accurancy of " +location.coords.accuracy);
		},
		
		registerEventHandlers : function()
		{
			
				try {
				console.log("Trying to register event handlers");
				GeoJSF.map.on("singleclick",  GeoJSF.processEventClick);
				GeoJSF.map.on("moveend"    ,  GeoJSF.processEventMove);
			 	console.log("Done registering.");
				}
				catch (e) {alert("Problem with registering Map events: " +e)}
		},
		
		processEventMove : function(event)
		{
			console.log("mapMove");
			console.log(event);
				
						
			this.centerLat             = ol.proj.transform(event.map.getView().getCenter(), 'EPSG:3857', 'EPSG:4326')[0];
			this.centerLon             = ol.proj.transform(event.map.getView().getCenter(), 'EPSG:3857', 'EPSG:4326')[1];
            
			// var extent = event.map.getView().calculateExtent(event.map.getSize());
			var extent = ol.extent.applyTransform(event.map.getView().calculateExtent(event.map.getSize()), ol.proj.getTransform("EPSG:3857", "EPSG:4326"));
			this.viewportBoundTop      = extent[0];
			this.viewportBoundBottom   = extent[1];
			this.viewportBoundLeft     = extent[2];
            this.viewportBoundRight    = extent[3];
			console.log(event.type +" has triggered change to center " +this.centerLat +"/"  +this.centerLon);
			
			
			try {
				PrimeFaces.ab({
					process:  '@form', 
		 			source:   GeoJSF.map.getTarget(), 
		 			event:    'mapMove', 
		 			update:   GeoJSF.updateOnMove,
					params: [
		 			         {name: 'org.geojsf.viewport.center.lon',     value: this.centerLon},
		 			         {name: 'org.geojsf.viewport.center.lat',     value: this.centerLat},
		 			         {name: 'org.geojsf.viewport.bottom',         value: this.viewportBoundBottom},
		 			         {name: 'org.geojsf.viewport.top',            value: ol.extent.applyTransform(event.map.getView().calculateExtent(event.map.getSize()), ol.proj.getTransform("EPSG:3857", "EPSG:4326"))[0]},
		 			         {name: 'org.geojsf.viewport.left',           value: this.viewportBoundLeft},
		 			         {name: 'org.geojsf.viewport.right',          value: this.viewportBoundRight},
		 			         {name: 'org.geojsf.viewport.scale',          value:   Math.floor( GeoJSF.map.getView().getResolution())},
		 			        ],
		 			oncomplete: function(xhr, status, args) {console.log('map move AJAX request sent.')}
				});
			} catch(e) {
				 console.log("MapMove failed." +e);
			}
			finally {}
		},
		
		processEventClick : function(event)
		{
			console.log("mapClick detected");
			this.centerLat             = event.map.getView().getCenter()[0];
			this.centerLon             = event.map.getView().getCenter()[1];
                        var extent = event.map.getView().calculateExtent(event.map.getSize());
			this.viewportBoundTop      = extent[0];
			this.viewportBoundBottom   = extent[1];
			this.viewportBoundLeft     = extent[2];
                	this.viewportBoundRight    = extent[3];
                        var latlon                 = ol.proj.transform(event.coordinate, 'EPSG:3857', 'EPSG:4326');
			try {
				PrimeFaces.ab({
					process:  '@form', 
		 			source:   GeoJSF.map.getTarget(), 
		 			event:    'mapClick', 
		 			update:   GeoJSF.updateOnClick,
					params: [
		 			         {name: 'org.geojsf.click.lat',				value: latlon[0]},
                                                 {name: 'org.geojsf.click.lon',				value: latlon[1]},
		 			         {name: 'org.geojsf.viewport.center.lon',               value: this.centerLon},
		 			         {name: 'org.geojsf.viewport.center.lat',               value: this.centerLat},
		 			         {name: 'org.geojsf.viewport.bottom',                   value: this.viewportBoundBottom},
		 			         {name: 'org.geojsf.viewport.top',                      value: this.viewportBoundTop},
		 			         {name: 'org.geojsf.viewport.left',                     value: this.viewportBoundLeft},
		 			         {name: 'org.geojsf.viewport.right',                    value: this.viewportBoundRight},
		 			         {name: 'org.geojsf.viewport.scale',                    value:   Math.floor( GeoJSF.map.getView().getResolution())*50},
		 			        ],
		 			oncomplete: function(xhr, status, args) {console.log('mapClick AJAX request sent.')}
				});
			} catch(e) {
				 console.log("MapClick failed." +e);
			}
				finally {}
		},
		
		initMap : function(mapDiv,baseMap)
		{
                        var layers = [];
                        if (baseMap=="osm")
                        {
                            layers.push(new ol.layer.Tile({
                                              source: new ol.source.OSM()
                                            }));
                        }
			GeoJSF.map = new ol.Map({
                                controls     : ol.control.defaults({ 
                                                attribution: false,
                                                zoom       : false,
                                                rotate     : false
                                                }),
                                layers       : layers,
                                target       : mapDiv
                            });
                            
                            // This is important due to the calculations of the extent needed
                            // in initial TILED parameter TILESORIGIN in WMS
                            GeoJSF.map.getView().setCenter([0,0]);
                            GeoJSF.map.getView().setResolution(1000);
		},
		
		// Remove all layers and add the base layer again
			resetLayers : function()
		{
			if (GeoJSF.map.layers)
				{
					for (var i = GeoJSF.map.layers.length - 1; i >= 0; i--) {
					    GeoJSF.map.removeLayer(GeoJSF.map.layers[i]);
					}
				}
		},
		
		addLayer : function(name, url, params, options)
		{
                    console.log("Trying to add " +params.layers);
                    var sizeOfMap = GeoJSF.map.getSize();
                    console.log("Size of Map:" +sizeOfMap);
                    
                    var viewOfMap = GeoJSF.map.getView();
                    console.log("View of Map:" +viewOfMap);
                    console.log("View Projection: " +viewOfMap.getProjection().getCode());
                    console.log("View Center: " +viewOfMap.getCenter());
                    
                    var bounds = viewOfMap.calculateExtent(sizeOfMap);
                    console.log("Bounds: " +bounds);
                    
                    // Get an array of bounds
                    // e.g. [1555173.7562473097, 1494886.6852190704, 1603273.7562473097, 1534886.6852190704]
                    var extents     = GeoJSF.map.getView().calculateExtent(GeoJSF.map.getSize());
                    console.log("Extents:" +extents);
                    
                    // Transform this extent array to GPS coordinates (from Mercator)
                    // e.g. [13.970363546985254, 13.30751144611122, 14.402453198646745, 13.656934756062881]
                    var extent      = ol.proj.transformExtent(extents, 'EPSG:3857', 'EPSG:4326');

                    // Now get the bottom left of this extent array
                    // e.g. [13.970363546985254, 13.30751144611122]
                    var coordinates = ol.extent.getBottomLeft(extent);

                    console.log(coordinates);

                    var layer = new ol.layer.Tile({
                        source: new ol.source.TileWMS({
                                url        : url,
                                params     : {'LAYERS'     : params.layers,
                                              'TILED'      : true,
                                              'TILESORIGIN': coordinates[0] +", " +coordinates[1],
                                             },
                                serverType : 'geoserver'
                                 })
                        });
                    layer.set('name', name);
                    GeoJSF.map.addLayer(layer);
		},
		
		ajaxResponse : function(data)
		{
			console.log(data);
		},
		
		updateTime : function(layerName, time)
		{
			 var layers = this.map.getLayersByName(layerName);
			 var layer  = layers[0];
			 var params = {};
			 var date  = new Date(time);
			 var isoTime = date.toISOString();
			 params.time = isoTime;
			 console.log("Merging new Time parameter: " +isoTime);
			 layer.mergeNewParams(params);
			 
			// This is the PrimeFaces based solution along with an 'oncomplete' call
			 PrimeFaces.ab({process: '@all', 
				 			source: this.map.div, 
				 			event: 'updateTime', 
				 			params: [{name: 'org.geojsf.update.time', value: time}],
				 			oncomplete: function(xhr, status, args) {console.log("Back in client.");}});
		
		},
		
		updateSqlViewParams : function(parameters)
		{
			 console.log("SQL View Parameter Update requested.");
			 var params = {};
			 params.viewparams = parameters;
			 if (GeoJSF.map.getLayers())
                         {
                            GeoJSF.map.getLayers().forEach(function (layer) {
                                if (layer.getSource() instanceof ol.source.OSM)
                                {
                                   console.log("Ignoring OSM base layer"); 
                                }
                                else
                                {
                                    layer.getSource().updateParams(params);
                                    console.log("Merging new SqlParameter parameter: " +params.viewparams);
                                }
                            });
                         }
		},
		
		updateSldParams : function(parameters)
		{
			 console.log("SLD Parameter Update requested.");
			 var params = {};
			     params.env = parameters;
                         if (GeoJSF.map.getLayers())
                         {
                            GeoJSF.map.getLayers().forEach(function (layer) {
                                if (layer.getSource() instanceof ol.source.OSM)
                                {
                                   console.log("Ignoring OSM base layer"); 
                                }
                                else
                                {
                                    layer.getSource().updateParams(params);
                                    console.log("Merging new SldParameter parameter: " +params.env);
                                }
                            });
                         }
		},
		
		switchLayerClassic : function(layerId)
		{
			 // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			 // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			 
			console.log("switch request detected for layer " +layerId);
			try{ 
				PrimeFaces.ab({
			 			process: '@form', 
			 			source: GeoJSF.map.getTarget(), 
			 			event: 'layerSwitch', 
			 			update: '@form',
			 			params: [{name: 'org.geojsf.switch.layer', value: layerId}],
			 			oncomplete: function(xhr, status, args) {GeoJSF.performLayerSwitch(xhr, status, args);}
				});
			} catch(e) {
				 console.log("layer switch failed." +e);
			} 
			finally {}
		},
		
		secondRun : function()
		{
			PrimeFaces.ab({process: '@all', 
				 			source: GeoJSF.map.getTarget(), 
				 			update: '@form',
				 			event: 'updateModel', 
				 			oncomplete: function(xhr, status, args) {console.log("Update complete.");}
			});
		},
		
		refreshMap : function()
		{
			 // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			 // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			 
			 // This is the PrimeFaces based solution along with an 'oncomplete' call
			 PrimeFaces.ab({process: '@all', 
				 			source: GeoJSF.map.getTarget(), 
				 			event: 'updateMap', 
				 			params: [],
				 			oncomplete: function(xhr, status, args) {GeoJSF.performLayerSwitch(xhr, status, args);}});
		},
		
		performLayerSwitch : function(xhr, status, args)
		{
			try {
			console.log("Performing layer switch via OpenLayers.");
                        console.log(args);
                        console.log(args.toggleLayer);
                        console.log(JSON.parse(args.toggleLayer));
			var command = JSON.parse(args.toggleLayer);
			console.log("Map has " +GeoJSF.map.getLayers().getLength() +" layers");
		        console.log("OpenLayers: Please set " +command.serviceId +" to have the layers " +command.layer +" using the method " +command.command);
			if (command.command == "hide")
				{
					for (var i = GeoJSF.map.getLayers().getLength() - 1; i >= 0; i--) {
                                        if(GeoJSF.map.getLayers().item(i).get('name') == command.serviceId ) 
                                        {
                                            GeoJSF.map.getLayers()[i].setVisibility(false);
                                        } else {console.log('big problem');}
				}}
			if (command.command == "show")
				{
                                    for (var i = GeoJSF.map.getLayers().getLength() - 1; i >= 0; i--) {
                                        if(GeoJSF.map.getLayers().item(i).get('name') == command.serviceId ) 
                                        {
                                            GeoJSF.map.getLayers().item(i).setVisibility(true);
                                        } else {console.log('big problem');}
				}}
                        if (command.command == "merge")
				{
			    	var layersToAdd = "";
                                
                                    for (var i = GeoJSF.map.getLayers().getLength() - 1; i >= 0; i--) {
                                        if(GeoJSF.map.getLayers().item(i).get('name') == command.serviceId ) 
                                        {
                                            for (var counter in command.layer)
                                                    {
                                                            layersToAdd += command.layer[counter];
                                                            layersToAdd += ",";
                                                    }
                                            var layerList = layersToAdd.substring(0, layersToAdd.length - 1);
                                            console.log("Trying to add " +layerList +" to Layer " +GeoJSF.map.getLayers().item(i).get('name'));
                                            var params = {};
                                            params.LAYERS = layerList;
                                            console.log("New parameter set: " +params);
                                            GeoJSF.map.getLayers().item(i).getSource().updateParams(params);
                                        } else {console.log('big problem');}
				}
			}
			} catch(e) {
				 console.log("A problem occured when interpreting layer switch command. Maybe NULL?" +e);
			}
			finally {}
			GeoJSF.secondRun();
		}
};