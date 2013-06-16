var GeoJsfCoordinate = {	

		addCoordinatesControl : function(position)
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

			mousePosition = new OpenLayers.Control.MousePosition({emptyString: ''});
			GeoJSF.map.addControl(mousePosition);
			mousePosition.element.setAttribute("style", style);
			return mousePosition;
		},
};