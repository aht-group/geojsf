var GeoJsfViewport = {	

		center : function(lon,lat,resolution,zoom)
		{
			// Coordinates must be transformed from GPS to Web Mercator
			GeoJSF.map.getView().setCenter(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857'));
			
			// Set the resolution if given
			if (resolution) {console.log("Rendering Resolution of " +resolution); GeoJSF.map.getView().setResolution(resolution);}
			
			// Alternatively set the zoom if given
			if (zoom) {console.log("Rendering with zoom factor " +zoom); GeoJSF.map.getView().setZoom(zoom);}
		},
		
		reset : function()
		{
			// Resets the map to a standard viewport. To be used in Debugging via console.
			// Coordinates must be transformed from GPS to Web Mercator
			console.log("Reseting ViewPort");
			GeoJSF.map.getView().setCenter(ol.proj.transform([0.0, 0.0], 'EPSG:4326', 'EPSG:3857'));
			GeoJSF.map.getView().setResolution(100000);
		}
}