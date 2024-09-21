var GeoJsfCoordinate = {
	
	myFormat : function(dgts)
	{
	  return (
		function(coord1) {
			var coord2 = [coord1[1], coord1[0]]; 
		  return olCoordinate.toStringXY(coord2,dgts);
	  });        
	},

	addCoordinatesControl : function(position, precision)
	{
				// Please use CSS to configure appearance
				var mousePosition = new olControl.MousePosition({
									   coordinateFormat:	GeoJsfCoordinate.myFormat(2),
									   projection      : 'EPSG:4326'
									});
				GeoJSF.map.addControl(mousePosition);
	},
};