package org.geojsf.factory.builder;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class GeoSldFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,?,?,?>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
								SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
								RULE extends GeoJsfSldRule<L,D,G>
										>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoSldFactoryBuilder.class);
	
	private final Class<SLDTEMPLATE> cTemplate; public Class<SLDTEMPLATE> getClassTemplate(){return cTemplate;}
	private final Class<SLDTYPE> cSldType; public Class<SLDTYPE> getClassSldType(){return cSldType;}
	private final Class<SLD> cSld; public Class<SLD> getClassSld(){return cSld;}
	
	public GeoSldFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<SLDTEMPLATE> cTemplate, final Class<SLDTYPE> cSldType, final Class<SLD> cSld)
	{
		super(cL,cD);
		this.cTemplate=cTemplate;
		this.cSldType=cSldType;
		this.cSld=cSld;
	}
}