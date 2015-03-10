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
		viewportCenterLon: null,
		viewportCenterLat: null,
		viewportBoundTop: null,
		viewportBoundBottom: null,
		viewportBoundLeft: null,
		viewportBoundRight: null,
		updateOnClick: null,
		updateOnMove: null,
		id: null,
		lastLayer: null,
		eventsRegistered: false,
		baseLayer: null,
		
		setAjaxUpdates : function(updateClicks, updateMove)
		{
			this.updateOnClick = updateClicks;
			this.updateOnMove  = updateMove;
		},
		
		// Deprecated, now done in "loadend" event of last layer
		addLoadEvent : function()
		{
		//	OpenLayers.Event.observe(GeoJSF.map.div, "load", GeoJSF.registerEventHandlers ); 
		},
		
		getCurrentLocation : function()
		{
			navigator.geolocation.getCurrentPosition(GeoJSF.processCurrentLocation);
		},
		
		processCurrentLocation : function(location)
		{
		    console.log("You are at: (lat/lon) (" +location.coords.latitude +"/" +location.coords.longitude +") with an accurancy of " +location.coords.accuracy);
		},
		
		register : function(layerName)
		{
			try {
				var layers = this.map.getLayersByName(layerName);
				if(layers.length === 1) {
					loadLayer = layers[0];
				    loadLayer.events.register("loadend", loadLayer, function(e) {GeoJSF.lastLayer = layerName; GeoJSF.registerEventHandlers(e);} );
			    }
			    console.log("loadend event processing completed."); 
			} catch (e)
			{
					console.log("Problem when adding loadend event listener: " +e);
			}
		},
		
		registerEventHandlers : function(e)
		{
			if (!GeoJSF.eventsRegistered){
				try {
				console.log("Trying to register event handlers");
				GeoJSF.map.events.register("click",  GeoJSF.map,GeoJSF.processEventClick,true);
				GeoJSF.map.events.register("movestart",GeoJSF.map,GeoJSF.processEventMoveStart ,true);
				GeoJSF.map.events.register("moveend",GeoJSF.map,GeoJSF.processEventMove ,true);
			 	console.log("Done registering.");
			 	GeoJSF.eventsRegistered = true;
				}
				catch (e) {alert("Problem with registering Map events: " +e)}
			}
			else
			{
				try{
					console.log("Removing listener for " +GeoJSF.lastLayer);
					var layers = this.map.getLayersByName(GeoJSF.lastLayer);
					if(layers.length === 1) {
						loadLayer = layers[0];
						loadLayer.events.remove("loadend");
					}
				} catch (e)
				{
					console.log("Problem when removing loadend listener: " +e);}
			}
		},
		
		processEventMove : function(event)
		{
			console.log("mapMove");
			this.centerLon = event.object.getCenter().lon;
			this.centerLat = event.object.getCenter().lat;
			this.viewportBoundTop = event.object.getExtent().top;
			this.viewportBoundBottom = event.object.getExtent().bottom;
			this.viewportBoundLeft = event.object.getExtent().left;
			this.viewportBoundRight = event.object.getExtent().right;
			console.log(event.type +" has triggered change to center " +this.centerLon +"/"  +this.centerLat);
			$('.olTileImage').each(function(idx){
		        console.log( $(this) );
		    });
		
			try {
				PrimeFaces.ab({
					process:  '@form', 
		 			source:   GeoJSF.map.div, 
		 			event:    'mapMove', 
		 			update:   GeoJSF.updateOnMove,
					params: [
		 			         {name: 'org.geojsf.viewport.center.lon',     value: this.centerLon},
		 			         {name: 'org.geojsf.viewport.center.lat',     value: this.centerLat},
		 			         {name: 'org.geojsf.viewport.bottom',  value: this.viewportBoundBottom},
		 			         {name: 'org.geojsf.viewport.top',     value: this.viewportBoundTop},
		 			         {name: 'org.geojsf.viewport.left',    value: this.viewportBoundLeft},
		 			         {name: 'org.geojsf.viewport.right',   value: this.viewportBoundRight},
		 			         {name: 'org.geojsf.viewport.scale', value: GeoJSF.map.getScale()},
		 			        ],
		 			oncomplete: function(xhr, status, args) {console.log('map move AJAX request sent.')}
				});
			} catch(e) {
				 console.log("MapMove failed." +e);
			}
			finally {}
		},
		
		processEventMoveStart : function(event)
		{
			console.log("mapMoveStart");
			$('.olTileImage').each(function(idx){
		        console.log( $(this) );
		//      $(this).css('cursor','default ');
		    });
		},
		
		processEventClick : function(event)
		{
			console.log("mapClick detected");
			GeoJSF.map.div.style.cursor = 'default ';
			console.log(GeoJSF.map.div);
			$(GeoJSF.map.div).css('cursor','default ');
		    $(GeoJSF.map.div.id).children( ".olTileImage" ).each(function(i) { 
		    });
		    
		    $('.olTileImage').each(function(idx){
		        $(this).css('cursor','default ');
		    });
			try {
				PrimeFaces.ab({
					process:  '@form', 
		 			source:   GeoJSF.map.div, 
		 			event:    'mapClick', 
		 			update:   GeoJSF.updateOnClick,
					params: [
		 			         {name: 'org.geojsf.click.lon',				value: GeoJSF.map.getLonLatFromViewPortPx(event.xy).lon},
		 			         {name: 'org.geojsf.click.lat',				value: GeoJSF.map.getLonLatFromViewPortPx(event.xy).lat},
		 			         {name: 'org.geojsf.viewport.center.lat',	value: GeoJSF.map.getExtent().getCenterLonLat().lat},
		 			         {name: 'org.geojsf.viewport.center.lon',  	value: GeoJSF.map.getExtent().getCenterLonLat().lon},
		 			         {name: 'org.geojsf.viewport.bottom',		value: GeoJSF.map.getExtent().bottom},
		 			         {name: 'org.geojsf.viewport.top',			value: GeoJSF.map.getExtent().top},
		 			         {name: 'org.geojsf.viewport.left',			value: GeoJSF.map.getExtent().left},
		 			         {name: 'org.geojsf.viewport.right',		value: GeoJSF.map.getExtent().right},
		 			         {name: 'org.geojsf.viewport.scale',		value: GeoJSF.map.getScale()}
		 			        ],
		 			oncomplete: function(xhr, status, args) {console.log('mapClick AJAX request sent.')}
				});
			} catch(e) {
				 console.log("MapClick failed." +e);
			}
				finally {}
		},
		
		initMap : function(mapDiv,msOptions,height, width)
		{
			sizeParam = new OpenLayers.Size(256,256);
			GeoJSF.map = new OpenLayers.Map(mapDiv,{
			//	eventListeners: {
				//	'moveend': GeoJSF.processEventMove,
				//	'click':   GeoJSF.processEventClick
		       // },
				controls: [new OpenLayers.Control.Navigation()], 
				version: '1.1.0', 
				request: 'GetMap',
				srs: 'EPSG:4326', 
				height: height, 
				width: width, 
				theme: null, 
				tileSize: sizeParam,
			//	allOverlays: true,
				});

			/* This is experimental adding of Google and/or OpenStreetMap layers
					 GeoJSF.baseLayer = new OpenLayers.Layer.Google(
					            "Google Streets", // the default
					            {numZoomLevels: 20}
					        );
					 GeoJSF.baseLayer = new OpenLayers.Layer.OSM();
				 	var osm = new OpenLayers.Layer.OSM();            
				    var gmap = new OpenLayers.Layer.Google("Google Streets");
				    var google_maps = new OpenLayers.Layer.Google(
				   	     "Google Maps", {
				   	          numZoomLevels: 20
				   	     }
				   	);
				   	var google_satellite = new OpenLayers.Layer.Google(
				        "Google Satellite", {
				           type: google.maps.MapTypeId.SATELLITE,
				         numZoomLevels: 20
				    }
				  	);
				  	GeoJSF.map.addLayers([this.google_maps, this.google_satellite]);
				    GeoJSF.map.addLayers([osm]);
					this.map.addLayer(new OpenLayers.Layer.OSM());
					GeoJSF.map.addLayer(GeoJSF.baseLayer);
			*/
			
		    OpenLayers.DOTS_PER_INCH = 25.4 / 0.28;
		    // For centering the map
		    // GeoJSF.map.setCenter( new OpenLayers.LonLat(30.06119013, -1.95854944) .transform( new OpenLayers.Projection("EPSG:4326"), this.map.getProjectionObject() ), 17 );

		    // Bootstrapping jQuery
		    if (!window.jQuery) {
		    	  console.log("Bootstrap jQuery if not present.");
		    	  var jq = document.createElement('script'); jq.type = 'text/javascript';
		    	  // Path to jquery.js file, eg. Google hosted version
		    	  jq.src = '//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js';
		    	  document.getElementsByTagName('head')[0].appendChild(jq);
		    	  alert("Added jQuery.");
		    	}
		    $(GeoJSF.map.div).on('mouseout', function() {
		    	$(this).css('cursor','default ');
		    });
		},
		
		// Remove all layers and add the base layer again
			resetLayers : function()
		{
			if (this.map.layers)
				{
					for (var i = this.map.layers.length - 1; i >= 0; i--) {
					    this.map.removeLayer(map.layers[i]);
					}
				}
		},
		
		/*		
		resetLayers : function()
		{
			if (GeoJSF.map)
				{
					if (GeoJSF.map.layers)
					{
						for (var i = GeoJSF.map.layers.length - 1; i >= 0; i--) {
						    GeoJSF.map.removeLayer(GeoJSF.map.layers[i]);
						}
					}
				}
			
			GeoJSF.map.addLayer(GeoJSF.baseLayer);
		},
		*/
		addLayer : function(name, url, layers, params)
		{
		//	layers.tiled = true;
	    //    layers.tilesorigin = GeoJSF.map.maxExtent.left + ',' + GeoJSF.map.maxExtent.bottom;
	        
			var layer = new OpenLayers.Layer.WMS(
					  name, 
					  url, 
					  layers,
					  params);
			this.map.addLayer(layer);
			this.map.zoomToMaxExtent();
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
			 if (GeoJSF.map.layers)
				{
					for (var i = GeoJSF.map.layers.length - 1; i >= 0; i--) {
					    GeoJSF.map.layers[i].mergeNewParams(params);
					}
				}
			 console.log("Merging new SqlViewParameter parameter: " +params.viewparams);
		},
		
		updateSldParams : function(parameters)
		{
			 console.log("SLD Parameter Update requested.");
			 var params = {};
			 params.env = parameters;
			 if (GeoJSF.map.layers)
				{
					for (var i = GeoJSF.map.layers.length - 1; i >= 0; i--) {
					    GeoJSF.map.layers[i].mergeNewParams(params);
					}
				}
			 console.log("Merging new SldParameter parameter: " +params.env);
		},
		
		switchLayerClassic : function(layerId)
		{
			 // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			 // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			 
			console.log("switch request detected for layer " +layerId);
			try{ 
				PrimeFaces.ab({
			 			process: '@form', 
			 			source: GeoJSF.map.div, 
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
				 			source: GeoJSF.map.div, 
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
				 			source: GeoJSF.map.div, 
				 			event: 'updateMap', 
				 			params: [],
				 			oncomplete: function(xhr, status, args) {GeoJSF.performLayerSwitch(xhr, status, args);}});
		},
		
		performLayerSwitch : function(xhr, status, args)
		{
			try {
			console.log("Performing layer switch via OpenLayers.");
			var command = JSON.parse(args.toggleLayer);
			
		//    alert("OpenLayers: Please set " +command.serviceId +" to have the layers " +command.layer +" using the method " +command.command);
			if (command.command == "hide")
				{
					var layers = this.map.getLayersByName(command.serviceId);
				    if(layers.length === 1) {
				    	layers[0].setVisibility(false);
				    } else {console.log('big problem');}
				}
			if (command.command == "show")
				{
					var layers = this.map.getLayersByName(command.serviceId);
				    if(layers.length === 1) {
				    	layers[0].setVisibility(true);
				    } else {console.log('big problem');}
				}
		    if (command.command == "merge")
				{
			    	var layers = this.map.getLayersByName(command.serviceId);
			    	var layersToAdd = "";
				    if(layers.length === 1) {
				    	var wmsLayer = layers[0];
				    	var params = wmsLayer.params;
				    	for (var counter in command.layer)
				    		{
					    		layersToAdd += command.layer[counter];
					    		layersToAdd += ",";
				    		}
				    	var layerList = layersToAdd.substring(0, layersToAdd.length - 1);
				    	var wmsLayer = layers[0];
				    	console.log("Trying to add " +layerList +" to Layer " +wmsLayer.url);
				    	params.layers = layerList;
				    	console.log("New parameter set: " +params);
				    	wmsLayer.mergeNewParams(params);
				    	} else {console.log('big problem');}
			}
			} catch(e) {
				 console.log("A problem occured when interpreting layer switch command. Maybe NULL?");
			}
			finally {}
			GeoJSF.secondRun();
		}
};