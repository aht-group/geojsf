// Define the variable where the panZoomBar OpenLayers control will be injected
var panZoomBar;
//Define the variable where the map Div container will be injected
var map;
// Define a Namespace for the GeoJSF specific JavaScript functionality
var GeoJSF = {	

		initMap : function(mapDiv,msOptions)
		{
			map = new OpenLayers.Map(mapDiv,msOptions);
			map.addControl(new OpenLayers.Control.Navigation({'zoomWheelEnabled': false}));
		    var click = new OpenLayers.Control.Click();
		    map.addControl(click);
		    click.activate();
		    panZoomBar = new OpenLayers.Control.PanZoomBar();
		    map.addControl(panZoomBar);
		    for (var p = 0; p < 4; p++) {
		        panZoomBar.buttons[p].style.display = 'none';
		    }
		    panZoomBar.div.style.marginTop = "-50px";
		 //   var scalebar = new OpenLayers.Control.ScaleBar({align: "right"});
		 //   map.addControl(scalebar);
		 //   map.addControl(new OpenLayers.Control.LayerSwitcher());
		},

		addLoadEvent : function(func)
		{
			OpenLayers.Event.observe(window, "load", func ); 
		},
		
		addClickHandler : function (id, resetId, inputDOMelement, mapDOMelement, clientId)
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
		        	 inputDOMelement.val(map.getLonLatFromViewPortPx(e.xy));
		        //	 alert(inputDOMelement.val());
		        	 inputDOMelement.change();
		        	 var evObj = document.createEvent('MouseEvents');
		        	 evObj.lonlat = map.getLonLatFromViewPortPx(e.xy);
		        	 evObj.initMouseEvent("mapClick", true, true, window,
		        			    0, 0, 0, 0, 0, false, false, false, false, 0, null);
		        //	 alert('mapDOMelement' +mapDOMelement.text() +' id ' +mapDOMelement.attr('id'));
		        //	 firefoxElement = document.getElementById(mapDOMelement.attr('id')); 
		        //	 firefoxElement.dispatchEvent(evObj);
		        //	 firefoxElement.click();
		        //	 var e = jQuery.Event("click", { lonlat: evObj.lonlat });
		        //	 jQuery(id).trigger(e);
		        //	 jsf.ajax.request(this, "mapClick",{execute:"@form",render: resetId,params: evObj.lonlat});
		         }
		     });
		},
		
		// Remove all layers and add the base layer again
		resetLayers : function()
		{
			if (map.layers)
				{
					for (var i = map.layers.length - 1; i >= 0; i--) {
					    map.removeLayer(map.layers[i]);
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
		//	alert("Adding layer: " +name +","+url+","+params);
			map.addLayer(layer);
			map.zoomToMaxExtent();
			for (var p = 0; p < 4; p++)
			{
				panZoomBar.buttons[p].style.display = 'none';
			}
		},
		
		center : function(lon,lat,zoom)
		{
			map.setCenter(new OpenLayers.LonLat(lon, lat), zoom);
		},
};