var GeoJSF = {	
		map: null,
		switcher: null,
		commandCorrect: true,
		
		bootstrap : function()
		{
			var head = document.getElementsByTagName("head")[0];
		    var s = document.createElement("script");
		    s.type = "text/javascript";
		    s.src = "http://openlayers.org/api/OpenLayers.js";
		    head.appendChild(s);
		},
		
		initMap : function(mapDiv,msOptions,height, width)
		{
			this.map = new OpenLayers.Map(mapDiv,{controls: [],bbox: '2.0,4.19999980926514,25.3300018310547,21.5', version: '1.1.0', request: 'GetMap',srs: 'EPSG:4326', height: height, width: width, theme: null});
		    var click = new OpenLayers.Control.Click();
		    this.map.addControl(click);
		    click.activate();
		    this.switcher = new OpenLayers.Control.LayerSwitcher({'ascending':false});
		    var touchUI = new OpenLayers.Control.TouchNavigation();
		    this.map.addControl(touchUI);
		    OpenLayers.DOTS_PER_INCH = 25.4 / 0.28;
		    //Activate for debugging
		    //this.map.addControl(this.switcher);
		},

		addLoadEvent : function(func)
		{
			OpenLayers.Event.observe(window, "load", func ); 
		},
		
		addClickHandler : function (id, resetId, updateOnClick)
		{
			 OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {                
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
		
		// Deprecated - Logic will be placed in server bean soon
		toggleComplexLayer : function(layerCode, shown) 
		{
			var layerArray = layerCode.split(":");
			var service    = layerArray[0];
			var layer      = layerArray[1];
			console.log('Trying to set ' +layer +' of ' + service +' to ' +shown);
		    var currentService = GeoJSF.map.getLayersByName(service);
		    console.log('Getting Service: ' +currentService);
		    console.log('Getting Service Params: ' +currentService.params);
		    console.log('Getting Service LAYERS: ' +currentService.params.LAYERS);
		    console.log('Getting Layers: ' +currentLayer);
		    
		    if (!shown)
		    {
		    	if (currentLayer.length==1)
		    	{
		    		currentService.setVisibility(shown);
		    	}
		    	else
		    	{
		    		var index = currentLayer.indexOf(layer);
		    		if (index > -1) {
		    		    currentLayer.splice(index, 1);
		    		}
		    		removeLayer(service);
		    		addLayer(currentService.name, currentService.url, currentLayer, currentService.params);
		    	}
		    }
		    if (shown)
		    {
		    	if (currentLayer.length==1)
		    		{
		    			currentService.setVisibility(shown);
		    		}
		    	else
		    		{
		    			var index = currentLayer.indexOf(layer);
		    			if (index > -1) {
		    				currentLayer.splice(index, 1);
		    			}
		    			removeLayer(service);
		    			addLayer(currentService.name, currentService.url, currentLayer, currentService.params);
		    		}
		    }
		},
		
		// Deprecated
		toggleLayer : function(layerName, shown) 
		{
		    var layers = GeoJSF.map.getLayersByName(layerName);
		    if(layers.length === 1) {
		    	layers[0].setVisibility(shown);
		    }
		    else {
		        console.log('no layer with name ' +layerName +' found!');
		    }
		},
		
		// Deprecated
		setLayers : function(checkboxIds, numberOfOptions) 
		{
			for (var i=0;i<numberOfOptions;i++)
			{
				var c = document.getElementById(checkboxIds +i);
				console.log('Setting ' +c.value + ' to ' +c.checked);
			//	this.toggleComplexLayer(c.value, c.checked);
			}
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
		
		switchLayer : function(layerId, element)
		{
			 // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			 // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			 
			 // This is the PrimeFaces based solution along with an 'oncomplete' call
			 PrimeFaces.ab({process: '@all', 
				 			source: element, 
				 			event: 'layerSwitch', 
				 			params: [{name: 'org.geojsf.switch.layer', value: layerId}],
				 			oncomplete: function(xhr, status, args) {GeoJSF.performLayerSwitch(xhr, status, args);}});
		},
		
		switchLayer : function(layerId)
		{
			 // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			 // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			 
			 // This is the PrimeFaces based solution along with an 'oncomplete' call
			 PrimeFaces.ab({process: '@all', 
				 			source: 'source', 
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
					var layers = GeoJSF.map.getLayersByName(command.serviceId);
				    if(layers.length === 1) {
				    	layers[0].setVisibility(false);
				    } else {console.log('big problem');}
				}
			if (command.command == "show")
				{
					var layers = GeoJSF.map.getLayersByName(command.serviceId);
				    if(layers.length === 1) {
				    	layers[0].setVisibility(true);
				    } else {console.log('big problem');}
				}
		    if (command.command == "merge")
				{
			    	var layers = GeoJSF.map.getLayersByName(command.serviceId);
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
		}
};