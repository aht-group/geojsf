 var map;
 var panZoomBar;
 
function __addLoadEvent(func) {
    Event.observe(window, "load", func ); 
}

function __addClickHandler() {
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
             var lonlat = map.getLonLatFromViewPortPx(e.xy);
             geoJsfClick(lonlat.lon,lonlat.lat,map.getResolution(),map.getScale());
         }

     });
}
 
function __initMapDiv(mapDiv,msOptions) {
	OpenLayers.ImgPath='resources/geojsf/images/';
    map = new OpenLayers.Map(mapDiv,msOptions);
    map.addControl(new OpenLayers.Control.Navigation({'zoomWheelEnabled': false}));
    var click = new OpenLayers.Control.Click();
    map.addControl(click);
    click.activate();
}

function __initMapLayers(msName,msURL,centerX,centerY,msZoom,layerOptions){
    var ol_wms = new OpenLayers.Layer.WMS(msName,msURL,layerOptions);
    map.addLayers([ol_wms]);
    if(centerX == 0 & centerY == 0)
    {
        map.zoomToMaxExtent();
    }
    else
    {
        map.setCenter(new OpenLayers.LonLat(centerX,centerY),msZoom);
    }   
    panZoomBar = new OpenLayers.Control.PanZoomBar();
    map.addControl(panZoomBar);
    for (var p = 0; p < 4; p++) {
        panZoomBar.buttons[p].style.display = 'none';
    }
    panZoomBar.div.style.marginTop = "-50px";
    var scalebar = new OpenLayers.Control.ScaleBar({align: "right"});
    map.addControl(scalebar);
}

function geoJsfReloadLayer(data)
{ 
	removeAllLayers();
	var layer = new OpenLayers.Layer.WMS('GeoJSF Layer Switcher',data.url, data.params, {buffer: 1, isBaseLayer: true} );
	map.addLayer(layer);
	 for (var p = 0; p < 4; p++) {
	        panZoomBar.buttons[p].style.display = 'none';
	    }
}

function removeAllLayers()
{
	for (var i = map.layers.length - 1; i >= 0; i--) {
	    map.removeLayer(map.layers[i]);
	}
}


 