package org.geojsf.util.wfs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.geojsf.exception.GeoJsfDeveloperException;
import org.geojsf.factory.xml.gml.XmlCoordinatesFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.with.EjbWithGeometry;
import org.geojsf.interfaces.qualifier.IgnoreGeoJsfWfsProperty;
import org.geojsf.interfaces.wfs.WfsGetFeaturePropertyProvider;
import org.geojsf.model.xml.geojsf.Coordinate;
import org.geojsf.model.xml.specs.gml.Coordinates;
import org.geojsf.model.xml.specs.ogc.Distance;
import org.geojsf.model.xml.specs.wfs.GetFeature;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsIdFacade;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

@SuppressWarnings("rawtypes")
public class WfsPointQuery<W extends EjbWithGeometry,I extends EjbWithId,
							L extends UtilsLang,D extends UtilsDescription,
							G extends UtilsGraphic<L,D,G,GT,GS>,GT extends UtilsStatus<GT,L,D>,GS extends UtilsStatus<GS,L,D>,
							CATEGORY extends GeoJsfCategory<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							SERVICE extends GeoJsfService<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							LAYER extends GeoJsfLayer<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							MAP extends GeoJsfMap<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							VIEW extends GeoJsfView<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							VP extends GeoJsfViewPort<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							DS extends GeoJsfDataSource<L,D,G,GT,GS,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,DS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
							SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
							SLD extends GeoJsfSld<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>,
							RULE extends GeoJsfSldRule<L,D,G,GT,GS,SLDTEMPLATE,SLDTYPE,SLD,RULE>> 
{
	final static Logger logger = LoggerFactory.getLogger(WfsPointQuery.class);
	
	private LAYER layer;
	private WfsGetFeaturePropertyProvider propertyProvider;
	private UtilsIdFacade fGeo;
	
	private Namespace nsGml;
	
	private Class<I> cId;
	
	private String geometryColumn;
	private String[] queryProperties;
		
	public WfsPointQuery(UtilsIdFacade fGeo, WfsGetFeaturePropertyProvider propertyProvider, LAYER layer, Class<W> cGeometry,Class<I> cId)
	{
		this.fGeo=fGeo;
		this.propertyProvider=propertyProvider;
		this.layer=layer;
		this.cId=cId;
		
		logger.info("Using URL:"+layer.getService().getWms()+" with layer:"+layer);
		
		nsGml = Namespace.getNamespace("gml", "http://www.opengis.net/gml");
		geometryColumn = getGeometryColumnName(cGeometry);
		queryProperties = getPropertyColumnNames(cGeometry);
	}
	
	protected String getGeometryColumnName(Class<W> clazz)
	{
		Field geometryField = getGeometryfield(clazz);
		
		Annotation annotation = geometryField.getAnnotation(Column.class);
		if(annotation==null){throw new GeoJsfDeveloperException("Field \"geometry\" in "+clazz.getName()+" does not has the javax.persistence.Column annotation");}
		
		Column column = (Column)annotation;
		return column.name();
	}
	
	private Field getGeometryfield(Class<?> clazz)
	{
		Field geometryField=null;
		
		for(Field field : clazz.getDeclaredFields())
		{
			if(field.getName().equals("geometry"))
			{
				geometryField = field;
				break;
			}
		}
		if(geometryField==null)
		{
			if(clazz.getSuperclass().getSimpleName().equals("Object"))
			{
				throw new GeoJsfDeveloperException("Class "+clazz.getName()+" does not provide a field \"geometry\"");
			}
			else
			{
				geometryField = getGeometryfield(clazz.getSuperclass());
			}
			
		}
		return geometryField;
	}
	
	protected String[] getPropertyColumnNames(Class<W> clazz)
	{
		List<String> propertyFields = new ArrayList<String>();
	
		for(Field field : clazz.getDeclaredFields())
		{
			if(!field.getName().equals("geometry") && !field.getName().equals("id") && !Modifier.isStatic(field.getModifiers()))
			{
				if(field.getAnnotation(IgnoreGeoJsfWfsProperty.class)==null)
				{
					boolean fieldAdded = false;
					if(field.getAnnotation(Column.class)!=null)
					{
						Column column = (Column)field.getAnnotation(Column.class);
						propertyFields.add(column.name());
						fieldAdded = true;
					}
					if(field.getAnnotation(JoinColumn.class)!=null)
					{
						JoinColumn column = (JoinColumn)field.getAnnotation(JoinColumn.class);
						propertyFields.add(column.name());
						fieldAdded = true;
					}
					if(!fieldAdded && field.getAnnotation(ManyToOne.class)!=null)
					{
						propertyFields.add(field.getName()+"_id");
						fieldAdded = true;
					}
					if(!fieldAdded){propertyFields.add(field.getName());}
				}
				
				
			}
		}
		String[] result = new String[propertyFields.size()];
		propertyFields.toArray(result);
		
		return result;
	}
	
	public List<I> execute(Coordinate coordinate, Distance distance)
	{
		return execute(XmlCoordinatesFactory.build(coordinate),distance);
	}
	
	public List<I> execute(Coordinates coordinates, Distance distance)
	{			
		if(logger.isTraceEnabled())
		{
			for(String s : queryProperties){logger.trace(s.toString());}
		}
		
		GetFeature gf = PointQueryFactory.cGetFeature(propertyProvider.getWorkspace()+":"+layer.getCode(),
													  queryProperties, geometryColumn,
													  coordinates,distance);
		JaxbUtil.info(gf);
		WfsHttpRequest r = new WfsHttpRequest(layer.getService().getWcs());
		
		Document doc = r.request(gf);

		Namespace nsQuery = propertyProvider.getNameSpace();
		StringBuffer xpath = new StringBuffer();
		xpath.append("//gml:featureMember");
		xpath.append("/").append(nsQuery.getPrefix()).append(":").append(layer.getCode());
		
		logger.info("XPATH: "+xpath.toString());
		XPathExpression<Element> xpe = XPathFactory.instance().compile(xpath.toString(),Filters.element(), null,nsQuery,nsGml);
		List<Element> elements = xpe.evaluate(doc);
		logger.info("Elements: "+elements.size());
		
		JDomUtil.debug(doc);
		
		results = new ArrayList<I>();
		Set<Long> ids = new HashSet<Long>();
		for (Element e : elements)
		{	
			try
			{
				String s = e.getAttributeValue("fid");
				Long id = new Long(s.substring(s.lastIndexOf(".")+1));
				if(!ids.contains(id)){results.add(fGeo.find(cId, id));}
				ids.add(id);
			}
			catch (UtilsNotFoundException ex)
			{
				logger.error(ex.getMessage());
			}
		}
		return results;
	}
	
	public List<I> sqlLayer(Coordinates coordinates, Distance distance,String[] queryProperties,String viewParams)
	{				
		GetFeature gf = PointQueryFactory.cGetFeature(propertyProvider.getWorkspace()+":"+layer.getCode(),
													  queryProperties, geometryColumn,
													  coordinates,distance);
		gf.setViewParams(viewParams);
		
//		JaxbUtil.info(gf);
		WfsHttpRequest r = new WfsHttpRequest(layer.getService().getWcs());
		
		Document doc = r.request(gf);
//		JDomUtil.debug(doc);
		
		Namespace nsQuery = propertyProvider.getNameSpace();
		StringBuffer xpath = new StringBuffer();
		xpath.append("//gml:featureMember");
		xpath.append("/").append(nsQuery.getPrefix()).append(":").append(layer.getCode());
		
		logger.trace("XPATH: "+xpath.toString());
		XPathExpression<Element> xpe = XPathFactory.instance().compile(xpath.toString(),Filters.element(), null,nsQuery,nsGml);
		List<Element> elements = xpe.evaluate(doc);
		logger.trace("Elements: "+elements.size());
		
		results = new ArrayList<I>();
		for (Element e : elements)
		{	
			try
			{
				String s = e.getAttributeValue("fid");
				Long id = new Long(s.substring(s.lastIndexOf(".")+1));
				results.add(fGeo.find(cId, id));
			}
			catch (UtilsNotFoundException ex)
			{
				logger.error(ex.getMessage());
			}
		}
		return results;
	}
	
	//ResultList
	private List<I> results;
	public List<I> getResults(){return results;}
	
}