var GeoJSF = {	
		map: null,
		switcher: null,
		
		bootstrap : function()
		{
			var head = document.getElementsByTagName("head")[0];
		    var s = document.createElement("script");
		    s.type = "text/javascript";
		    s.src = "http://openlayers.org/api/OpenLayers.js";
		    head.appendChild(s);
		},
		
		initMap : function(mapDiv,msOptions)
		{
			this.map = new OpenLayers.Map(mapDiv,{controls: [], theme: null});
		    var click = new OpenLayers.Control.Click();
		    this.map.addControl(click);
		    click.activate();
		    this.switcher = new OpenLayers.Control.LayerSwitcher({'ascending':false});
		    var touchUI = new OpenLayers.Control.TouchNavigation();
		    this.map.addControl(touchUI);
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
		
		toggleService : function(serviceId, layer)
		{
			console.log("Setting " + serviceId + " to active with " +layer);
		},
		
		testAjaxData : function(xhr, status, args)
		{
			//The args attribute is filled by the server using PrimeFaces ResponseWriter method
			var activeLayers = JSON.parse( args.activeLayers );
	//		console.log(activeLayers);
			for(var service in activeLayers)
			{
				var serviceObj = activeLayers[service];
		        this.toggleService(serviceObj.serviceId, serviceObj.layer);
		    }
		},
		
		testAjaxButtonData : function(xhr, status, args)
		{
			//The args attribute is filled by the server using PrimeFaces ResponseWriter method
			var activeLayers = JSON.parse( args.activeLayers );
	//		console.log(activeLayers);
			for(var service in activeLayers)
			{
				var serviceObj = activeLayers[service];
		        this.toggleService(serviceObj.serviceId, serviceObj.layer);
		    }
		},
		
		updateTime : function(layerName, time)
		{
			 var layer = this.map.getLayersByName(layerName);
			 var params  = {};
			 var date  = new Date(time);
			 var isoTime = date.toISOString();
			 params.time = isoTime;
			 console.log("Merging new Time parameter: " +isoTime);
			 this.layer.mergeNewParams(params);
		}
		
		
};