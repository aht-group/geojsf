var GeoJsfCoordinate = {	

		addCoordinatesControl : function(position, precision)
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

			mousePosition = new OpenLayers.Control.MousePosition({ prefix: '<nobr>', emptyString: ''});
			mousePosition.formatOutput = function(lonlat) {return [(lonlat.lat).toFixed(precision), (lonlat.lon).toFixed(precision)].join(", ");}
			GeoJSF.map.addControl(mousePosition);
			mousePosition.element.setAttribute("style", style);
			return mousePosition;
		},
};