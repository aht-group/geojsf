var GeoJsfControl = {	
		
		addZoom : function(type, position)
		{
                    // Please use CSS to configure appearance
					console.log('ZOOOOOOM.');
                    var zoomslider = new ol.control.ZoomSlider();
                    GeoJSF.map.addControl(zoomslider);
		},
		
		addScale : function(position, system, major, sub)
		{
                    // Please use CSS to configure appearance
                    var mousePosition = new ol.control.MousePosition({
                                           coordinateFormat: ol.coordinate.createStringXY(2),
                                           projection      : 'EPSG:4326'
                                        });
                    GeoJSF.map.addControl(mousePosition);
		},
		
		addScaleBar : function(position, system, major, sub)
		{
                    // Please use CSS to configure appearance
					console.log('Adding scale line.');
                    GeoJSF.map.addControl(new ol.control.ScaleLine());
		},
		
		removeScaleBar : function()
		{
                    // Deprecated, because it was primarily used for printing
                    GeoJSF.map.removeControl(GeoJsfControl.scaleBar);
		},
		
		addTimeManager : function()
		{
		    // Deprecated - new implementation needed
                    /*  timeManager = new OpenLayers.Control.TimeManager({
				units:OpenLayers.TimeUnit.HOURS,
				step:6,
				frameRate:0.85
			});
			GeoJSF.map.addControl(timeManager);
                    */
		},
                
		addGraticule : function()
		{
			// Create the graticule component
			var graticule = new ol.Graticule({
			  // the style to use for the lines, optional.
			  // (taken from openlayers.org standard example)
				strokeStyle: new ol.style.Stroke({
				color: 'rgba(255,120,0,0.9)',
				width: 2,
				lineDash: [0.5, 4]
			  })
			});
		    graticule.setMap(GeoJSF.map);
		},
		
		addRotation : function()
		{
			var rotation = new ol.control.Rotate();
			GeoJSF.map.addControl(rotation);
		},

		addNorthArrow : function(imagePath)
		{
			ol.control.NorthArrow = function(opt_options) {
				this.options = opt_options || {};
				this.options.class = this.options.class || 'ol-northarrow';
				this.options.imagePath = this.options.imagePath || imagePath;
				this.div = document.createElement('div');
				this.div.className = this.options.class + ' ol-unselectable';
				narrowImage = document.createElement('img');
				narrowImage.src = this.options.imagePath;
				this.div.appendChild(narrowImage);
				ol.control.Control.call(this, {
					element: this.div
				});
			};
			ol.inherits(ol.control.NorthArrow, ol.control.Control);
			ol.control.NorthArrow.prototype.setMap = function(map) {
				ol.control.Control.prototype.setMap.call(this, map);
				var self = this;
				var rotateArrow = function(evt){
					var radian = evt.target.getRotation();
					self.div.style.transform = 'rotate(' + radian * 180 / Math.PI + 'deg)';
				};
				if (map !== null) {
					if (self.map) {
						self.map.getView().un('change:rotation', rotateArrow);
					}
					self.map = map;
					map.getView().on('change:rotation', rotateArrow);
				} else {
					if (self.map) {
						self.map.getView().un('change:rotation', rotateArrow);
						self.map = null;
					}
				}
			}
			var northArrowControlObj = new ol.control.NorthArrow();
			GeoJSF.map.addControl(northArrowControlObj);
		}
	
};