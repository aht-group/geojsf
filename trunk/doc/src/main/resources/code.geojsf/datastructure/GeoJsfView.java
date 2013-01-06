import java.util.List;

public interface GeoJsfView
{
	String getCode();
	void setCode(String code);
	
	List<VL> getLayer();
	void setLayer(List<VL> layer);
	
	Double getX();
	void setX(Double x);
	
	Double getY();
	void setY(Double y);
	
	Integer getZoom();
	void setZoom(Integer zoom);
}