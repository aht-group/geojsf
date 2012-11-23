public interface GeoJsfService
{
	String getCode();
	void setCode(String code);
	
	List<LAYER> getLayer();
	void setLayer(List<LAYER> layer);
}