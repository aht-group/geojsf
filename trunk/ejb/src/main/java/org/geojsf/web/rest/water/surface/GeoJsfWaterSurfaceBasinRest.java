package org.geojsf.web.rest.water.surface;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Aht;

import org.geojsf.interfaces.rest.water.surface.GeoJsfWaterSurfaceBasinExportRest;
import org.geojsf.web.rest.AbstractGeoJsfRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfWaterSurfaceBasinRest <L extends UtilsLang,D extends UtilsDescription,MODEL extends UtilsStatus<MODEL,L,D>>
	extends AbstractGeoJsfRest<L,D>
	implements GeoJsfWaterSurfaceBasinExportRest
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfWaterSurfaceBasinRest.class);

	private final Class<MODEL> cM;
	
	public GeoJsfWaterSurfaceBasinRest(UtilsFacade fUtils, final String[] defaultLangs, final Class<L> cL, final Class<D> cD, final Class<MODEL> cM)
	{
		super(fUtils,defaultLangs,cL,cD);
		this.cM=cM;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,MODEL extends UtilsStatus<MODEL,L,D>>
		GeoJsfWaterSurfaceBasinRest<L,D,MODEL>
		factory(UtilsFacade fGeoMonitoring, final String[] defaultLangs, final Class<L> cL, final Class<D> cD,final Class<MODEL> cM)
	{
		return new GeoJsfWaterSurfaceBasinRest<L,D,MODEL>(fGeoMonitoring,defaultLangs,cL,cD,cM);
	}

	//Export
	@Override public Aht exportGeoJsfWaterSurfaceBasinModel(){return exportStatus(cM,"");}

}