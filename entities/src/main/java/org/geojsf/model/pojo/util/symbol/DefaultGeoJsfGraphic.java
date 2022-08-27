package org.geojsf.model.pojo.util.symbol;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.geojsf.model.pojo.util.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.util.DefaultGeoJsfLang;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.qualifier.er.EjbErNode;

@EjbErNode(name="Graphic",category="symbol",subset="sld")
public class DefaultGeoJsfGraphic implements JeeslGraphic<DefaultGeoJsfLang,DefaultGeoJsfDescription,DefaultGeoJsfGraphicType,DefaultGeoJsfGraphicFigure,DefaultGeoJsfGraphicStyle>
{
	public static final long serialVersionUID=1;

	
	public static String[] defaultLangs = {"fr","en","de"};
	
	private long id;
	@Override public long getId() {return id;}
	@Override  public void setId(long id) {this.id = id;}
	
	@Override public Long getVersionLock() {return new Long(0);}
	
	@ManyToOne
	private DefaultGeoJsfGraphicType type;
	public DefaultGeoJsfGraphicType getType() {return type;}
	public void setType(DefaultGeoJsfGraphicType type) {this.type = type;}

	@ManyToOne
	private DefaultGeoJsfGraphicStyle style;
	public DefaultGeoJsfGraphicStyle getStyle() {return style;}
	public void setStyle(DefaultGeoJsfGraphicStyle style) {this.style = style;}
	
	private byte[] data;
    @Override public byte[] getData() {return data;}
    @Override public void setData(byte[] data) {this.data = data;}
    
    private Integer size;
	public Integer getSize() {return size;}
	public void setSize(Integer size) {this.size = size;}
	
    private Integer sizeBorder;
	public Integer getSizeBorder() {return sizeBorder;}
	public void setSizeBorder(Integer sizeBorder) {this.sizeBorder = sizeBorder;}
	
	private String color;
	public String getColor() {return color;}
	public void setColor(String color) {this.color = color;}
	
	private String colorBorder;
	public String getColorBorder() {return colorBorder;}
	public void setColorBorder(String colorBorder) {this.colorBorder = colorBorder;}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="graphic")
	@OrderBy("position ASC")
	private List<DefaultGeoJsfGraphicFigure> figures;
	@Override public List<DefaultGeoJsfGraphicFigure> getFigures() {return figures;}
	@Override public void setFigures(List<DefaultGeoJsfGraphicFigure> figures) {this.figures = figures;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}