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
		
		bootstrap : function()
		{
			var head = document.getElementsByTagName("head")[0];
		    var s = document.createElement("script");
		    s.type = "text/javascript";
		    s.src = "http://openlayers.org/api/OpenLayers.js";
		    head.appendChild(s);
		},
		
		setAjaxUpdates : function(updateClicks, updateMove)
		{
			this.updateOnClick = updateClicks;
			this.updateOnMove  = updateMove;
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
			console.log(event.type +" has triggered change to center " +this.centerLon +"/"  +this.centerLat +" Bounds: (" +this.viewportBoundTop);
			//console.log(event.object.getExtent());
			//console.log(event.object.getCenter());
			
			try {
			jsf.ajax.request(GeoJSF.id, 'move', {
						render: GeoJSF.updateOnMove,
						execute: '@form',
						'javax.faces.behavior.event': 'mapMove',
						'javax.faces.partial.event': 'mapMove',
						'org.geojsf.viewport.lon': this.centerLon,  
						'org.geojsf.viewport.lat': this.centerLat,
						'org.geojsf.viewport.bottom': this.viewportBoundBottom,
						'org.geojsf.viewport.top': this.viewportBoundTop,
						'org.geojsf.viewport.left': this.viewportBoundLeft,
						'org.geojsf.viewport.right': this.viewportBoundRight});
			console.log("completed mapMove.");
			} catch(e) {
				 console.log("MapMove failed.");
			}
			finally {}
		},
		
		processEventClick : function(event)
		{
			console.log("mapClick");
			try {
			jsf.ajax.request(GeoJSF.id, 'click', {
						render: GeoJSF.updateOnClick,
						execute: '@form',
						'javax.faces.behavior.event': 'mapClick',
						'javax.faces.partial.event': 'mapClick',	
						'org.geojsf.coordinates.scale': GeoJSF.map.getScale(),
						'org.geojsf.coordinates.lat': GeoJSF.map.getLonLatFromViewPortPx(event.xy).lat,
						'org.geojsf.coordinates.lon': GeoJSF.map.getLonLatFromViewPortPx(event.xy).lon
			});
			console.log("completed mapClick.");
		} catch(e) {
			 console.log("MapClick failed.");
		}
		finally {}
		},
		
		initMap : function(mapDiv,msOptions,height, width)
		{
			GeoJSF.map = new OpenLayers.Map(mapDiv,{
				eventListeners: {
					'moveend': GeoJSF.processEventMove,
					'click':   GeoJSF.processEventClick
		        },
				controls: [new OpenLayers.Control.Navigation()], 
				version: '1.1.0', 
				request: 'GetMap',
				srs: 'EPSG:4326', 
				height: height, 
				width: width, 
				theme: null, 
				});
		//    var click = new OpenLayers.Control.Click();
		//    this.map.addControl(click);
		//    click.activate();
		    
		//    var touchUI = new OpenLayers.Control.DragPan();
		//    this.map.addControl(touchUI);
		    OpenLayers.DOTS_PER_INCH = 25.4 / 0.28;
		    //Activate for debugging
		    //this.switcher = new OpenLayers.Control.LayerSwitcher({'ascending':false});
		    //this.map.addControl(this.switcher);
		},

		addLoadEvent : function(func)
		{
			OpenLayers.Event.observe(window, "load", func ); 
		},
		
		addClickHandler : function (id, resetId, updateOnClick)
		{
			this.id = id;
			/* OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {                
		         defaultHandlerOptions: {
		             'single': true,
		             'double': false,
		             'pixelTolerance': 0,
		             'stopSingle': false,
		             'stopDouble': false
		         },

		         initialize: function(options) {
		             this.handlerOptions = OpenLayers.Util.extend(
		                 {}, this.defaultHandlerOptions
		             );
		             OpenLayers.Control.prototype.initialize.apply(
		                 this, arguments
		             ); 
		             this.handler = new OpenLayers.Handler.Click(
		                 this, {
		                     'click': this.trigger
		                 }, this.handlerOptions
		             );
		         }, 

		         trigger: function(e) {
		        	 jsf.ajax.request(id, 'click', {render: updateOnClick, execute: '@form', 'javax.faces.behavior.event': 'mapClick','javax.faces.partial.event': 'mapClick','org.geojsf.coordinates.scale': this.map.getScale(),  'org.geojsf.coordinates.lat': this.map.getLonLatFromViewPortPx(e.xy).lat, 'org.geojsf.coordinates.lon': this.map.getLonLatFromViewPortPx(e.xy).lon});
		         }
		     });*/
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
		
		switchLayer : function(serviceId, layerId, elementId)
		{
			 var checkbox = document.getElementById(elementId);
			 var active   = checkbox.checked;
			 console.log("Switching: " +layerId +" of service "+ serviceId +" to " +active);
			 // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			 // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			 
			 // This is the PrimeFaces based solution along with an 'oncomplete' call
			 PrimeFaces.ab({process: '@all', 
				 			source: elementId, 
				 			event: 'layerSwitch', 
				 			params: [{name: 'org.geojsf.switch.service', value: serviceId},{name: 'org.geojsf.switch.layer', value: layerId},{name: 'org.geojsf.switch.on', value: active}, ],
				 			oncomplete: function(xhr, status, args) {GeoJSF.performLayerSwitch(xhr, status, args);}});
		},
		
		switchLayer : function(element, layerId)
		{
			 // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			 // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			 
			console.log(element.getAttribute("id"));
			
			 // This is the PrimeFaces based solution along with an 'oncomplete' call
			 PrimeFaces.ab({process: '@all', 
				 			source: GeoJSF.id, 
				 			event: 'layerSwitch', 
				 			params: [{name: 'org.geojsf.switch.layer', value: layerId}],
				 			oncomplete: function(xhr, status, args) {GeoJSF.performLayerSwitch(xhr, status, args);}});
		},
		
		secondRun : function()
		{
			// This is the PrimeFaces based solution along with an 'oncomplete' call
			 PrimeFaces.ab({process: '@all', 
				 			source: GeoJSF.id, 
				 			event: 'updateModel', 
				 			oncomplete: function(xhr, status, args) {console.log("Update complete.");}});
		},
		
		switchLayer : function(layerId)
		{
			 // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			 // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			 
			 // This is the PrimeFaces based solution along with an 'oncomplete' call
			 PrimeFaces.ab({process: '@all', 
				 			source: GeoJSF.id, 
				 			event: 'layerSwitch', 
				 			params: [{name: 'org.geojsf.switch.layer', value: layerId}],
				 			oncomplete: function(xhr, status, args) {GeoJSF.performLayerSwitch(xhr, status, args);}});
		},
		
		refreshMap : function()
		{
			 // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			 // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			 
			 // This is the PrimeFaces based solution along with an 'oncomplete' call
			 PrimeFaces.ab({process: '@all', 
				 			source: 'source', 
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