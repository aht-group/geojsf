var GeoJsfMarkerLayer = {	
		
    addMarkerLayer : function(data, iconUrl, name)
    {
	// Use a Wikipedia SVG Map Marker as fallbak
	if (!iconUrl) {iconUrl = 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Map_marker.svg/780px-Map_marker.svg.png';}
	
	// Here all markers are to be stored
	var iconFeatures=[];
	
	// This will be the source data from the backend in JSON
	// var jsonData;
	console.log('test');
	var jsonData = JSON.parse(data);
	console.log('test2');
	
	// Function to add all given markers to vector layer (to be called by oncomplete)
	function addFeatures(jsonDataObject) 
	{
	    for (var i = 0; i < jsonDataObject.length; i++) 
	    { 
		var iconFeature = new ol.Feature({
		    id: jsonDataObject[i].id,
		    geometry: new ol.geom.Point(ol.proj.transform([jsonDataObject[i].lon, jsonDataObject[i].lat], 'EPSG:4326',     
		    'EPSG:3857')),
		    name: jsonDataObject[i].name
		    });
		iconFeatures.push(iconFeature);
	    }               
	};
	console.log('test3');
	
	// Test for Function above
	addFeatures(jsonData);

	// Constructing the OpenLayers vector layer source
	var vectorSource = new ol.source.Vector({
	    features: iconFeatures
	});

	// Constructing the OpenLayers vector layer style
	var iconStyle = new ol.style.Style({
	  image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
		anchor: [0.0, 0.0],
		anchorXUnits: 'fraction',
		anchorYUnits: 'pixels',
		opacity: 0.75,
		scale: 0.035,
		src: iconUrl
	  }))
	});

	// The complete vector layer object to be added to the map
	var vectorLayer = new ol.layer.Vector({
	  source: vectorSource,
	  style: iconStyle
	});
	
	// Set the name
	vectorLayer.set('name', name);
	
	GeoJSF.map.addLayer(vectorLayer);

    },
    
    addPopopUp : function()
    {
	var popup = new ol.Overlay({
		element: document.getElementById('popup'),
	      });
	GeoJSF.map.addOverlay(popup);
    },

    removeMarkerLayer : function(name)
    {

    }
};