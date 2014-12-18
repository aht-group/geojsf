var GeoJsfControl = {	
		
		scalebar: null,

		addZoom : function(type, position)
		{
			var zoom;
			var style;
			if (position=="right")
			{
				style = "92%;";
			}
			else
			{
				style = "5%;";
			}
			if (type=="button")
			{
				this.zoom = new OpenLayers.Control.Zoom();
				GeoJSF.map.addControl(this.zoom);
				var originalStyle = this.zoom.draw.parentNode.getAttribute("style");
				this.zoom.draw.setAttribute("style", originalStyle +" left: " +style +";");
			}
			if (type=="slider")
			{
				this.zoom = new OpenLayers.Control.PanZoomBar();
				GeoJSF.map.addControl(this.zoom);
				 for (var p = 0; p < 4; p++) {
					    var originalStyleP = this.zoom.buttons[p].firstChild.getAttribute("style");
					    console.log("Original Style: " +originalStyleP);
				        this.zoom.buttons[p].firstChild.setAttribute("style", originalStyleP +" display: none !important;");
				        console.log("new Style: " +this.zoom.buttons[p].getAttribute("style"));
				 }
				 for (var p = 4; p < 6; p++) {
					    var originalStyleP = this.zoom.buttons[p].getAttribute("style");
					    console.log("Original Style: " +originalStyleP);
				        this.zoom.buttons[p].setAttribute("style", originalStyleP +" left: " +style +";");
				        console.log("new Style: " +this.zoom.buttons[p].getAttribute("style"));
				 }
				 var originalStyleSlider = this.zoom.slider.getAttribute("style");
				 this.zoom.slider.setAttribute("style", originalStyleSlider +" left: " +style +";");
				 var originalStyle = this.zoom.zoombarDiv.getAttribute("style");
				 console.log("Original Style: " +originalStyle);
				 this.zoom.zoombarDiv.setAttribute("style", originalStyle +" left: " +style +";");
				 console.log("new Style: " +this.zoom.zoombarDiv.getAttribute("style"));
			}
		},
		
		addScale : function(position, system, major, sub)
		{
			// Apply given style
		//	var style = "z-index: 1001; margin: 20px; ";
			var style = "margin: 30px; ";
			if (position=="northeast")
			 {
				style = style +"right: 0px; left: 70%; top: 0px;"
			 }
			if (position=="northwest")
			 {
			 	style = style +"left: 0px; right: 70%; top: 0px;"
			 }
			if (position=="southeast")
			 {
			 	style = style +"right: 0px; left: 70%; bottom: 0px;"
			 }
			if (position=="southwest")
			 {
			 	style = style +"left: 0px; right: 70%; bottom: 0px;"
			 }

			scaleBar = new OpenLayers.Control.ScaleBar({
				displaySystem: system,
				divisions: major,
			    subdivisions: sub
			});
		//		scaleBar = new OpenLayers.Control.ScaleLine();
			GeoJSF.map.addControl(scaleBar);
		//	scaleBar.eTop.parentNode.setAttribute("style", style);
		//	scaleBar.eBottom.setAttribute("style", "display: none;");
			var originalStyle = scaleBar.element.parentNode.getAttribute("style");
			scaleBar.element.setAttribute("style", originalStyle +style);
			return scaleBar;
		},
		
		addScaleBar : function(position, system, major, sub)
		{
			// Apply given style
		//	var style = "z-index: 1001; margin: 20px; ";
			var style = "margin: 30px; ";
			if (position=="northeast")
			 {
				style = style +"right: 0px; left: 70%; top: 0px;"
			 }
			if (position=="northwest")
			 {
			 	style = style +"left: 0px; right: 70%; top: 0px;"
			 }
			if (position=="southeast")
			 {
			 	style = style +"right: 0px; left: 70%; bottom: 0px;"
			 }
			if (position=="southwest")
			 {
			 	style = style +"left: 0px; right: 70%; bottom: 0px;"
			 }

			scaleBar = new OpenLayers.Control.ScaleBar({
				displaySystem: system,
				divisions: major,
			    subdivisions: sub
			});
				this.scaleBar = new OpenLayers.Control.ScaleLine();
			GeoJSF.map.addControl(this.scaleBar);
			return this.scaleBar;
		},
		
		removeScaleBar : function()
		{
			GeoJSF.map.removeControl(GeoJsfControl.scaleBar);
		},
		
		addTimeManager : function()
		{
			timeManager = new OpenLayers.Control.TimeManager({
				units:OpenLayers.TimeUnit.HOURS,
				step:6,
				frameRate:0.85
			});
			GeoJSF.map.addControl(timeManager);
		}
};