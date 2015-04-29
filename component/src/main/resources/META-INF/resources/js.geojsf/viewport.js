var GeoJsfViewport = {	

		center : function(lon,lat,zoom)
		{
			GeoJSF.map.setCenter(new OpenLayers.LonLat(lon, lat), zoom, true, true);
		},
};