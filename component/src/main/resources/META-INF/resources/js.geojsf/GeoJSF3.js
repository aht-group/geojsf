
/**
 * Protect window.console method calls, e.g. console is not defined on IE
 * unless dev tools are open, and IE doesn't define console.debug
 */
 (function() {
	if (!window.console) {
	  window.console = {};
	}
	// union of Chrome, FF, IE, and Safari console methods
	var m = [
	  "log", "info", "warn", "error", "debug", "trace", "dir", "group",
	  "groupCollapsed", "groupEnd", "time", "timeEnd", "profile", "profileEnd",
	  "dirxml", "assert", "count", "markTimeline", "timeStamp", "clear"
	];
	// define undefined methods as noops to prevent errors
	for (var i = 0; i < m.length; i++) {
	  if (!window.console[m[i]]) {
		window.console[m[i]] = function() {};
	  }    
	} 
  })();
  
  var GeoJSF = {	
		  map: null,
		  switcher: null,
		  commandCorrect: true,
		  updateOnClick: null,
		  updateOnMove: null,
		  updateOnPopUp: null,
		  toolTipFeature: null,
		  popUpOverlay: null,
		  id: null,
		  lastLayer: null,
		  baseLayer: null,
		  featureLayer: null,
		  markerPosition: null,
		  markerUrl: null,
		  features: null,
		  feature: null,
		  vectorSource: null,
		  scaleValues: null,
		  iconStyle: new ol.style.Style({
					  image: new ol.style.Icon({
						anchor: [0.5, 0.75],
						anchorXUnits: 'fraction',
						anchorYUnits: 'fraction',
						opacity: 1,
						scale: 0.6,
						src: 'http://localhost:8080/geojsf/javax.faces.resource/marker.png.jsf?ln=geojsf'
					  })
				  }),
		  
		  setAjaxUpdates : function(updateClicks, updateMove)
		  {
			  this.updateOnClick = updateClicks;
			  this.updateOnMove  = updateMove;
		  },
		  
		  setAjaxUpdatesPopUp : function(updatePopUp)
		  {
			  this.updateOnPopUp = updatePopUp;
		  },
		  
		  setScaleValues : function(values)
		  {
			  GeoJSF.scaleValues = values;
			  console.log('Allowed scale values are: ' +this.scaleValues);
		  },
		  
		  getCurrentLocation : function()
		  {
			  navigator.geolocation.getCurrentPosition(GeoJSF.processCurrentLocation);
		  },
		  
		  processCurrentLocation : function(location)
		  {
			  console.log("You are at: (lat/lon) (" +location.coords.latitude +"/" +location.coords.longitude +") with an accurancy of " +location.coords.accuracy);
		  },
		  
		  registerEventHandlers : function()
		  {
			  
				  try {
				  console.log("Trying to register event handlers");
				  GeoJSF.map.on("singleclick",  GeoJSF.processEventClick);
				  GeoJSF.map.on("moveend"    ,  GeoJSF.processEventMove);
				   console.log("Done registering.");
				  }
				  catch (e) {alert("Problem with registering Map events: " +e)}
		  },
		  
		  processEventMove : function(event)
		  {
			  console.log("mapMove");
			  console.log(event);
				  GeoJsfUtil.triggerJsfAjaxEvent(event, "mapMove");
						  /*
			  this.centerLat             = ol.proj.transform(event.map.getView().getCenter(), 'EPSG:3857', 'EPSG:4326')[0];
			  this.centerLon             = ol.proj.transform(event.map.getView().getCenter(), 'EPSG:3857', 'EPSG:4326')[1];
			  this.centerLon             = ol.proj.transform(event.map.getView().getCenter(), 'EPSG:3857', 'EPSG:4326')[1];
			  
			  // var extent = event.map.getView().calculateExtent(event.map.getSize());
			  var extent = ol.extent.applyTransform(event.map.getView().calculateExtent(event.map.getSize()), ol.proj.getTransform("EPSG:3857", "EPSG:4326"));
			  this.viewportBoundTop      = extent[0];
			  this.viewportBoundBottom   = extent[1];
			  this.viewportBoundLeft     = extent[2];
			  this.viewportBoundRight    = extent[3];
			  console.log(event.type +" has triggered change to center " +this.centerLat +"/"  +this.centerLon);
			  
			  
			  try {
				  PrimeFaces.ab({
					  process:  '@form', 
					   source:   GeoJSF.map.getTarget(), 
					   event:    'mapMove', 
					   update:   GeoJSF.updateOnMove,
					  params: [
								{name: 'org.geojsf.viewport.center.lon',     value: this.centerLon},
								{name: 'org.geojsf.viewport.center.lat',     value: this.centerLat},
								{name: 'org.geojsf.viewport.bottom',         value: this.viewportBoundBottom},
								{name: 'org.geojsf.viewport.top',            value: ol.extent.applyTransform(event.map.getView().calculateExtent(event.map.getSize()), ol.proj.getTransform("EPSG:3857", "EPSG:4326"))[0]},
								{name: 'org.geojsf.viewport.left',           value: this.viewportBoundLeft},
								{name: 'org.geojsf.viewport.right',          value: this.viewportBoundRight},
								{name: 'org.geojsf.viewport.scale',          value: Math.floor( GeoJSF.mapScale() )},
							   ],
					   oncomplete: function(xhr, status, args) {console.log('map move AJAX request sent.')}
				  });
			  } catch(e) {
				   console.log("MapMove failed." +e);
			  }
			  finally {}
			  */
		  },
		  
			mapScale : function () {
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
		  
		  processEventClick : function(event)
		  {
			  console.log("mapClick detected");
			  GeoJsfUtil.triggerJsfAjaxEvent(event, "mapClick");
			  /*
			  this.centerLat             = event.map.getView().getCenter()[1];
			  this.centerLon             = event.map.getView().getCenter()[0];
				var extent = event.map.getView().calculateExtent(event.map.getSize());
			  this.viewportBoundTop      = ol.proj.transform(extent[0], 'EPSG:3857', 'EPSG:4326');
			  this.viewportBoundBottom   = ol.proj.transform(extent[1], 'EPSG:3857', 'EPSG:4326');
			  this.viewportBoundLeft     = ol.proj.transform(extent[2], 'EPSG:3857', 'EPSG:4326');
			  this.viewportBoundRight    = ol.proj.transform(extent[3], 'EPSG:3857', 'EPSG:4326');
			  var lonlat                 = ol.proj.transform(event.coordinate, 'EPSG:3857', 'EPSG:4326');
			  console.log(this.viewportBoundTop);
			  console.log(extent);
			  try {
				  PrimeFaces.ab({
					  process:  '@form', 
					   source:   GeoJSF.map.getTarget(), 
					   event:    'mapClick', 
					   update:   GeoJSF.updateOnClick,
					  params: [
								{name: 'org.geojsf.click.lat',							value: lonlat[1]},
								{name: 'org.geojsf.click.lon',					  	value: lonlat[0]},
								{name: 'org.geojsf.viewport.center.lon',               value: this.centerLon},
								{name: 'org.geojsf.viewport.center.lat',               value: this.centerLat},
								{name: 'org.geojsf.viewport.bottom',                   value: this.viewportBoundBottom},
								{name: 'org.geojsf.viewport.top',                      value: this.viewportBoundTop},
								{name: 'org.geojsf.viewport.left',                     value: this.viewportBoundLeft},
								{name: 'org.geojsf.viewport.right',                    value: this.viewportBoundRight},
								{name: 'org.geojsf.viewport.resolution',               value: GeoJSF.map.getView().getResolution()},
								{name: 'org.geojsf.viewport.unit',                     value: GeoJSF.map.getView().getProjection().getUnits()},
								{name: 'org.geojsf.viewport.scale',                    value:   Math.floor( GeoJSF.mapScale() )},
							   ],
					   oncomplete: function(xhr, status, args) {
							 console.log('mapClick AJAX request sent. Resolution : ' +GeoJSF.map.getView().getResolution()+ ' ' +GeoJSF.map.getView().getProjection().getUnits());
							clickSearcher.updateCircleOnClick(lonlat);
					   }
				  });
			  } catch(e) {
				   console.log("MapClick failed." +e);
			  }
				  finally {}
			  */
		  },
		  
		  processEventPointerMove : function(event)
		  {
			// console.log("processEventPointerMove");
			//  console.log(event);
				  
					  
			 if (GeoJSF.toolTipFeature !== null) {
			    GeoJSF.toolTipFeature = null;
			  }
			
			  GeoJSF.map.forEachFeatureAtPixel(event.pixel, function (f) {
			    GeoJSF.toolTipFeature = f;
			    return true;
			  });
			
			  if (GeoJSF.toolTipFeature) 
			  {
				ol.control.PopUp.popUpContent.innerHTML = GeoJSF.toolTipFeature.get('ttinfo');
		    	GeoJSF.popUpOverlay.setPosition(event.coordinate);
			  }
			  else
			  {
				GeoJSF.popUpOverlay.setPosition(undefined);
				ol.control.PopUp.popUpCloser.blur();
			  } 
			},	 

		  processEventSelectFeature : function(featureId, type)
		  {
			  console.log("Select Feature detected id: " + featureId);
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
		  },
		  
		  processEventMarkerMove : function(event)
		  {
			  console.log("markerMove detected");
			  var lonlat                 = ol.proj.transform(event.features.getArray()[0].getGeometry().getCoordinates(), 'EPSG:3857', 'EPSG:4326');
			  try {
				  PrimeFaces.ab({
					  process:  '@form', 
					   source:   GeoJSF.map.getTarget(), 
					   event:    'markerMove', 
					   update:   GeoJSF.updateOnClick,
					  params: [
								{name: 'org.geojsf.click.lat',				value: lonlat[1]},
							   {name: 'org.geojsf.click.lon',				value: lonlat[0]},
							   ],
					   oncomplete: function(xhr, status, args) {console.log('markerMovev AJAX request sent. Resolution : ' +GeoJSF.map.getView().getResolution()+ ' ' +GeoJSF.map.getView().getProjection().getUnits())}
				  });
			  } catch(e) {
				   console.log("MarkerMove failed." +e);
			  }
				  finally {}
		  },
		  
		  initMap : function(mapDiv,baseMap)
		  {
			  var layers = [];
			  if (baseMap=="osm")
			  {
				  layers.push(new ol.layer.Tile({
									source: new ol.source.OSM()
								  }));
			  }
			  // console.log(layers);
			  GeoJSF.map = new ol.Map({
				  controls     : ol.control.defaults.defaults({ 
								  attribution: false,
								  zoom       : false,
								  rotate     : false
								  }),
				  layers       : layers,
				  target       : mapDiv
			  });
  
			  // This is important due to the calculations of the extent needed
			  // in initial TILED parameter TILESORIGIN in WMS
			  GeoJSF.map.getView().setCenter([0,0]);
			  GeoJSF.map.getView().setResolution(1000);
		  },
		  
		  centerMap : function(lat, lon)
		  {
			  // GeoJSF.map.getView().setCenter([lot,lan]);
						  console.log("Centering map to lat/lon: " +lat +"/" +lon);
						  centerPoint = new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857')),
						  console.log("Constructed Point: " +centerPoint);
						  console.log("Constructed Points' coordinates: " +centerPoint.getCoordinates());
						  GeoJSF.map.getView().centerOn(centerPoint.getCoordinates(), GeoJSF.map.getSize(), [GeoJSF.map.getSize()[0]/2, GeoJSF.map.getSize()[1]/2]);
		  },
		  
		  // Remove all layers and add the base layer again
		  resetLayers : function()
		  {
			  console.log("Reseting all layers");
			  if (GeoJSF.map.layers)
				  {
					  for (var i = GeoJSF.map.layers.length - 1; i >= 0; i--) {
						  GeoJSF.map.removeLayer(GeoJSF.map.layers[i]);
					  }
				  }
				  //GeoJSF.map.addLayer(GeoJSF.featureLayer);
		  },
		  
		  addLayer : function(name, url, params, options)
		  {
					  console.log("Trying to add " +params.layers);
					  var sizeOfMap = GeoJSF.map.getSize();
					  console.log("Size of Map:" +sizeOfMap);
					  
					  var viewOfMap = GeoJSF.map.getView();
					  console.log("View of Map:" +viewOfMap);
					  console.log("View Projection: " +viewOfMap.getProjection().getCode());
					  //console.log("View Center: " +viewOfMap.getCenter());
					  
					  var bounds = viewOfMap.calculateExtent(sizeOfMap);
					  console.log("Bounds: " +bounds);
					  
					  // Get an array of bounds
					  // e.g. [1555173.7562473097, 1494886.6852190704, 1603273.7562473097, 1534886.6852190704]
					  var extents     = GeoJSF.map.getView().calculateExtent(GeoJSF.map.getSize());
					  console.log("Extents:" +extents);
					  
					  // Transform this extent array to GPS coordinates (from Mercator)
					  // e.g. [13.970363546985254, 13.30751144611122, 14.402453198646745, 13.656934756062881]
					  var extent      = ol.proj.transformExtent(extents, 'EPSG:3857', 'EPSG:4326');
  
					  // Now get the bottom left of this extent array
					  // e.g. [13.970363546985254, 13.30751144611122]
					  var coordinates = ol.extent.getBottomLeft(extent);
					  
					  // Center the map with the center of the new layer
					  // e.g. [13.970363546985254, 13.30751144611122]
					 // var coordinates = ol.extent.getBottomLeft(extent);
					  
					  //GeoJSF.map.getView().setCenter([0,0]);
  
					  console.log(coordinates);
  
					  var layer = new ol.layer.Tile({
						  source: new ol.source.TileWMS({
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
				//	GeoJSF.map.getView().fit(extents, GeoJSF.map.getSize());
		  },

		addPointDataLayerWithSld : function(name, url, sldLayerUrl)
		{
			var viewOfMap = GeoJSF.map.getView();
		
			OpenLayers.Request.GET({
				url: sldLayerUrl,
				success: function(req) {
					sld = format.read(req.responseText);
					// GeoJSON layer
					var vectorSource = new ol.source.Vector({
						url:url,
						projection: viewOfMap.getProjection().getCode(),
						format: new ol.format.GeoJSON(),
					});

					var vectorlayer = new ol.layer.Vector({
						name: name,
						source: vectorSource,
						styleMap: new OpenLayers.StyleMap(sld.namedLayers[0].userStyles[0])
					});	

					GeoJSF.map.addLayer(vectorlayer);
				},
    		});
		},

		addPointClusterLayer : function(name, url, clusterColor)
		{
			var viewOfMap = GeoJSF.map.getView();
			
			// Style for the clusters
			const style = new ol.style.Style({
							fill: new ol.style.Fill({
							color: '#eeeeee',
							}),
						});
			var styleCache = {};

			// Cluster Source GeoJSON layer
			var clusterSource=new ol.source.Cluster({
				distance: 40,
				source: new ol.source.Vector({
					url: url,
					projection: viewOfMap.getProjection().getCode(),
					format: new ol.format.GeoJSON(),
				})
			});
		

			// Animated cluster layer
			var clusterLayer = new ol.layer.AnimatedCluster({
				name: 'Cluster',
				source: clusterSource,
				animationDuration:  0,
				// Cluster style
				style:  function(feature, resolution)
				{
				var size = feature.get('features').length;
				var style = styleCache[size];
				if (!style) 
				{
					//var color = size>25 ? "192,0,0" : size>8 ? "255,128,0" : "0,128,0";
					var color =  clusterColor;
					var radius = Math.max(8, Math.min(size*0.75, 20));
					var dash = 2*Math.PI*radius/6;
					var dash = [ 0, dash, dash, dash, dash, dash, dash ];
					
					style = styleCache[size] = new ol.style.Style({
					image: new ol.style.Circle({
						radius: radius,
						stroke: new ol.style.Stroke({
						color: "rgba("+color+",0.5)", 
						width: 15 ,
						lineDash: dash,
						lineCap: "butt"
						}),
						fill: new ol.style.Fill({
						color:"rgba("+color+",1)"
						})
					}),
					text: new ol.style.Text({
						text: size.toString(),
						//font: 'bold 12px comic sans ms',
						//textBaseline: 'top',
						fill: new ol.style.Fill({
						color: '#fff'
						})
					})
					});
				}
				return style;
				}
			});
		  
			GeoJSF.map.addLayer(clusterLayer);
		},
		  
		addMultipointDataLayer : function(name, url, params, options)
		{
			var viewOfMap = GeoJSF.map.getView();
			// GeoJSON layer
			var vectorSource = new ol.source.Vector({
				url:url,
				projection: viewOfMap.getProjection().getCode(),
				format: new ol.format.GeoJSON(),
			});

			const style = new ol.style.Style({
				fill: new ol.style.Fill({
				  color: '#eeeeee',
				}),
			  });

			var vectorlayer = new ol.layer.Vector({
				name: name,
				source: vectorSource,
				style: function (feature) 
				{
					var fillcolor = feature.get('color');
					style.getFill().setColor(fillcolor);
					return style;
				  },
				});	
				vectorlayer.setZIndex(-1);
				GeoJSF.map.getLayers().item(0).setOpacity(0.3);
				GeoJSF.map.addLayer(vectorlayer);
		},
  
				addPointDataLayer : function(name, url, theGlyph, theText)
		{
			var viewOfMap = GeoJSF.map.getView();
			// GeoJSON layer
			var vectorSource = new ol.source.Vector({
				url: url,
				projection: viewOfMap.getProjection().getCode(),
				format: new ol.format.GeoJSON(),
			});
			
			var vectorlayer = new ol.layer.Vector({
				name: name,
				source: vectorSource,
				style: function (feature) 
				{
					var img_Src = feature.get(theGlyph);
					return new ol.style.Style({
						image:new ol.style.Icon({
							anchor: [0.5, 46],
							anchorXUnits: 'fraction',
							anchorYUnits: 'pixels',
							src: img_Src,
						}),
					  });
				  },
				});	
			GeoJSF.map.addLayer(vectorlayer);
		  },
  
  
		  ajaxResponse : function(data)
		  {
			  console.log(data);
		  },
		  
		  updateTime : function(layerName, time)
		  {
			   var layers = this.map.getLayersByName(layerName);
			   var layer  = layers[0];
			   var params = {};
			   var date  = new Date(time);
			   var isoTime = date.toISOString();
			   params.time = isoTime;
			   console.log("Merging new Time parameter: " +isoTime);
			   layer.mergeNewParams(params);
			   
			  // This is the PrimeFaces based solution along with an 'oncomplete' call
			   PrimeFaces.ab({process: '@all', 
							   source: this.map.div, 
							   event: 'updateTime', 
							   params: [{name: 'org.geojsf.update.time', value: time}],
							   oncomplete: function(xhr, status, args) {console.log("Back in client.");}});
		  
		  },
		  
		  updateSqlViewParams : function(parameters)
		  {
			   console.log("SQL View Parameter Update requested.");
			   var params = {};
			   params.viewparams = parameters;
			   if (GeoJSF.map.getLayers())
						   {
							  GeoJSF.map.getLayers().forEach(function (layer) {
								  if (layer.getSource() instanceof ol.source.OSM)
								  {
									 console.log("Ignoring OSM base layer"); 
								  }
								  else
								  {
									  layer.getSource().updateParams(params);
									  console.log("Merging new SqlParameter parameter: " +params.viewparams);
								  }
							  });
						   }
		  },
		  
		  updateViewParams : function(key, value)
		  {
			   console.log("View Parameter Update requested. Trying to add " +key +" with a value of " +value);
			   var params = {};
			   params[key] = value;
			   if (GeoJSF.map.getLayers())
						   {
							  GeoJSF.map.getLayers().forEach(function (layer) {
								  if (layer.getSource() instanceof ol.source.OSM)
								  {
									 console.log("Ignoring OSM base layer"); 
								  }
								  else
								  {
									  layer.getSource().updateParams(params);
									  console.log("Merging new parameter: " +key +" with a value of " +value);
								  }
							  });
						   }
		  },
		  
		  
		  updateSldParams : function(parameters)
		  {
			   console.log("SLD Parameter Update requested.");
			   var params = {};
				   params.env = parameters;
						   if (GeoJSF.map.getLayers())
						   {
							  GeoJSF.map.getLayers().forEach(function (layer) {
								  if (layer.getSource() instanceof ol.source.OSM)
								  {
									 console.log("Ignoring OSM base layer"); 
								  }
								  else
								  {
									  layer.getSource().updateParams(params);
									  console.log("Merging new SldParameter parameter: " +params.env);
								  }
							  });
						   }
		  },
		  
		  updateSld : function(parameters)
		  {
			   console.log("SLD Update requested.");
			   var params = {};
				   params.SLD = parameters;
						   if (GeoJSF.map.getLayers())
						   {
							  GeoJSF.map.getLayers().forEach(function (layer) {
								  if (layer.getSource() instanceof ol.source.OSM)
								  {
									 console.log("Ignoring OSM base layer"); 
								  }
								  else
								  {
									  layer.getSource().updateParams(params);
									  console.log("Merging new SLD parameter: " +params.SLD);
								  }
							  });
						   }
		  },
		  
		  updateEcqlParams : function(parameters)
		  {
					  console.log("ECQL Parameter Update requested.");
					  var params = {};
						  params.CQL_FILTER = parameters;
					  if (GeoJSF.map.getLayers())
					  {
						 GeoJSF.map.getLayers().forEach(function (layer) {
							 if (layer.getSource() instanceof ol.source.OSM || layer.getSource() instanceof ol.source.Vector)
							 {
								console.log("Ignoring OSM base layer"); 
							 }
							 else
							 {
								 layer.getSource().updateParams(params);
								 console.log("Merging new ECQL parameter: " +params.CQL_FILTER);
							 }
						 });
					  }
		  },
		  
		  switchLayerClassic : function(layerId)
		  {
			   // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			   // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			   
			  console.log("switch request detected for layer " +layerId);
			  try{ 
				  PrimeFaces.ab({
						   process: '@form', 
						   source: GeoJSF.map.getTarget(), 
						   event: 'layerSwitch', 
						   update: '@form',
						   params: [{name: 'org.geojsf.switch.layer', value: layerId}],
						   oncomplete: function(xhr, status, args) {GeoJSF.performLayerSwitch(xhr, status, args);}
				  });
			  } catch(e) {
				   console.log("layer switch failed." +e);
			  } 
			  finally {}
		  },
		  
		  secondRun : function()
		  {
			  PrimeFaces.ab({process: '@all', 
							   source: GeoJSF.map.getTarget(), 
							   update: '@form',
							   event: 'updateModel', 
							   oncomplete: function(xhr, status, args) {console.log("Update complete.");}
			  });
		  },
		  
		  refreshMap : function()
		  {
			   // This is the pure JSF based approach, not having an 'oncomplete' method:	 
			   // jsf.ajax.request(elementId, 'layerChange', {execute: '@form', 'javax.faces.behavior.event': 'layerSwitch','javax.faces.partial.event': 'layerSwitch','org.geojsf.switch.service': serviceId,  'org.geojsf.switch.layer': layerId, 'org.geojsf.switch.on': active});
			   
			   // This is the PrimeFaces based solution along with an 'oncomplete' call
			   PrimeFaces.ab({process: '@all', 
							   source: GeoJSF.map.getTarget(), 
							   event: 'updateMap', 
							   params: [],
							   oncomplete: function(xhr, status, args) {GeoJSF.performLayerSwitch(xhr, status, args);}});
		  },
				  
		  initMarker : function(lat, lon)
		  {
					  GeoJSF.vectorSource = new ol.source.Vector({
						  projection: 'EPSG:3857'
					  }); 
  
					  //GeoJSF.addPoint( -6.121435, 106.774124,GeoJSF.featureLayer);
					  console.log(GeoJSF.iconStyle);
  
					  //layers.push(GeoJSF.featureLayer);
					  console.log(GeoJSF.markerPosition);
					  GeoJSF.feature = new ol.Feature({
						  geometry: new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857')),
						  name: 'GeoJSF Marker'
						});
						console.log(GeoJSF.feature.getGeometry().getCoordinates()[0] +"," +GeoJSF.feature.getGeometry().getCoordinates()[1]);
					  console.log("Layers " +GeoJSF.map.getLayers());
					  //console.log(GeoJSF.feature);
					  GeoJSF.features = [];
					  GeoJSF.features.push(GeoJSF.feature);
					  GeoJSF.vectorSource.clear;
					  GeoJSF.vectorSource.addFeatures(GeoJSF.features);
					  console.log(GeoJSF.vectorSource);
					  GeoJSF.featureLayer = new ol.layer.Vector({
						  style: GeoJSF.iconStyle,
						  source: GeoJSF.vectorSource,
						  name: 'marker Layer',
						  visibility: true,
						  zIndex: 1000
					  });
					  GeoJSF.map.addLayer(GeoJSF.featureLayer);
					  // Process marker
					  var features = GeoJSF.vectorSource.getFeatures();
					  console.log("features: " +features);
  
					  var modify = new ol.interaction.Modify({
						  features: new ol.Collection([GeoJSF.feature])
					  });
					  modify.on('modifyend', GeoJSF.processEventMarkerMove);
  
					 //GeoJSF.feature.on('modifyend', GeoJSF.processEventMarkerMove ,GeoJSF.feature);
					 GeoJSF.map.addInteraction(modify);
					 GeoJSF.map.getView().centerOn(GeoJSF.feature.getGeometry().getCoordinates(), GeoJSF.map.getSize(), [GeoJSF.map.getSize()[0]/2, GeoJSF.map.getSize()[1]/2]);
		  },
		  
		  setMarkerUrl: function(srcUrl)
		  {
			  GeoJSF.markerUrl = srcUrl;
			  GeoJSF.iconStyle = new ol.style.Style({
					  image: new ol.style.Icon({
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
		  
		  setMarkerPosition: function(lat, lon)
		  {
			  GeoJSF.markerPosition = new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857'));
			  GeoJSF.feature = new ol.Feature({
					  geometry: new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857')),
					  name: 'GeoJSF Marker'
					});
			  console.log("Marker position set to " +GeoJSF.markerPosition.getCoordinates()[0] +"," +GeoJSF.markerPosition.getCoordinates()[1]);
		  },
		  
		  performLayerSwitch : function(xhr, status, args)
		  {
			  try {
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
											  GeoJSF.map.getLayers()[i].setVisibility(false);
										  } else {console.log('big problem');}
				  }}
			  if (command.command == "show")
				  {
									  for (var i = GeoJSF.map.getLayers().getLength() - 1; i >= 0; i--) {
										  if(GeoJSF.map.getLayers().item(i).get('name') == command.serviceId ) 
										  {
											  GeoJSF.map.getLayers().item(i).setVisibility(true);
										  } else {console.log('big problem');}
				  }}
						  if (command.command == "merge")
				  {
					  var layersToAdd = "";
								  
									  for (var i = GeoJSF.map.getLayers().getLength() - 1; i >= 0; i--) {
										  if (GeoJSF.map.getLayers().item(i).getSource() instanceof ol.source.OSM)
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
								  var params = {};
								  params.LAYERS = layerList;
								  console.log("New parameter set: ");
								  console.log(params);
								  GeoJSF.map.getLayers().item(i).getSource().updateParams(params);
							  } else {console.log('big problem');}
						  }
					  }
				  }
			  } catch(e) {
				   console.log("A problem occured when interpreting layer switch command. Maybe NULL?" +e);
			  }
			  finally {}
			  GeoJSF.secondRun();
		  }
  };
  
  var clickSearcher = {
		  radiusFactor : 15,
		  enable : false,	
		  featureCircleOnClick : null,
		  enableClickSearchFeature : false,
		  
		  init:function(radFactor){
			  this.radiusFactor = radFactor;
			  this.enable = true;
		  },
		  
		   updateCircleOnClick: function(olEventObject) 
		   {
			  if(!this.enable){return;}
			  var latlon = [0.0,0.0];
			  if (olEventObject.coordinate)
			  {
				latlon = ol.proj.transform(olEventObject.coordinate, 'EPSG:3857', 'EPSG:4326');
			  }
			   
			  var radius = Math.round(GeoJSF.map.getView().getResolution()*this.radiusFactor).toString();
			  
			  if (this.featureCircleOnClick && this.featureCircleOnClick!== 'null' && this.featureCircleOnClick !== "undefined") {
				  GeoJSF.map.removeLayer(this.featureCircleOnClick);
				  this.featureCircleOnClick = null;
			  }
			  
			  var view = GeoJSF.map.getView();
			  var projection = view.getProjection();
			  var resolutionAtEquator = view.getResolution();
			  var center = ol.proj.fromLonLat([latlon[0], latlon[1]]);
			  var pointResolution = ol.proj.getPointResolution(projection, resolutionAtEquator, center);
			  var resolutionFactor = resolutionAtEquator/pointResolution;
			  radius = (radius / ol.proj.Units.METERS_PER_UNIT.m) * resolutionFactor;
			  
			  var circle = new ol.geom.Circle(center, radius);
			  var circleFeature = new ol.Feature(circle);
			  var vectorSource = new ol.source.Vector({features: [circleFeature]});
			   
			  this.featureCircleOnClick = new ol.layer.Vector({
				source: vectorSource,
				style: new ol.style.Style({
					fill: new ol.style.Fill({
						color: 'rgba(255, 100, 50, 0.3)'
					}),
					stroke: new ol.style.Stroke({
						width: 3,
						color: 'rgba(255, 0, 0, 1)'
					}),
					image: new ol.style.Circle({
						fill: new ol.style.Fill({
							color: 'rgba(55, 200, 150, 0.5)'
						}),
						stroke: new ol.style.Stroke({
							width: 3,
							color: 'rgba(255, 0, 0, 1)'
						}),
						radius: 7
					}),
				})
				});
			  GeoJSF.map.addLayer(this.featureCircleOnClick);
		  }
  };
  
  var dtSelectCallback = {
	  svgMarker: '<svg width="160" height="160" version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 160 160" viewPort="0 0 160 160" class="svgClass">' +
		  '<circle cx="30" cy="30" r="10" stroke="rgb(0, 191, 255)" stroke-width="1" fill="none" opacity="0.1">' +
		  '<animate attributeType="CSS" attributeName="stroke-width" from="1" to="30" dur="0.5s" begin="0s" repeatCount="indefinite" />' +
		  '<animate attributeType="CSS" attributeName="opacity" from="0.8" to="0.2" dur="0.5s" begin="0s" repeatCount="indefinite" />' +
		  '</circle>' +
		  '<circle cx="30" cy="30" r="10" fill="rgba(0,0,0,0.8)">' +
		  '</circle>' +
		  '</svg>',
  
	  lastMarker: '',
	  createMarker: function(lon, lat) {
		  this.removeMarker();
		  var marker = new ol.Feature({
			  geometry: new ol.geom.Point(ol.proj.fromLonLat([lon, lat])),
		  });
		 
		  marker.setStyle(new ol.style.Style({
			fill: new ol.style.Fill({
				color: 'rgba(255, 100, 50, 0.3)'
			}),
			stroke: new ol.style.Stroke({
				width: 3,
				color: 'rgba(255, 0, 0, 1)'
			}),
			image: new ol.style.Circle({
				fill: new ol.style.Fill({
					color: 'rgba(55, 200, 150, 0.5)'
				}),
				stroke: new ol.style.Stroke({
					width: 3,
					color: 'rgba(255, 0, 0, 1)'
				}),
				radius: 7
			}),
		}));
	
		  var vectorSource = new ol.source.Vector({
			  features: [marker]
		  });
  
		  this.lastMarker = new ol.layer.Vector({
			  source: vectorSource,
		  });
		  GeoJSF.map.addLayer(this.lastMarker);
	  },
	  removeMarker: function() {
		  if (this.lastMarker) {
			  GeoJSF.map.removeLayer(this.lastMarker);
			  this.lastMarker = '';
		  }
	  }
  };