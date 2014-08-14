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
		
		setAjaxUpdates : function(updateClicks, updateMove)
		{
			this.updateOnClick = updateClicks;
			this.updateOnMove  = updateMove;
		},
		
		addLoadEvent : function()
		{
			OpenLayers.Event.observe(window, "load", GeoJSF.registerEventHandlers ); 
		},
		
		registerEventHandlers : function()
		{
			GeoJSF.map.events.register("moveend",GeoJSF.map,GeoJSF.processEventMove ,true);
			GeoJSF.map.events.register("click",  GeoJSF.map,GeoJSF.processEventClick,true);
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
			
			try {
				PrimeFaces.ab({
					process:  '@form', 
		 			source:   GeoJSF.map.div, 
		 			event:    'mapMove', 
		 			update:   GeoJSF.updateOnMove,
					params: [
		 			         {name: 'org.geojsf.viewport.lon',     value: this.centerLon},
		 			         {name: 'org.geojsf.viewport.lat',     value: this.centerLat},
		 			         {name: 'org.geojsf.viewport.bottom',  value: this.viewportBoundBottom},
		 			         {name: 'org.geojsf.viewport.top',     value: this.viewportBoundTop},
		 			         {name: 'org.geojsf.viewport.left',    value: this.viewportBoundLeft},
		 			         {name: 'org.geojsf.viewport.right',   value: this.viewportBoundRight},
		 			         {name: 'org.geojsf.coodinates.scale', value: GeoJSF.map.getScale()},
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
			try {
				PrimeFaces.ab({
					process:  '@form', 
		 			source:   GeoJSF.map.div, 
		 			event:    'mapClick', 
		 			update:   GeoJSF.updateOnClick,
					params: [
		 			         {name: 'org.geojsf.coordinates.lon',  value: GeoJSF.map.getLonLatFromViewPortPx(event.xy).lon},
		 			         {name: 'org.geojsf.coordinates.lat',  value: GeoJSF.map.getLonLatFromViewPortPx(event.xy).lat},
		 			         {name: 'org.geojsf.coodinates.scale', value: GeoJSF.map.getScale()}
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
				});
		    OpenLayers.DOTS_PER_INCH = 25.4 / 0.28;
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
		
		addLayer : function(name, url, layers, params)
		{
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