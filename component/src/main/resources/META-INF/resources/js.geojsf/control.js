var GeoJsfControl = {	

		addZoom : function(type)
		{
			var zoom;
			if (type=="button")
			{
				this.zoom = new OpenLayers.Control.Zoom();
				GeoJSF.map.addControl(this.zoom);
			}
			if (type=="slider")
			{
				this.zoom = new OpenLayers.Control.PanZoomBar();
				GeoJSF.map.addControl(this.zoom);
				 for (var p = 0; p < 4; p++) {
				        this.zoom.buttons[p].style.display = 'none';
				 }
			}
		},
};