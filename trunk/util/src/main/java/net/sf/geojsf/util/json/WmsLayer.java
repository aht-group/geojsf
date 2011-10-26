package net.sf.geojsf.util.json;

public class WmsLayer
{
	private String name;
	private String url;
	private Params params;

	public WmsLayer()
	{
		
	}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	
	public Params getParams() {return params;}
	public void setParams(Params params) {this.params = params;}

	
	//********** Inner class **********
	
	public class Params
	{
		private String layers;
		private String format;

		public Params()
		{
			
		}
		
		public String getLayers() {return layers;}
		public void setLayers(String layers) {this.layers = layers;}
		
		public String getFormat() {return format;}
		public void setFormat(String format) {this.format = format;}
		
		public String toString()
		{
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("layers: '");
			sb.append(layers);
			sb.append("', ");
			sb.append("format: '"+format+"'");
			sb.append("}");
			return sb.toString();
		}
	}
}
