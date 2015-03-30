package org.geojsf.model.pojo.sld;

import java.io.Serializable;
import java.util.Map;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.qualifier.EjbErNode;

import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;

@EjbErNode(name="ViewPort",category="geojsf")
public class DefaultGeoJsfSldType implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsStatus<DefaultGeoJsfSldType,DefaultGeoJsfLang,DefaultGeoJsfDescription>
{
	public static enum Code {welcome}
	public static final long serialVersionUID=1;
	
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public boolean equals(Object object)
	{
        return (object instanceof DefaultGeoJsfSldType) ? id == ((DefaultGeoJsfSldType) object).getId() : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
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
	public Map<String, DefaultGeoJsfLang> getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setName(Map<String, DefaultGeoJsfLang> name) {
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
	public String getStyle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStyle(String style) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getImageAlt() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setImageAlt(String image) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public <P extends EjbWithCode> P getParent() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <P extends EjbWithCode> void setParent(P parent) {
		// TODO Auto-generated method stub
		
	}

	
}