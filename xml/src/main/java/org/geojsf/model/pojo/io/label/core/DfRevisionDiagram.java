package org.geojsf.model.pojo.io.label.core;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfDescription;
import org.geojsf.model.pojo.io.locale.DefaultGeoJsfLang;
import org.jeesl.interfaces.model.io.label.er.JeeslRevisionDiagram;

@Table(name="RevisionDiagram", uniqueConstraints=@UniqueConstraint(columnNames={"code"}))
public class DfRevisionDiagram implements JeeslRevisionDiagram<DefaultGeoJsfLang,DefaultGeoJsfDescription,DfRevisionCategory>
{
    public static final long serialVersionUID=1;
   
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Override public long getId() {return id;}
    @Override public void setId(long id) {this.id = id;}

	@Override public String resolveParentAttribute() {return JeeslRevisionDiagram.Attributes.category.toString();}
    @ManyToOne
    private DfRevisionCategory category;
    @Override public DfRevisionCategory getCategory() {return category;}
    @Override public void setCategory(DfRevisionCategory category) {this.category = category;}

    private String code;
    @Override public String getCode() {return code;}
    @Override public void setCode(String code) {this.code=code;}

    private int position;
    @Override public int getPosition() {return position;}
    @Override public void setPosition(int position) {this.position = position;}

    @MapKey(name="lkey")
    @JoinTable(name="RevisionDiagramJtLang",joinColumns={@JoinColumn(name="diagram_id")},inverseJoinColumns={@JoinColumn(name="lang_id")})
    private Map<String,DefaultGeoJsfLang> name;
    @Override public Map<String,DefaultGeoJsfLang> getName() {return name;}
    @Override public void setName(Map<String,DefaultGeoJsfLang> name) {this.name = name;}

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @MapKey(name="lkey")
    @JoinTable(name="RevisionDiagramJtDescription",joinColumns={@JoinColumn(name="diagram_id")},inverseJoinColumns={@JoinColumn(name="description_id")})
    private Map<String,DefaultGeoJsfDescription> description;
    @Override public Map<String,DefaultGeoJsfDescription> getDescription() {return description;}
    @Override public void setDescription(Map<String,DefaultGeoJsfDescription> description) {this.description = description;}

    private boolean documentation;
    @Override public boolean isDocumentation() {return documentation;}
    @Override public void setDocumentation(boolean documentation) {this.documentation = documentation;}

    @Column(columnDefinition="text")
    private String dotGraph;
    @Override public String getDotGraph() {return dotGraph;}
    @Override public void setDotGraph(String dotGraph) {this.dotGraph=dotGraph;}
    
    private boolean dotManual;
    @Override public boolean isDotManual() {return dotManual;}
    @Override public void setDotManual(boolean dotManual) {this.dotManual = dotManual;}

    @Override public String toString()
    {
            StringBuffer sb = new StringBuffer();
            sb.append("[").append(id).append("]");
            sb.append(" ").append(code);
            sb.append(":"+dotGraph);
            return sb.toString();
    }

    @Override public boolean equals(Object object) {return (object instanceof DfRevisionDiagram) ? id == ((DfRevisionDiagram) object).getId() : (object == this);}
    @Override public int hashCode() {return new HashCodeBuilder(17,51).append(id).toHashCode();}
}