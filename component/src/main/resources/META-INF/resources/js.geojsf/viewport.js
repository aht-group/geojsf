var GeoJsfViewport = {	

		center : function(lon,lat,zoom,isScale)
		{
			if (isScale)
				{
					GeoJSF.map.setCenter(new OpenLayers.LonLat(lon, lat));
					GeoJSF.map.zoomToScale(zoom);
					console.log("Centered and zoomed to scale of " +zoom);
				}
			else
				{
					GeoJSF.map.setCenter(new OpenLayers.LonLat(lon, lat), zoom, true, true);
					console.log("Centered and zoomed to zoom level of " +zoom);
				}
		},
};