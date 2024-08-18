  var clickSearcher = {
		  radiusFactor : 15,
		  enable : false,	
		  featureCircleOnClick : null,
		  enableClickSearchFeature : false,
		  
		  init:function(radFactor){
			  this.radiusFactor = radFactor;
			  this.enable = true;
		  },
		  
		   updateCircleOnClick: function(lonlat) {
			   if(!this.enable){return;}
			  var radius = Math.round(GeoJSF.map.getView().getResolution()*this.radiusFactor).toString();
			  
			  if (this.featureCircleOnClick && this.featureCircleOnClick!== 'null' && this.featureCircleOnClick !== "undefined") {
				  GeoJSF.map.removeLayer(this.featureCircleOnClick);
				  this.featureCircleOnClick = null;
			  }
			  
			  var view = GeoJSF.map.getView();
			  var projection = view.getProjection();
			  var resolutionAtEquator = view.getResolution();
			  var center = ol.proj.fromLonLat([lonlat[0], lonlat[1]]);
			  var pointResolution = projection.getPointResolution(resolutionAtEquator, center);
			  var resolutionFactor = resolutionAtEquator/pointResolution;
			  radius = (radius / ol.proj.METERS_PER_UNIT.m) * resolutionFactor;
			  
			  var circle = new ol.geom.Circle(center, radius);
			  var circleFeature = new ol.Feature(circle);
			  var vectorSource = new ol.source.Vector({features: [circleFeature]});
			   
			  this.featureCircleOnClick = new ol.layer.Vector({
			  source: vectorSource
			  });
			  GeoJSF.map.addLayer(this.featureCircleOnClick);
		  }
  };