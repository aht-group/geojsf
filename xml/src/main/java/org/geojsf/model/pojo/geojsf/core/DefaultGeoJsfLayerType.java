package org.geojsf.model.pojo.geojsf.core;

import java.util.List;
import java.util.Map;

import org.geojsf.interfaces.model.core.GeoJsfLayerType;
import org.geojsf.model.pojo.io.graphic.DefaultGeoJsfGraphic;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.model.with.primitive.code.EjbWithCode;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="Type",category="sld",subset="sld",level=3)
public class DefaultGeoJsfLayerType implements GeoJsfLayerType<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfLayerType,DefaultGeoJsfGraphic>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	protected String symbol;
	public String getSymbol(){return symbol;}
	public void setSymbol(String symbol){this.symbol = symbol;}
	
	@Override public String getCode() {return null;}
	@Override public void setCode(String code) {}
	
	@Override public int getPosition() {return 0;}
	@Override public void setPosition(int position) {}
	
	@Override public boolean isVisible() {return false;}
	@Override public void setVisible(boolean visible) {}
	
	@Override public Map<String, DefaultGeoJsfLang> getName() {return null;}
	@Override public void setName(Map<String, DefaultGeoJsfLang> name) {}
	
	@Override public Map<String, DefaultGeoJsfDescription> getDescription() {return null;}
	@Override public void setDescription(Map<String, DefaultGeoJsfDescription> description) {}
	
	@Override public String getStyle() {return null;}
	@Override public void setStyle(String style) {}
	
	@Override public String getImage() {return null;}
	@Override public void setImage(String image) {}
	
	@Override public String getImageAlt() {return null;}
	@Override public void setImageAlt(String image) {}
	
	@Override public <P extends EjbWithCode> P getParent() {return null;}
	@Override public <P extends EjbWithCode> void setParent(P parent) {}
	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfLayerType) ? id == ((DefaultGeoJsfLayerType) object).getId() : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
	@Override
	public List<String> getFixedCodes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DefaultGeoJsfGraphic getGraphic() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setGraphic(DefaultGeoJsfGraphic graphic) {
		// TODO Auto-generated method stub
		
	}
}