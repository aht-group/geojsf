// Include all needed other modules

import {GeoJSF} from './geo';
import * as ol from 'ol';
import * as olProj from 'ol/proj';
import * as olUnits from 'ol/proj/Units';
import * as olSource from 'ol/source';
import * as olExtent from 'ol/extent';
import Layer from 'ol/layer/Layer';
import * as olSphere from 'ol/sphere';
import TileLayer from 'ol/layer/Tile';

// Include the types defined in PrimeFaces Node Types dependency
/// <reference types="primefaces" />

export var GeoJsfUtil = {	

		closestNumber : function (num : number, arr : Array<number>) {
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
			var unit			= (window as any).GeoJSF.map.getView().getProjection().getUnits();
			var inchesPerMetre	= 39.37;
			var dpi				= 72;
			var resolution = olProj.getPointResolution(
				(window as any).GeoJSF.map.getView().getProjection(),
				(window as any).GeoJSF.map.getView().getResolution(),
				(window as any).GeoJSF.map.getView().getCenter(),
				'm'
			  );
			for (let i = 0; i < 400; i++) 
			{
				if (matchMedia("(min-resolution: " +i +"dpi)").matches)
				{
					dpi			= i;
				}
			}
			var scale = resolution * olUnits.METERS_PER_UNIT['m'] * inchesPerMetre * dpi;
			console.log("Detected a dpi for your monitor of " +dpi +" resulting in a scale of " +scale);
			console.log("Calculated by a resolution of " +resolution + " and a unit of " +unit +" using Meters_Per_Unit for that as " +olUnits.METERS_PER_UNIT['m']);

			  // const dpi = this.dpi_ || DEFAULT_DPI;
			  const inchesPerMeter = 1000 / 25.4;
			  var mapScaleInternalCalc = resolution * inchesPerMeter * dpi;
			console.log("Scale according to Map is " +mapScaleInternalCalc);
			return scale;
		},

		triggerJsfAjaxEvent : function (olEventObject : ol.MapBrowserEvent<MouseEvent>, jsfEventName : string) {
			console.log("Preparing State of Map to be send for event of Type " +jsfEventName);
			
			// Getting view information
			var centerCoordinates	   = olProj.transform(olEventObject.map.getView().getCenter(), 'EPSG:3857', 'EPSG:4326');
			var extent				   = olEventObject.map.getView().calculateExtent(olEventObject.map.getSize());
			this.centerLat             = centerCoordinates[1];
			this.centerLon             = centerCoordinates[0];
			this.viewportBoundTop      = olProj.transform(olExtent.getTopLeft(extent), 'EPSG:3857', 'EPSG:4326')[1];
			this.viewportBoundBottom   = olProj.transform(olExtent.getBottomLeft(extent), 'EPSG:3857', 'EPSG:4326')[1];
			this.viewportBoundLeft     = olProj.transform(olExtent.getBottomLeft(extent), 'EPSG:3857', 'EPSG:4326')[0];
			this.viewportBoundRight    = olProj.transform(olExtent.getBottomRight(extent), 'EPSG:3857', 'EPSG:4326')[0];
			var latlon                 = [0.0,0.0];
			if (olEventObject.coordinate)
			{
				var lonlat                 = olProj.transform(olEventObject.coordinate, 'EPSG:3857', 'EPSG:4326');
			}
			var distance				= olSphere.getDistance(olExtent.getTopLeft(extent),olExtent.getBottomLeft(extent)); 
			console.log("Distance from top to bottom of map is: " +distance);
				// var options : PrimeFaces.ajax.Configuration = {};
				var options : PrimeFaces.ajax.Configuration = {
					async: false,
					delay: 0,
					formId: '',
					event: '',
					ext: undefined,
					fragmentId: '',
					global: false,
					ignoreAutoUpdate: false,
					oncomplete: function (xhrOrErrorThrown, status, pfArgs, dataOrXhr) {
						console.log(pfArgs);
					},
					onerror: function (xhr, status, errorThrown) {
						console.log(status);
						console.log(errorThrown);
					},
					onstart: function (cfg) {
						console.log(cfg);
						return true;
					},
					onsuccess: function (data, status, xhr) {
						console.log(data);
						return true;
					},
					params: [],
					partialSubmit: false,
					partialSubmitFilter: '',
					process: '',
					promise: undefined,
					resetValues: false,
					skipChildren: false,
					source: '',
					timeout: 0,
					update: ''
				};
				options.source = (window as any).GeoJSF.map.getTarget();
				options.event  = jsfEventName;
				options.update = (window as any).GeoJSF.updateOnClick;
				options.process = "@form";
				options.params = [
					{name: 'org.geojsf.click.lat',						value: latlon[1]},
					{name: 'org.geojsf.click.lon',						value: latlon[0]},
					{name: 'org.geojsf.viewport.center.lon',            value: this.centerLon},
					{name: 'org.geojsf.viewport.center.lat',            value: this.centerLat},
					{name: 'org.geojsf.viewport.bottom',                value: this.viewportBoundBottom},
					{name: 'org.geojsf.viewport.top',                   value: this.viewportBoundTop},
					{name: 'org.geojsf.viewport.left',                  value: this.viewportBoundLeft},
					{name: 'org.geojsf.viewport.right',                 value: this.viewportBoundRight},	
					{name: 'org.geojsf.viewport.zoom',					value: (window as any).GeoJSF.map.getView().getZoom()},
					{name: 'org.geojsf.viewport.unit',                  value: (window as any).GeoJSF.map.getView().getProjection().getUnits()},
					{name: 'org.geojsf.viewport.scale',                 value: Math.floor( (window as any).GeoJSF.map.getView().getResolution() )}
					];
				//options.oncomplete = function(xhr : any, status : any, args : any) 
				//{
					//console.log('markerMovev AJAX request sent. Resolution : ' +(window as any).GeoJSF.map.getView().getResolution()+ ' ' +(window as any).GeoJSF.map.getView().getProjection().getUnits())
				//	console.log(args);
					// if (jsfEventName === "mapClick") {(window as any).clickSearcher.updateCircleOnClick(olEventObject);}
				//};
				console.log(options);
				PrimeFaces.ab(
				  options
				);
			
		},

		updateLayerParams : function(key : string, value: any)
		{
			console.log("SLD Update requested.");
			var params : any = {};
				params[key] = value;
			if ((window as any).GeoJSF.map.getLayers())
			{
				(window as any).GeoJSF.map.getLayers().forEach(function (layer : Layer) {
					if (layer.getSource() instanceof olSource.OSM)
					{
						console.log("Ignoring OSM base layer"); 
					}
					else
					{
						(layer.getSource() as olSource.TileWMS).updateParams(params);
						console.log("Merging new SLD parameter: " +params.SLD);
					}
				});
			}	
		},
};