package org.geojsf.model.pojo.util.symbol;

import java.io.Serializable;

import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Figure",category="symbol",subset="symbol")
public class DefaultGeoJsfGraphicFigure implements EjbRemoveable,Serializable,EjbPersistable,
								JeeslGraphicFigure<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfGraphic,DefaultGeoJsfGraphicType,DefaultGeoJsfGraphicFigure,DefaultGeoJsfGraphicStyle>
{
	public static final long serialVersionUID=1;


	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public String resolveParentAttribute() {return JeeslGraphicFigure.Attributes.graphic.toString();}
	@ManyToOne
	private DefaultGeoJsfGraphic graphic;
	@Override public DefaultGeoJsfGraphic getGraphic() {return graphic;}
	@Override public void setGraphic(DefaultGeoJsfGraphic graphic) {this.graphic = graphic;}
	
	@ManyToOne
	private DefaultGeoJsfGraphicStyle style;
	public DefaultGeoJsfGraphicStyle getStyle() {return style;}
	public void setStyle(DefaultGeoJsfGraphicStyle style) {this.style = style;}
	
	private boolean css;
	@Override public boolean isCss() {return css;}
	@Override public void setCss(boolean css) {this.css = css;}
	
	private int position;
	@Override public int getPosition() {return position;}
	@Override public void setPosition(int position) {this.position = position;}
	
	private boolean visible;
	@Override public boolean isVisible() {return visible;}
	@Override public void setVisible(boolean visible) {this.visible = visible;}
	
	double size;
	@Override public double getSize() {return size;}
	@Override public void setSize(double size) {this.size = size;}
	
	String color;
	@Override public String getColor() {return color;}
	@Override public void setColor(String color) {this.color = color;}
	
	double offsetX;
	@Override public double getOffsetX() {return offsetX;}
	@Override public void setOffsetX(double offsetX) {this.offsetX = offsetX;}
	
	double offsetY;
	@Override public double getOffsetY() {return offsetY;}
	@Override public void setOffsetY(double offsetY) {this.offsetY = offsetY;}

	double rotation;
	@Override public double getRotation() {return rotation;}
	@Override public void setRotation(double rotation) {this.rotation = rotation;}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		return sb.toString();
	}
	
	@Override public boolean equals(Object object){return (object instanceof DefaultGeoJsfGraphicFigure) ? id == ((DefaultGeoJsfGraphicFigure) object).getId() : (object == this);}
	@Override public int hashCode(){return new HashCodeBuilder(35,39).append(id).toHashCode();}
}