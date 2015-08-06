var GeoJsfCoordinate = {	

		addCoordinatesControl : function(position, precision)
		{
                    // Please use CSS to configure appearance
                    var mousePosition = new ol.control.MousePosition({
                                           coordinateFormat: ol.coordinate.createStringXY(precision),
                                           projection      : 'EPSG:4326'
                                        });
                    GeoJSF.map.addControl(mousePosition);
		},
};