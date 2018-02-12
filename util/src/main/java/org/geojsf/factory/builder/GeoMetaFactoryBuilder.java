package org.geojsf.factory.builder;

import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class GeoMetaFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								DS extends GeoJsfDataSource<L,D,?>

										>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(GeoMetaFactoryBuilder.class);
	
	private final Class<DS> cDs; public Class<DS> getClassDs() {return cDs;}

	
	public GeoMetaFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<DS> cDs)
	{
		super(cL,cD);
		this.cDs=cDs;
	}
}