var GeoJsfUtil = {	

		closestNumber : function (num, arr) {
            var curr = arr[0];
            var diff = Math.abs (num - curr);
            for (var val = 0; val < arr.length; val++) {
                var newdiff = Math.abs (num - arr[val]);
                if (newdiff < diff) {
                    diff = newdiff;
                    curr = arr[val];
                }
            }
            console.log('Closest value is ' +curr);
            return curr;
        },
		
		getCurrentScale : function () {
			var unit			= GeoJSF.map.getView().getProjection().getUnits();
			var inchesPerMetre	= 39.37;
			var dpi				= 72;
			var resolution = ol.proj.getPointResolution(
				GeoJSF.map.getView().getProjection(),
				GeoJSF.map.getView().getResolution(),
				GeoJSF.map.getView().getCenter(),
				'm'
			  );
			for (let i = 0; i < 400; i++) 
			{
				if (matchMedia("(min-resolution: " +i +"dpi)").matches)
				{
					dpi			= i;
				}
			}
			var scale = resolution * ol.proj.Units.METERS_PER_UNIT[unit] * inchesPerMetre * dpi;
			console.log("Detected a dpi for your monitor of " +dpi +" resulting in a scale of " +scale);
			console.log("Calculated by a resolution of " +resolution + " and a unit of " +unit +" using Meters_Per_Unit for that as " +ol.proj.Units.METERS_PER_UNIT[unit]);

			// const dpi = this.dpi_ || DEFAULT_DPI;
			const inchesPerMeter = 1000 / 25.4;
			var mapScaleInternalCalc = resolution * inchesPerMeter * dpi;
			console.log("Scale according to Map is " +mapScaleInternalCalc);
			return scale;
		},

		triggerJsfAjaxEvent : function (olEventObject, jsfEventName) {
			console.log("Preparing State of Map to be send for event of Type " +jsfEventName);
			
			// Getting view information
			var centerCoordinates	   = ol.proj.transform(olEventObject.map.getView().getCenter(), 'EPSG:3857', 'EPSG:4326');
			var extent				   = olEventObject.map.getView().calculateExtent(olEventObject.map.getSize());
			this.centerLat             = centerCoordinates[1];
			this.centerLon             = centerCoordinates[0];
			this.viewportBoundTop      = ol.proj.transform(ol.extent.getTopLeft(extent), 'EPSG:3857', 'EPSG:4326')[1];
			this.viewportBoundBottom   = ol.proj.transform(ol.extent.getBottomLeft(extent), 'EPSG:3857', 'EPSG:4326')[1];
			this.viewportBoundLeft     = ol.proj.transform(ol.extent.getBottomLeft(extent), 'EPSG:3857', 'EPSG:4326')[0];
			this.viewportBoundRight    = ol.proj.transform(ol.extent.getBottomRight(extent), 'EPSG:3857', 'EPSG:4326')[0];
			
			var latlon                 = [0.0,0.0];
			if (olEventObject.coordinate)
			{
				latlon                 = ol.proj.transform(olEventObject.coordinate, 'EPSG:3857', 'EPSG:4326');
			}
			
			var distance				= ol.sphere.getDistance(ol.extent.getTopLeft(extent),ol.extent.getBottomLeft(extent)); 
			console.log("Distance from top to bottom of map is: " +distance);
			try {
				  PrimeFaces.ab({
					process:  '@form', 
					source:   GeoJSF.map.getTarget(), 
					event:    jsfEventName, 
					update:   GeoJSF.updateOnClick,
					params: [
							{name: 'org.geojsf.click.lat',						value: latlon[1]},
							{name: 'org.geojsf.click.lon',						value: latlon[0]},
							{name: 'org.geojsf.viewport.center.lon',            value: this.centerLon},
							{name: 'org.geojsf.viewport.center.lat',            value: this.centerLat},
							{name: 'org.geojsf.viewport.bottom',                value: this.viewportBoundBottom},
							{name: 'org.geojsf.viewport.top',                   value: this.viewportBoundTop},
							{name: 'org.geojsf.viewport.left',                  value: this.viewportBoundLeft},
							{name: 'org.geojsf.viewport.right',                 value: this.viewportBoundRight},	
							{name: 'org.geojsf.viewport.zoom',					value: GeoJSF.map.getView().getZoom()},
							{name: 'org.geojsf.viewport.unit',                  value: GeoJSF.map.getView().getProjection().getUnits()},
						//	{name: 'org.geojsf.viewport.scale',                 value: Math.floor( GeoJsfUtil.getCurrentScale() )}
						//	{name: 'org.geojsf.viewport.scale',                 value: Math.floor( distance )}
							{name: 'org.geojsf.viewport.scale',                 value: Math.floor( GeoJSF.map.getView().getResolution() )}
							],
					   oncomplete: function(xhr, status, args) {
							console.log('AJAX Event ' +jsfEventName + ' sent . Resolution : ' +GeoJSF.map.getView().getResolution()+ ' ' +GeoJSF.map.getView().getProjection().getUnits());
						//	clickSearcher.updateCircleOnClick(lonlat);
					   }
				  });
			  } catch(e) {
				   console.log("MapClick failed." +e);
			  }
		}
};