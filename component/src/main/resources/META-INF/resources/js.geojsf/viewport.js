var GeoJsfViewport = {	

		center : function(lon,lat,resolution)
		{
			// Coordinates must be transformed from GPS to Web Mercator
			GeoJSF.map.getView().setCenter(ol.proj.transform([lat, lon], 'EPSG:4326', 'EPSG:3857'));

			// Now lets set the resolution
			GeoJSF.map.getView().setResolution(resolution);
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