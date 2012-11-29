public interface GeoJsfViewLayer
{
	VIEW getView();
	void setView(VIEW view);
	
	LAYER getLayer();
	void setLayer(LAYER layer);
	
	int getOrderNo();
	void setOrderNo(int orderNo);
	
	boolean isVisible();
	void setVisible(boolean visible);	
}