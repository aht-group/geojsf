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
			this.map = new OpenLayers.Map(mapDiv,{controls: []});
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
		
		addClickHandler : function (id, resetId)
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
		        	 jsf.ajax.request(id, 'click', {execute: '@form', 'javax.faces.behavior.event': 'mapClick','javax.faces.partial.event': 'mapClick', 'org.geojsf.coordinates': this.map.getLonLatFromViewPortPx(e.xy)});
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
};