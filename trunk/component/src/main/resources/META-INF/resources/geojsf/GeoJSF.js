var GeoJSF = {	
		map: null,
		panZoomBar: null,
		
		initMap : function(mapDiv,msOptions)
		{
			this.map = new OpenLayers.Map(mapDiv,msOptions);
		    var click = new OpenLayers.Control.Click();
		    this.map.addControl(click);
		    click.activate();
		    this.panZoomBar = new OpenLayers.Control.PanZoomBar();
		    this.map.addControl(this.panZoomBar);
		    this.panZoomBar.div.style.marginTop = "-50px";
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
		
		// Add a MousePosition control that displays the coordinates of the current mouse position
		addCoordinatesControl : function(position)
		{
			// Apply given style
			var style = "z-index: 1001; margin: 2px; ";
			if (position=="northeast")
			 {
				style = style +"right: 0px; left: 80%; top: 0px;"
			 }
			if (position=="northwest")
			 {
			 	style = style +"left: 0px; right: 80%; top: 0px;"
			 }
			if (position=="southeast")
			 {
			 	style = style +"right: 0px; left: 80%; bottom: 0px;"
			 }
			if (position=="southwest")
			 {
			 	style = style +"left: 0px; right: 80%; bottom: 0px;"
			 }
		
			
			
			mousePosition = new OpenLayers.Control.MousePosition({
				emptyString: ''
				});
			this.map.addControl(mousePosition);
			mousePosition.element.setAttribute("style", style);
			return mousePosition;
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
		
		center : function(lon,lat,zoom)
		{
			this.map.setCenter(new OpenLayers.LonLat(lon, lat), zoom);
		},
};