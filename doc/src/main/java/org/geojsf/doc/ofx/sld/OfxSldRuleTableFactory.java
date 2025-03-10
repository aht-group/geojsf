package org.geojsf.doc.ofx.sld;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.io.FileUtils;
import org.exlp.interfaces.system.property.Configuration;
import org.geojsf.doc.GeoJsfDocumentation;
import org.geojsf.factory.txt.TxtSldRuleFactory;
import org.geojsf.model.xml.geojsf.Sld;
import org.geojsf.model.xml.geojsf.SldRule;
import org.jeesl.doc.ofx.OfxMultiLangFactory;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.factory.svg.SvgSymbolFactory;
import org.jeesl.model.xml.io.locale.status.Translations;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.factory.xml.layout.XmlHeightFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.table.XmlCellFactory;
import org.openfuxml.factory.xml.table.XmlColumnFactory;
import org.openfuxml.media.transcode.Svg2SvgTranscoder;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.model.xml.core.media.Media;
import org.openfuxml.model.xml.core.ofx.Comment;
import org.openfuxml.model.xml.core.table.Body;
import org.openfuxml.model.xml.core.table.Cell;
import org.openfuxml.model.xml.core.table.Columns;
import org.openfuxml.model.xml.core.table.Content;
import org.openfuxml.model.xml.core.table.Head;
import org.openfuxml.model.xml.core.table.Row;
import org.openfuxml.model.xml.core.table.Specification;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.exlp.util.io.HashUtil;

public class OfxSldRuleTableFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxSldRuleTableFactory.class);
	
	private static String keyCaption = "geojsfTableLayerCaptionPrefix";
	private List<String> headerKeys;
	
	File fResources;
	private String imagePathPrefix;
	private String imagePath;
	
	public OfxSldRuleTableFactory(Configuration config,String[] langs,Translations translations)
	{
		super(config,langs,translations);
		
		fResources = new File(config.getString("doc.ofx.imagePathResourcesPrefix"));
		imagePathPrefix = config.getString("doc.ofx.imagePathPrefix");
		imagePath = config.getString("doc.ofx.imagePathGeoJsfSldRule");
		
		
		headerKeys = new ArrayList<String>();
		headerKeys.add("geojsfSldRuleTableHeaderSymbol");
		headerKeys.add("geojsfSldRuleTableHeaderBounds");
		headerKeys.add("geojsfSldRuleTableHeaderLabel");
	}
	
	public Table build(String id, Sld sld, boolean withComment) throws OfxAuthoringException, UtilsConfigurationException
	{	
		Table table = toOfx(sld.getSldRule());
		table.setId(id);
		
		if(withComment)
		{
			Comment comment = XmlCommentFactory.build();
			OfxCommentBuilder.fixedId(comment, id);
			DocumentationCommentBuilder.translationKeys(comment,config,GeoJsfDocumentation.keyTranslationFile);
			DocumentationCommentBuilder.tableHeaders(comment,headerKeys);
			DocumentationCommentBuilder.tableKey(comment,keyCaption,"Table Caption");
			OfxCommentBuilder.doNotModify(comment);
			table.setComment(comment);
		}
//		table.setTitle(OfxMultiLangFactory.title(keys, langs));
			
		return table;
	}
	
	private Table toOfx(List<SldRule> rules) throws OfxAuthoringException
	{
		Table table = new Table();
		table.setSpecification(createSpecifications());
		
		try{table.setContent(createContent(rules));}
		catch (IOException e) {e.printStackTrace();}
		catch (TranscoderException e) {e.printStackTrace();}
		
		return table;
	}
	
	private Specification createSpecifications()
	{
		Specification specification = new Specification();
		specification.setFloat(XmlFloatFactory.build(false));
		
		Columns cols = new Columns();
//		cols.getColumn().add(OfxColumnFactory.percentage(30));
//		cols.getColumn().add(OfxColumnFactory.flex(100));
		cols.getColumn().add(XmlColumnFactory.flex(3,true));
		cols.getColumn().add(XmlColumnFactory.flex(22,true));
		cols.getColumn().add(XmlColumnFactory.flex(75));
		specification.setColumns(cols);
		
		return specification;
	}
	
	private Content createContent(List<SldRule> rules) throws IOException, TranscoderException
	{	
		Head head = new Head();
		head.getRow().add(this.createHeaderRow(headerKeys));
		
		Body body = new Body();
		
		for(SldRule rule : rules)
		{
			body.getRow().add(createRow(rule));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		content.setHead(head);
		
		return content;
	}
	
	private Row createRow(SldRule rule) throws IOException, TranscoderException
	{		
		Row row = new Row();
		
		row.getCell().add(cellImage(rule));
		row.getCell().add(XmlCellFactory.createParagraphCell(""+TxtSldRuleFactory.bounds(rule)));
		
		Cell cell = XmlCellFactory.build();
		cell.getContent().addAll(OfxMultiLangFactory.paragraph(langs, rule.getLangs()));
		row.getCell().add(cell);		

		return row;
	}
	
	private Cell cellImage(SldRule rule) throws IOException, TranscoderException
	{		
		StringBuffer sbImage = new StringBuffer();
		sbImage.append(imagePath).append("/").append(rule.getId());
		
		StringBuffer sbSrc = new StringBuffer();
		sbSrc.append(imagePathPrefix).append("/").append(sbImage.toString()).append(".svg");
		File fSvg = new File(fResources,sbSrc.toString());
		
		SVGGraphics2D g = SvgSymbolFactory.build(12, rule.getGraphic().getSymbol());
		byte[] bytes = Svg2SvgTranscoder.transcode(g);
		
		boolean writeFile = true;
		if(fSvg.exists())
		{
			String oldHash = HashUtil.hash(fSvg);
			String newHash = HashUtil.hash(bytes);
			if(oldHash.equals(newHash)){writeFile=false;}
		}
		
		if(writeFile)
		{
			logger.info("Writing to :"+fSvg);
			FileUtils.writeByteArrayToFile(fSvg, bytes);
		}
		
		Media media = new Media();
		media.setSrc(sbSrc.toString());
		media.setDst(sbImage.toString());
		
		Image image = new Image();
		image.setMedia(media);
		image.setHeight(XmlHeightFactory.em(1));
		
		return XmlCellFactory.image(image);
	}
}