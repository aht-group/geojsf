/// <reference types="primefaces" />
// <reference path="./node_modules/primefaces/PrimeFaces.d.ts" />
import * as ol from 'ol';
import * as olSource from 'ol/source';
import * as olStyle from 'ol/style';
import * as olProj from 'ol/proj';
import * as olLayer from 'ol/layer';
import * as olControl from 'ol/control';
import * as olExtent from 'ol/extent';
import * as olGeom from 'ol/geom';
import * as olSphere from 'ol/sphere';
import * as olInteraction from 'ol/interaction';
import * as pf from 'primefaces/PrimeFaces-module';

import Feature from 'ol/Feature';
import GeoJSON from 'ol/format/GeoJSON';
import Point from 'ol/geom/Point';
import Layer from 'ol/layer/Layer';
import VectorLayer from 'ol/layer/Vector';
import TileLayer from 'ol/layer/Tile';
import Chart from 'ol-ext/style/Chart';
import AnimatedCluster, { ClusterOptions } from 'ol-ext/layer/AnimatedCluster';
import type { Options } from 'ol/layer/BaseVector'
import { FitOptions } from 'ol/View';
import VectorSource from 'ol/source/Vector';

import {GeoJsfUtil} from './geo-util';

(window as any).GeoJsfUtil = GeoJsfUtil;
(window as any).GeoJSF = {		
	_map: new ol.Map(),
	get map() : ol.Map {
		return this._map;
	},
	set map(value : ol.Map) {
		this._map = value;
	},

	_feature: (ol.Feature<any>),
	get feature() : ol.Feature {
		return this._feature;
	},
	set feature(value : ol.Feature) {
		this._feature = value;
	},

	_vectorSource: (olSource.Vector<any>),
	get vectorSource() : olSource.Vector<any>{
		return this._vectorSource;
	},
	set vectorSource(value : olSource.Vector<any>) {
		this._vectorSource = value;
	},

	iconStyle: new olStyle.Style({
		image: new olStyle.Icon({
			anchor: [0.5, 0.75],
			anchorXUnits: 'fraction',
			anchorYUnits: 'fraction',
			opacity: 1,
			scale: 0.6,
			src: 'http://localhost:8080/geojsf/javax.faces.resource/marker.png.jsf?ln=geojsf'
		})
	}),

		  
	commandCorrect: true,
	updateOnClick: "",
	updateOnMove: "",
	updateOnPopUp: "",
	toolTipFeature: "",
	popUpOverlay: "",
	lastLayer: "",
	id : "",
	baseLayer: "",
	markerUrl: "",
	_features: Array<ol.Feature>,
	get features() : Array<ol.Feature>{
		return this._features;
	},
	set features(value :  Array<ol.Feature>) {
		this._features = value;
	},
	_featureLayer: new olLayer.Vector(),
	get featureLayer() : olLayer.Vector<any> {
		return this.olLayer.Vector_featureLayer;
	},
	set featureLayer(value : olLayer.Vector<any>) {
		this._featureLayer = value;
	},
	_markerPosition: new olGeom.Point(olProj.transform([0, 0], 'EPSG:4326', 'EPSG:3857')),
	get markerPosition() : Point {
		return this._markerPosition;
	},
	set markerPosition(value : Point) {
		this._markerPosition = value;
	},


	scaleValues: new Array<number>,
		  
	setAjaxUpdates : function(updateClicks : string, updateMove :string)
	{
		this.updateOnClick = updateClicks;
		this.updateOnMove  = updateMove;
	},
	
	setAjaxUpdatesPopUp : function(updatePopUp : string)
	{
		this.updateOnPopUp = updatePopUp;
	},
	
	setScaleValues : function(values : any)
	{
		GeoJSF.scaleValues = values;
		console.log('Allowed scale values are: ' +this.scaleValues);
	},
	
	getCurrentLocation : function()
	{
		navigator.geolocation.getCurrentPosition(GeoJSF.processCurrentLocation);
	},
	
	processCurrentLocation : function(location : any)
	{
		console.log("You are at: (lat/lon) (" +location.coords.latitude +"/" +location.coords.longitude +") with an accurancy of " +location.coords.accuracy);
	},
	
	registerEventHandlers : function()
	{
		
		try 
		{
			console.log("Trying to register event handlers");
			this.map.on("singleclick",  this.processEventClick);
			this.map.on("moveend"    ,  this.processEventMove);
			console.log("Done registering.");
		}
		catch (e) {alert("Problem with registering Map events: " +e)}
	},
	
	processEventMove : function(event : ol.MapBrowserEvent<MouseEvent>)
	{
		console.log("mapMove detected");
		GeoJsfUtil.triggerJsfAjaxEvent(event, "mapMove");
	},
		  
	processEventClick : function(event : ol.MapBrowserEvent<MouseEvent>)
	{
		console.log("mapClick detected");
		GeoJsfUtil.triggerJsfAjaxEvent(event, "mapClick");
	},

	/*
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
			var options : PrimeFaces.ajax.Configuration = {
				async: false,
				delay: 0,
				formId: '',
				event: '',
				ext: undefined,
				fragmentId: '',
				global: false,
				ignoreAutoUpdate: false,
				oncomplete: function (this: JQuery.AjaxSettings<any>, xhrOrErrorThrown: unknown, status: JQuery.Ajax.TextStatus, pfArgs: PrimeFaces.ajax.PrimeFacesArgs, dataOrXhr: XMLDocument | PrimeFaces.ajax.pfXHR<PrimeFaces.ajax.PrimeFacesArgs>): void {
					throw new Error('Function not implemented.');
				},
				onerror: function (this: JQuery.AjaxSettings<any>, xhr: PrimeFaces.ajax.pfXHR<PrimeFaces.ajax.PrimeFacesArgs>, status: JQuery.Ajax.ErrorTextStatus, errorThrown: string): void {
					throw new Error('Function not implemented.');
				},
				onstart: function (this: PrimeFaces.ajax.Request, cfg: PrimeFaces.ajax.Configuration): boolean {
					throw new Error('Function not implemented.');
				},
				onsuccess: function (this: JQuery.AjaxSettings<any>, data: XMLDocument, status: JQuery.Ajax.SuccessTextStatus, xhr: PrimeFaces.ajax.pfXHR<PrimeFaces.ajax.PrimeFacesArgs>): boolean {
					throw new Error('Function not implemented.');
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
	*/

	processEventPointerMove : function(event : ol.MapBrowserEvent<MouseEvent>)
	{
		/*
			// console.log("processEventPointerMove");
			//  console.log(event);
				  
					  
			 if (GeoJSF.toolTipFeature !== null) {
			    GeoJSF.toolTipFeature = null;
			  }
			
			  GeoJSF.map.forEachFeatureAtPixel(event.pixel, function (f : Feature) {
			    GeoJSF.toolTipFeature = f;
			    return true;
			  });
			
			  if (GeoJSF.toolTipFeature) 
			  {
				olControl.PopUp.popUpContent.innerHTML = GeoJSF.toolTipFeature.get('ttinfo');
		    	GeoJSF.popUpOverlay.setPosition(event.coordinate);
			  }
			  else
			  {
				GeoJSF.popUpOverlay.setPosition(undefined);
				ol.control.PopUp.popUpCloser.blur();
			  } 
		*/
	},	 

	processEventSelectFeature : function(featureId : string, type : string)
	{
		console.log("Select Feature detected id: " + featureId);
		/*
		try {
			PrimeFaces.ab({
				process:  '@form', 
				source:   GeoJSF.map.getTarget(), 
				event:    'featureSelected', 
				update:   GeoJSF.updateOnPopUp,
				params: [
						{name: 'org.geojsf.click.feature.id',				value: featureId},
						{name: 'org.geojsf.click.feature.type',				value: type},
						],
				oncomplete: function(xhr, status, args) {
					// ol.control.PopUp.popUpContent.innerHTML = 'This should be coming from args of backend';
					ol.control.PopUp.popUpContent.innerHTML = args.popupText;
					console.log(args);
				}
			});
		} catch(e) {
			console.log("featureSelected failed." +e);
		}
			finally {}
		*/
	},

	addPointClusterLayer : function(name : string, url : string, clusterColor : string)
	{
		var viewOfMap = this.map.getView();
		
		// Style for the clusters
		const style = new olStyle.Style({
						fill: new olStyle.Fill({
						color: '#eeeeee',
						}),
					});
		var styleCache : any = {};

		// Cluster Source GeoJSON layer
		// var options : Options<Geometry> = {};
		var clusterSource=new olSource.Cluster({
			distance: 40,
			source: new olSource.Vector({
				url: url,
			//	projection: viewOfMap.getProjection().getCode(),
				format: new GeoJSON(),
			})
		});
	

		// Animated cluster layer
		//var animatedClusterOptions : ClusterOptions = {};
		var animatedClusterOptions = {} as any;
		animatedClusterOptions.animationDuration = 0;
		animatedClusterOptions.source = clusterSource;
		animatedClusterOptions.style = function(feature : Feature, resolution : number)
					{
						var size = feature.get('features').length;
						var style = styleCache[size];
						if (!style) 
						{
							//var color = size>25 ? "192,0,0" : size>8 ? "255,128,0" : "0,128,0";
							var color =  clusterColor;
							var radius = Math.max(8, Math.min(size*0.75, 20));
							var calc = 2*Math.PI*radius/6;
							var dash : number[]= [ 0, calc, calc, calc, calc, calc, calc ];
							
							style = styleCache[size] = new olStyle.Style({
							image: new olStyle.Circle({
								radius: radius,
								stroke: new olStyle.Stroke({
								color: "rgba("+color+",0.5)", 
								width: 15 ,
								lineDash: dash,
								lineCap: "butt"
								}),
								fill: new olStyle.Fill({
								color:"rgba("+color+",1)"
								})
							}),
							text: new olStyle.Text({
								text: size.toString(),
								//font: 'bold 12px comic sans ms',
								//textBaseline: 'top',
								fill: new olStyle.Fill({
								color: '#fff'
								})
							})
							});
						}
						return style;
					};
		var clusterLayer : AnimatedCluster = new AnimatedCluster(animatedClusterOptions);
	},
		  
	processEventMarkerMove : function(event : any)
	{
		console.log("markerMove detected");
		var lonlat                 = olProj.transform(event.features.getArray()[0].getGeometry().getCoordinates(), 'EPSG:3857', 'EPSG:4326');
		try {
			var parameters : Partial<PrimeFaces.ajax.ShorthandConfiguration>;
			//var options : PrimeFaces.ajax.Configuration;
			var options = {} as PrimeFaces.ajax.Configuration;
			options.source = GeoJSF.map.getTarget();
			options.event  = "markerMove";
			options.update = GeoJSF.updateOnClick;
			options.process = "@form";
			options.params = [
					{name: 'org.geojsf.click.lat',				value: lonlat[1]},
					{name: 'org.geojsf.click.lon',				value: lonlat[0]},
					];
			options.oncomplete = function(xhr:any, status:any, args:any) {console.log('markerMovev AJAX request sent. Resolution : ' +GeoJSF.map.getView().getResolution()+ ' ' +GeoJSF.map.getView().getProjection().getUnits())};
			
			PrimeFaces.ab(
				options
			);
		} catch(e) {
			console.log("MarkerMove failed." +e);
		}
		finally {}
	},
		  
	initMap : function(mapDiv : string,baseMap : string)
	{
		let layers: TileLayer<olSource.OSM>[] = [];
		if (baseMap=="osm")
		{
			var titleLayer = new TileLayer({
				source: new olSource.OSM()
			})
			layers.push(titleLayer);
		}
		// console.log(layers);
		this.map = new ol.Map({
			controls     : olControl.defaults({ 
							attribution: false,
							zoom       : false,
							rotate     : false
							}),
			layers       : layers,
			target       : mapDiv
		});

		// This is important due to the calculations of the extent needed
		// in initial TILED parameter TILESORIGIN in WMS
		this.map.getView().setCenter([0,0]);
		this.map.getView().setResolution(1000);
	},
		  
	centerMap : function(lat : number, lon : number)
	{
		
		// GeoJSF.map.getView().setCenter([lot,lan]);
		console.log("Centering map to lat/lon: " +lat +"/" +lon);
		var centerPoint = new olGeom.Point(olProj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857'));
		console.log("Constructed Point: " +centerPoint);
		console.log("Constructed Points' coordinates: " +centerPoint.getCoordinates());
		console.log("Map Size: " +this.map.getSize());
		if (typeof(this.map.getSize()) == "undefined" || this.map.getSize() == null)
		{
			this.map.setSize([840,400]);
		}
		GeoJSF.map.getView().centerOn(centerPoint.getCoordinates(), this.map.getSize(), [this.map.getSize()[0]/2, this.map.getSize()[1]/2]);
	},
		  
	// Remove all layers and add the base layer again
	resetLayers : function()
	{
		console.log("Reseting all layers");
		if (GeoJSF.map.getLayers)
			{
				for (var i = GeoJSF.map.getLayers.length - 1; i >= 0; i--) {
					GeoJSF.map.removeLayer(GeoJSF.map.getLayers().item(i));
				}
			}
			//GeoJSF.map.addLayer(GeoJSF.featureLayer);
	},
		  
	addLayer : function(name : string, url : string, params : any)
	{
		console.log("Trying to add " +params.layers);
		var sizeOfMap = this.map.getSize();
		console.log("Size of Map:" +sizeOfMap);
		
		var viewOfMap = this.map.getView();
		console.log("View of Map:" +viewOfMap);
		console.log("View Projection: " +viewOfMap.getProjection().getCode());
		//console.log("View Center: " +viewOfMap.getCenter());
		
		var bounds = viewOfMap.calculateExtent(sizeOfMap);
		console.log("Bounds: " +bounds);
		
		// Get an array of bounds
		// e.g. [1555173.7562473097, 1494886.6852190704, 1603273.7562473097, 1534886.6852190704]
		var extents     = this.map.getView().calculateExtent(this.map.getSize());
		console.log("Extents:" +extents);
		
		// Transform this extent array to GPS coordinates (from Mercator)
		// e.g. [13.970363546985254, 13.30751144611122, 14.402453198646745, 13.656934756062881]
		var extent      = olProj.transformExtent(extents, 'EPSG:3857', 'EPSG:4326');

		// Now get the bottom left of this extent array
		// e.g. [13.970363546985254, 13.30751144611122]
		var coordinates = olExtent.getBottomLeft(extent);
		
		// Center the map with the center of the new layer
		// e.g. [13.970363546985254, 13.30751144611122]
		// var coordinates = olExtent.getBottomLeft(extent);
		
		//GeoJSF.map.getView().setCenter([0,0]);

		console.log(coordinates);

		var layer = new TileLayer({
			source: new olSource.TileWMS({
					url        : url,
					params     : {'LAYERS'     : params.layers,
								'TILED'      : true,
								'TILESORIGIN': coordinates[0] +", " +coordinates[1],
								},
					serverType : 'geoserver'
					})
			});
		layer.set('name', name);
		GeoJSF.map.addLayer(layer);
		// var layerExtent = layer.getSource().getExtent();
		var options : FitOptions = {};
		options.size = GeoJSF.map.getSize();
		GeoJSF.map.getView().fit(extents, options);
	},

	ajaxResponse : function(data : any)
	{
		console.log(data);
	},
		  
	updateTime : function(layerName : string, time : number)
	{
		var layers = this.map.getLayersByName(layerName);
		var layer  = layers[0];
		var params : any = {};
		var date  = new Date(time);
		var isoTime = date.toISOString();
		params.time = isoTime;
		console.log("Merging new Time parameter: " +isoTime);
		layer.mergeNewParams(params);
		
		//var options : PrimeFaces.ajax.Configuration;
		var options = {} as PrimeFaces.ajax.Configuration;
		options.source = GeoJSF.map.getTarget();
		options.event  = "updateTime";
		options.process = "@all";
		options.params = [{name: 'org.geojsf.update.time', value: time}];
		options.oncomplete = function(xhr:any, status:any, args:any) {console.log("Back in client.");};
		
		PrimeFaces.ab(
			options
		);
	},

	calculateDistance : function(coordinate1 : any, coordinate2: any)
	{
		console.log("Calculating distance between two coordinates.");
		olSphere.getDistance(coordinate1, coordinate2);
	},
		  
	updateSqlViewParams : function(parameters : any)
	{
		console.log("SQL View Parameter Update requested.");
		GeoJsfUtil.updateLayerParams("viewparams", parameters);
	},
		
	updateViewParams : function(key : string, value : any)
	{
		console.log("View Parameter Update requested. Trying to add " +key +" with a value of " +value);
		GeoJsfUtil.updateLayerParams(key, value);
	},
		
	updateSldParams : function(parameters : any)
	{
		console.log("SLD Parameter Update requested.");
		GeoJsfUtil.updateLayerParams("env", parameters);
	},
		  
	updateSld : function(parameters : any)
	{
		console.log("SLD Update requested.");
		GeoJsfUtil.updateLayerParams("SLD", parameters);
	},
	
	updateEcqlParams : function(parameters : any)
	{
	console.log("ECQL Parameter Update requested.");
	GeoJsfUtil.updateLayerParams("CQL_FILTER", parameters);
	},
		  
	switchLayerClassic : function(layerId : string)
	{
		// This is the pure JSF based approach, not having an 'oncomplete' method:	 
		// jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
		
		console.log("switch request detected for layer " +layerId);
		//var options : PrimeFaces.ajax.Configuration;
		var options = {} as PrimeFaces.ajax.Configuration;
		options.source = GeoJSF.map.getTarget();
		options.event  = "layerSwitch";
		options.update = "@form";
		options.process = "@form";
		options.params = [{name: 'org.geojsf.switch.layer', value: layerId}];
		options.oncomplete = function(xhr:any, status:any, args:any) {GeoJSF.performLayerSwitch(xhr, status, args);}
		
		PrimeFaces.ab(
			options
		);
	},
		  
	secondRun : function()
	{
		//var options : PrimeFaces.ajax.Configuration;
		var options = {} as PrimeFaces.ajax.Configuration;
		options.source = GeoJSF.map.getTarget();
		options.event  = "updateModel";
		options.update = "@form";
		options.process = "@all";
		options.oncomplete = function(xhr : any, status : any, args : any) {console.log("Update complete.");}
		
		PrimeFaces.ab(
			options
		);
	},
		  
	refreshMap : function()
	{
		console.log("Refreshing the map.");
		
		// This is the pure JSF based approach, not having an 'oncomplete' method:	 
        // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
        // This is the PrimeFaces based solution along with an 'oncomplete' call
		
		//var options : PrimeFaces.ajax.Configuration;
		var options = {} as PrimeFaces.ajax.Configuration;
		options.source = GeoJSF.map.getTarget();
		options.event  = "updateMap";
		options.update = "@form";
		options.process = "@all";
		options.oncomplete = function(xhr : any, status : any, args : any) {GeoJSF.performLayerSwitch(xhr, status, args);};
		
		PrimeFaces.ab(
			options
		);
	},
				  
	initMarker : function(lat : number, lon: number)
	{
		var options = {} as any;
		options.projection = 'EPSG:3857';
		GeoJSF.vectorSource = new olSource.Vector(options); 

		//GeoJSF.addPoint( -6.121435, 106.774124,GeoJSF.featureLayer);
		console.log(GeoJSF.iconStyle);

		//layers.push(GeoJSF.featureLayer);
		console.log(GeoJSF.markerPosition);
		GeoJSF.feature = new ol.Feature({
			geometry: new olGeom.Point(olProj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857')),
			name: 'GeoJSF Marker'
		});
		console.log((GeoJSF.feature.getGeometry() as olGeom.Point).getCoordinates()[0] +"," +(GeoJSF.feature.getGeometry() as olGeom.Point).getCoordinates()[1]);
		console.log("Layers " +GeoJSF.map.getLayers());
		//console.log(GeoJSF.feature);
		GeoJSF.features = [];
		GeoJSF.features.push(GeoJSF.feature);
		GeoJSF.vectorSource.clear;
		GeoJSF.vectorSource.addFeatures(GeoJSF.features);
		console.log(GeoJSF.vectorSource);
		GeoJSF.featureLayer = new olLayer.Vector({
			style: GeoJSF.iconStyle,
			source: GeoJSF.vectorSource,
			//name: 'marker Layer',
			visible: true,
			zIndex: 1000
		});
		GeoJSF.map.addLayer(GeoJSF.featureLayer);
		// Process marker
		var features = GeoJSF.vectorSource.getFeatures();
		console.log("features: " +features);

		var modify = new olInteraction.Modify({
			features: new ol.Collection([GeoJSF.feature])
		});
		modify.on('modifyend', GeoJSF.processEventMarkerMove);

		//GeoJSF.feature.on('modifyend', GeoJSF.processEventMarkerMove ,GeoJSF.feature);
		GeoJSF.map.addInteraction(modify);
		GeoJSF.map.getView().centerOn((GeoJSF.feature.getGeometry() as olGeom.Point).getCoordinates(), GeoJSF.map.getSize(), [GeoJSF.map.getSize()[0]/2, GeoJSF.map.getSize()[1]/2]);
	},
		  
	setMarkerUrl: function(srcUrl : string)
	{
		GeoJSF.markerUrl = srcUrl;
		GeoJSF.iconStyle = new olStyle.Style({
				image: new olStyle.Icon({
				anchor: [0.5, 0.75],
				anchorXUnits: 'fraction',
				anchorYUnits: 'fraction',
				opacity: 1,
				scale: 0.6,
				src: srcUrl
				})
			});
		console.log("Marker source URL set to " +GeoJSF.markerUrl);
	},
		  
	setMarkerPosition: function(lat : number, lon : number)
	{
		GeoJSF.markerPosition = new olGeom.Point(olProj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857'));
		GeoJSF.feature = new ol.Feature({
				geometry: new olGeom.Point(olProj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857')),
				name: 'GeoJSF Marker'
			});
		console.log("Marker position set to " +GeoJSF.markerPosition.getCoordinates()[0] +"," +GeoJSF.markerPosition.getCoordinates()[1]);
	},

	addPointDataLayer : function(name : string, url : string, theGlyph : string, theText : string)
	{
		var viewOfMap = GeoJSF.map.getView();
		// GeoJSON layer
		var vectorSource = new olSource.Vector({
			url: url,
		//	projection: viewOfMap.getProjection().getCode(),
			format: new GeoJSON(),
		});
		
		var vectorlayer = new olLayer.Vector({
		//	name: name,
			source: vectorSource,
			style: function (feature) 
			{
				var img_Src = feature.get(theGlyph);
				return new olStyle.Style({
					image:new olStyle.Icon({
						anchor: [0.5, 46],
						anchorXUnits: 'fraction',
						anchorYUnits: 'pixels',
						src: img_Src,
					}),
					});
				},
			});	
		this.map.addLayer(vectorlayer);
	},
  
		  
	performLayerSwitch : function(xhr : any, status : any, args : any)
	{
		try 
		{
		console.log("Performing layer switch via OpenLayers.");
		console.log(args);
		console.log(args.toggleLayer);
		console.log(JSON.parse(args.toggleLayer));
		var command = JSON.parse(args.toggleLayer);
		console.log("Map has " +GeoJSF.map.getLayers().getLength() +" layers");
			console.log("OpenLayers: Please set " +command.serviceId +" to have the layers " +command.layer +" using the method " +command.command);
		if (command.command == "hide")
		{
			for (var i = GeoJSF.map.getLayers().getLength() - 1; i >= 0; i--) {
			if(GeoJSF.map.getLayers().item(i).get('name') == command.serviceId ) 
			{
				GeoJSF.map.getLayers().item(i).setVisible(false);
			} else {console.log('big problem');}
		}}
		if (command.command == "show")
			{
								for (var i = GeoJSF.map.getLayers().getLength() - 1; i >= 0; i--) {
									if(GeoJSF.map.getLayers().item(i).get('name') == command.serviceId ) 
									{
										GeoJSF.map.getLayers().item(i).setVisible(true);
									} else {console.log('big problem');}
			}}
					if (command.command == "merge")
			{
				var layersToAdd = "";
							
					for (var i = GeoJSF.map.getLayers().getLength() - 1; i >= 0; i--) {
					if ((GeoJSF.map.getLayers().item(i) as Layer).getSource() instanceof olSource.OSM)
					{
					console.log("Ignoring OSM base layer"); 
					}
					else
					{
						if(GeoJSF.map.getLayers().item(i).get('name') == command.serviceId ) 
						{
						for (var counter in command.layer)
						{
							layersToAdd += command.layer[counter];
							layersToAdd += ",";
						}
						var layerList = layersToAdd.substring(0, layersToAdd.length - 1);
						console.log("Trying to add " +layerList +" to Layer " +GeoJSF.map.getLayers().item(i).get('name'));
						var params : any = {};
						params.LAYERS = layerList;
						console.log("New parameter set: ");
						console.log(params);
						(((GeoJSF.map.getLayers().item(i) as Layer).getSource() as olSource.TileWMS).updateParams(params));
						} else {console.log('big problem');}
					}
				}
			}
		} catch(e) {
			console.log("A problem occured when interpreting layer switch command. Maybe NULL?" +e);
		}
		finally {}
		GeoJSF.secondRun();
	},

	addGeoJSONLayer: function(url : string) {
		this.chartlayer = new olLayer.Vector(
			{
				source: new olSource.Vector(
				{
					format: new GeoJSON(),
					url: url
				}),
			//	renderOrder: ol.ordering.yOrdering(),
			//	name: 'geojsonLayer',
				
				// Create chart style
				style: function(feature) 
				{ 
					// Prepare the variables
					// Sum is the sum of all values for this chart for calculations
					var values:any[] = [];
					var names:any[]  = [];
					var sum    =  0;
					
					// Activate in case of problems
					// console.log(feature.get("pieChartParts"));
					
					// The property "pieChartParts" need to have the sector and the value as properties
					if (feature.get("pieChartParts"))
					{
						for (const [sector, value] of (Object as any).entries(feature.get("pieChartParts"))) {
							values.push(value);
							sum = sum + value;
							names.push(sector);					
						}
					}
					
					// Calculate the radius based on the sum of values
					var radius = 20 * Math.sqrt (sum / Math.PI);
					
					// Define the actual Chart details
					var options : any = {
						type: 'pie', 
						radius: radius, 
						displacement: [0,0],
						data: values, 
						colors: 'classic',
						rotateWithView: true,
						animation: false,
						stroke: new olStyle.Stroke({
							color: "#000",
							width: 2
						})
						}
					
					var style = [
					  new olStyle.Style({
						image: new Chart(options)})
					];
					
					// Now the text for the pie parts will be prepared
					// For every value, a caption will be placed next to it
					var s = 0;
					for (var i=0; i<values.length; i++) {
					  var d = values[i];
					  var a = (2*s+d)/sum * Math.PI - Math.PI/2; 
					  var v = Math.round(d/sum*1000);
					  if (v>0) {
						style.push(new olStyle.Style({
						  text: new olStyle.Text({
							text: names[i],
							offsetX: Math.cos(a)*(radius+3),
							offsetY: Math.sin(a)*(radius+3),
							textAlign: (a < Math.PI/2 ? "left":"right"),
							textBaseline: "middle",
							stroke: new olStyle.Stroke({ color:"#fff", width:2.5 }),
							fill: new olStyle.Fill({color:"#333"})
						  })
						}));
					  }
					  s += d;
				  }
				 return style;
				}	
			});
		console.log("Created Layer");
		console.log(this.chartlayer);
		this.map.addLayer(this.chartlayer);
	  },
	  
	  
  };

  export var GeoJSF = (window as any).GeoJSF;
