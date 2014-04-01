var GeoJsfPlayer = {
		
		timestep: null,
		currentTime: null,
		playing: false,
		temporalLayers: null,
		
		setTemporalLayers : function(layers)
		{
			var temporalLayers = JSON.parse( layers );
		},
		
		setTime : function(time)
		{
			currentTime = time;
			var isoDate = new Date(time).toISOString();
			console.log("Time of player is set to " +isoDate +" in ISO format.");
		},

		start : function()
		{
			this.playing = true;
			while(this.playing==true)
			{
				currentTime = currentTime + timeStep;
				for (var layer in timeLayers)
					{
						GeoJSF.updateTime(layer, currentTime);
					}
			}
		},
		
		stop : function()
		{
			this.playing = false;
		}
};