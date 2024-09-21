var GeoJsfControl = {	
		
	addZoom : function(type, position)
	{
				// Please use CSS to configure appearance
				console.log('ZOOOOOOM.');
				var zoomslider = new olControl.ZoomSlider();
				GeoJSF.map.addControl(zoomslider);
	},
	
	addScale : function(position, system, major, sub)
	{
				// Please use CSS to configure appearance
				var mousePosition = new olControl.MousePosition({
									   coordinateFormat: olCoordinate.createStringXY(2),
									   projection      : 'EPSG:4326'
									});
				GeoJSF.map.addControl(mousePosition);
	},
	
	addScaleBar : function(position, system, major, sub)
	{
				// Please use CSS to configure appearance
				console.log('Adding scale line.');
				GeoJSF.map.addControl(new olControl.ScaleLine());
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
		console.log('Graticule not yet implemented');
		/*
		var style = new ol.style.Style();
			style.setStroke (new ol.style.Stroke({ color: "rgba(255,120,0,0.9)", width:1 }));
			//style.setFill (new ol.style.Fill({ color:  "#ffff"} ));
			style.setText (new ol.style.Text({
			  stroke: new ol.style.Stroke({ color:"#fff", width:2 }),
			  fill: new ol.style.Fill({ color: "rgba(255,120,0,0.9)" }),
			  font: "10px",
			}));
		// Create the graticule component
		var graticule = new olControl.Graticule({
			maxResolution: 250, step: 0.1, stepCoord: 1, margin:0, projection: 'EPSG:4326', formatCoord:function(c){ return c.toFixed(1)+"°" },
		});
		graticule.setStyle(style);
		graticule.setMap(GeoJSF.map);
		*/
	},
	
	addRotation : function()
	{
		var rotation = new olControl.Rotate();
		GeoJSF.map.addControl(rotation);
	},

	addNorthArrow : function(imagePath)
	{
		const northArrowControl = new NorthArrow({
			imagePath: imagePath
		});
		GeoJSF.map.addControl(northArrowControl);
		/*
		olControl.NorthArrow = function(opt_options) {
			this.options = opt_options || {};
			this.options.class = this.options.class || 'ol-northarrow';
			this.options.imagePath = this.options.imagePath || imagePath;
			this.div = document.createElement('div');
			this.div.className = this.options.class + ' ol-unselectable';
			narrowImage = document.createElement('img');
			narrowImage.src = this.options.imagePath;
			this.div.appendChild(narrowImage);
			return new olControl.Control({ element: this.div });
		};
		ol.inherits(olControl.NorthArrow, olControl.Control);
		olControl.NorthArrow.prototype.setMap = function(map) {
			olControl.Control.prototype.setMap.call(this, map);
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
		var northArrowControlObj = new olControl.NorthArrow();
		GeoJSF.map.addControl(northArrowControlObj);
		*/
	},
	
	addPopUp : function()
	{
	 // Control Select 
	  var selectedFeature = new ol.interaction.Select({});
	  GeoJSF.map.addInteraction(selectedFeature);
	
	// On selected => show/hide popup
	  selectedFeature.getFeatures().on(['add'], function(e) {
		   let coordinate = e.element.getGeometry().getCoordinates();
		console.log(coordinate);

		let feature = e.element;
		layerSource = selectedFeature.getLayer(feature);
		GeoJSF.processEventSelectFeature(feature.get("id"), layerSource.get('name'));

		GeoJSF.popUpOverlay.setPosition(coordinate);
	  })
	  
	  selectedFeature.getFeatures().on(['remove'], function(e) {
		 GeoJSF.popUpOverlay.setPosition(undefined);
		 olControl.PopUp.popUpCloser.blur();
		 return false;
	  })
	},
	
	addPopUpOverlay : function()
	{
		olControl.PopUp = {};
		olControl.PopUp.popUpContainer = document.createElement('div');
		olControl.PopUp.popUpContainer.className = 'ol-popup';
		olControl.PopUp.popUpContainer.id = 'popup';
		
		olControl.PopUp.popUpCloser = document.createElement('a');
		olControl.PopUp.popUpCloser.id = 'popup-closer';
		olControl.PopUp.popUpCloser.className = 'ol-popup-closer';
		
		olControl.PopUp.popUpContent = document.createElement('div');
		olControl.PopUp.popUpContent.id = 'popup-content';
		
		olControl.PopUp.popUpContainer.appendChild(olControl.PopUp.popUpCloser);
		olControl.PopUp.popUpContainer.appendChild(olControl.PopUp.popUpContent);
		
		
		olControl.PopUp.content = function(coordinate){
						let ajaxPopUpContent = document.getElementById(PF('wgtPopupContent').id);
						return ajaxPopUpContent.innerHTML;
		}
			
		/**
		 * Create an overlay to anchor the popup to the map.
		 */
		GeoJSF.popUpOverlay = new ol.Overlay({
		  element: olControl.PopUp.popUpContainer,
		  autoPan: {
			animation: {
			  duration: 250,
			},
		  },
		});
		
		/**
		 * Add a click handler to hide the popup.
		 * @return {boolean} Don't follow the href.
		 */
		 olControl.PopUp.popUpCloser.onclick = function () {
			GeoJSF.popUpOverlay.setPosition(undefined);
			olControl.PopUp.popUpCloser.blur();
			return false;
		};
		
		
		GeoJSF.map.addOverlay(GeoJSF.popUpOverlay);
	}
};

class NorthArrow extends olControl.Control {
    constructor(opt_options) {
        const options = opt_options || {};
        const element = document.createElement('div');
        element.className = (options.class || 'ol-northarrow') + ' ol-unselectable';
        const narrowImage = document.createElement('img');
        narrowImage.src = options.imagePath;
        element.appendChild(narrowImage);

        super({
            element: element,
            target: options.target
        });

        this.map = null;
        this.rotateArrow = this.rotateArrow.bind(this);
    }

    setMap(map) {
        super.setMap(map);
        if (map) {
            map.getView().on('change:rotation', this.rotateArrow);
            this.map = map;
        } else if (this.map) {
            this.map.getView().un('change:rotation', this.rotateArrow);
            this.map = null;
        }
    }

    rotateArrow(evt) {
        const radian = evt.target.getRotation();
        this.element.style.transform = 'rotate(' + radian * 180 / Math.PI + 'deg)';
    }
}
