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
			  let center = [0.0,0.0];
			  if (olEventObject.coordinate)
			  {
				center = olEventObject.coordinate;//olProj.transform(olEventObject.coordinate, 'EPSG:3857', 'EPSG:4326');	
			  }
			  
			  if (this.featureCircleOnClick && this.featureCircleOnClick!== 'null' && this.featureCircleOnClick !== "undefined") {
				  GeoJSF.map.removeLayer(this.featureCircleOnClick);
				  this.featureCircleOnClick = null;
			  }
			  
			  let view = GeoJSF.map.getView();
			  let projection = view.getProjection();
			  let resolutionAtEquator = view.getResolution();
			  let pointResolution = projection.getMetersPerUnit(projection.getUnits());
			  let resolutionFactor = resolutionAtEquator / pointResolution;
			  let radius =  resolutionFactor/this.radiusFactor;
			  let circle = new olGeom.Circle(center, radius);
			  this.featureCircleOnClick = new olLayer.Vector({
				  source: new olSource.Vector({
					  projection: 'EPSG:4326',
					  features: [new ol.Feature(circle)]
				  }),
				  style: new olStyle.Style({
					  stroke: new olStyle.Stroke({
						  color: 'rgba(20, 19, 19, 0.81)',
						  width: 30
					  }),
					  fill: new olStyle.Fill({
						  color: 'rgba(246, 6, 130, 0.2)'
					  })
				  })
			  });
			  GeoJSF.map.addLayer(this.featureCircleOnClick);
		  }
  };