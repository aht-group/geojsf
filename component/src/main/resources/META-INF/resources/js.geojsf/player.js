var GeoJsfPlayer = {
		
		timestep: null,
		currentTime: null,
		maxTime: null,
		playing: false,
		animation: null,
		waitTime: 10000,
		displayId: null,
		temporalLayers: null,
		
		setTemporalLayers : function(layers)
		{
			this.temporalLayers = JSON.parse( layers );
		},
		
		init : function(min, max, displayId)
		{
			this.currentTime = min;
			this.maxTime = max;
			this.timestep = Math.round((max-min)/12);
			this.displayId = displayId;
			document.getElementById(this.displayId).innerHTML=new Date(this.currentTime).toISOString();
			var isoDate = new Date(this.currentTime).toISOString();
			console.log("Time of player is set to " +isoDate +" in ISO format. From " +this.currentTime);
		},

		start : function()
		{
			console.log("Start.");
			this.playing = true;
			this.animation = setInterval('GeoJsfPlayer.step(this.currentTime, this.timestep, this.displayId)', this.waitTime);
		},
		
		step : function()
		{
			this.currentTime = this.currentTime + this.timestep;
			document.getElementById(this.displayId).innerHTML=new Date(this.currentTime).toISOString();
			console.log("step" +new Date(this.currentTime).toISOString());
			GeoJsfPlayer.updateMap(this.temporalLayers);
		},
		
		stop : function()
		{
			clearInterval(this.animation);
			console.log('Stop!');
			this.playing = false;
		},
		
		updateMap : function(temporalLayers)
		{
			console.log("switching layer requested. " +this.temporalLayers);
			if ((typeof this.temporalLayers)=='number')
				{
					GeoJSF.updateTime(this.temporalLayers, this.currentTime);
				}
			for (var counter in this.temporalLayers)
				{
					console.log("Trying to update service " +this.temporalLayers[layerName]);
					GeoJSF.updateTime(layerName, this.currentTime);
				}
		}
};