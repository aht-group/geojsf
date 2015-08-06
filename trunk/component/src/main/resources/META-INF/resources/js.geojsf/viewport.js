var GeoJsfViewport = {	

		center : function(lon,lat,zoom,isScale)
		{
                    // Coordinates must be transformed from GPS to Web Mercator
                    GeoJSF.map.getView().setCenter(ol.proj.transform([lat, lon], 'EPSG:4326', 'EPSG:3857'));
                   
                    if (isScale) {
                        GeoJSF.map.getView().setResolution(zoom);
                    }
                    else
                    {
                        GeoJSF.map.getView().setZoom(zoom);
                    }
                }
}