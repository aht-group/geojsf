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
		  /*
		  marker.setStyle(new ol.style.Style({
			  image: new ol.style.Icon({
				  src: 'data:image/svg+xml;charset=utf-8,' + encodeURIComponent(this.svgMarker)
			  })
		  }));
		  
		  marker.setStyle(new ol.style.Style({
			  image: new ol.style.Icon(({
				  crossOrigin: 'anonymous',
				  src: '../geojsf/marker.png',
				  // the real size of your icon
				  size: [255, 300],
				  // the scale factor
				  scale: 0.2
			  }))
		  }));
		  */
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