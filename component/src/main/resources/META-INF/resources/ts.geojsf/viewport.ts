import {GeoJSF} from './geo';
import * as olProj from 'ol/proj';

export var GeoJsfViewport = {	

		center : function(lon : number,lat : number ,resolution : number,zoom : number)
		{
			console.log("GeoJsfViewport:center Rendering ViewPort at " +lon + " / " +lat);
			// Coordinates must be transformed from GPS to Web Mercator
			
			//GeoJSF.map.getView().setCenter(olProj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857'));
			//changed sm for the above line to the below line
			var centerPoint = new olGeom.Point(olProj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857'));
			GeoJSF.map.getView().setCenter(centerPoint.getCoordinates());

			if (resolution) {console.log("Rendering Resolution of " +resolution); GeoJSF.map.getView().setResolution(resolution);}
			// Set the resolution if given
			// Alternatively set the zoom if given
			if (zoom) {console.log("Rendering with zoom factor " +zoom); GeoJSF.map.getView().setZoom(zoom);}
			//if (zoom) {console.log("Rendering with zoom factor " +zoom); GeoJSF.map.setZoom(zoom);}
		},
		
		reset : function()
		{
			// Resets the map to a standard viewport. To be used in Debugging via console.
			// Coordinates must be transformed from GPS to Web Mercator
			console.log("Reseting ViewPort");
			GeoJSF.map.getView().setCenter(olProj.transform([0.0, 0.0], 'EPSG:4326', 'EPSG:3857'));
			GeoJSF.map.getView().setResolution(100000);
		}
}