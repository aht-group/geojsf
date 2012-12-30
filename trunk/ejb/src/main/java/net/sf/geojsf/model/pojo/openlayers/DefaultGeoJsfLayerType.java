package net.sf.geojsf.model.pojo.openlayers;

import java.io.Serializable;
import java.util.Map;

import net.sf.ahtutils.model.interfaces.EjbPersistable;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import net.sf.geojsf.model.pojo.util.DefaultGeoJsfLang;

public class DefaultGeoJsfLayerType implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsStatus<DefaultGeoJsfLang,DefaultGeoJsfDescription>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	
	
	//******************************************************************************
	
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setCode(String code) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setImage(String image) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<String, DefaultGeoJsfDescription> getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDescription(Map<String, DefaultGeoJsfDescription> description) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<String, DefaultGeoJsfLang> getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setName(Map<String, DefaultGeoJsfLang> name) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getStyle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStyle(String style) {
		// TODO Auto-generated method stub
		
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfLayerType)
             ? id == ((DefaultGeoJsfLayerType) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
	
}