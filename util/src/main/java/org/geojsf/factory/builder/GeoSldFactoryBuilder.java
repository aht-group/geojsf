package org.geojsf.factory.builder;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class GeoSldFactoryBuilder<L extends UtilsLang, D extends UtilsDescription
/*								CATEGORY extends GeoJsfCategory<L,D,LAYER>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,?,?>,
								MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
								SCALE extends GeoJsfScale<L,D>,
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								VP extends GeoJsfViewPort
*/
										>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoSldFactoryBuilder.class);
	
//	protected final Class<CATEGORY> cCategory;

	
	public GeoSldFactoryBuilder(final Class<L> cL, final Class<D> cD)//, final Class<CATEGORY> cCategory)
	{
		super(cL,cD);
	}
}