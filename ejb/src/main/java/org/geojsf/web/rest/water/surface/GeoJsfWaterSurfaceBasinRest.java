package org.geojsf.web.rest.water.surface;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;

import org.geojsf.factory.ejb.area.EjbBasinFactory;
import org.geojsf.interfaces.model.water.GeoBasin;
import org.geojsf.interfaces.rest.water.surface.GeoJsfWaterSurfaceBasinRestExport;
import org.geojsf.interfaces.rest.water.surface.GeoJsfWaterSurfaceBasinRestImport;
import org.geojsf.model.xml.area.Basin;
import org.geojsf.model.xml.area.Areas;
import org.geojsf.web.rest.AbstractGeoJsfRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfWaterSurfaceBasinRest <L extends UtilsLang,D extends UtilsDescription,BASIN extends GeoBasin<L,D,BASIN,MODEL>,MODEL extends UtilsStatus<MODEL,L,D>>
	extends AbstractGeoJsfRest<L,D>
	implements GeoJsfWaterSurfaceBasinRestExport,GeoJsfWaterSurfaceBasinRestImport
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfWaterSurfaceBasinRest.class);

	private final Class<MODEL> cM;
	private final Class<BASIN> cB;
	
	private EjbBasinFactory<L,D,BASIN,MODEL> efBasin;
	
	public GeoJsfWaterSurfaceBasinRest(UtilsFacade fUtils, final String[] defaultLangs, final Class<L> cL, final Class<D> cD, final Class<BASIN> cB, final Class<MODEL> cM)
	{
		super(fUtils,defaultLangs,cL,cD);
		this.cB=cB;
		this.cM=cM;
		efBasin = EjbBasinFactory.factory(cL, cD, cB);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,BASIN extends GeoBasin<L,D,BASIN,MODEL>,MODEL extends UtilsStatus<MODEL,L,D>>
		GeoJsfWaterSurfaceBasinRest<L,D,BASIN,MODEL>
		factory(UtilsFacade fGeoMonitoring, final String[] defaultLangs, final Class<L> cL, final Class<D> cD,final Class<BASIN> cB,final Class<MODEL> cM)
	{
		return new GeoJsfWaterSurfaceBasinRest<L,D,BASIN,MODEL>(fGeoMonitoring,defaultLangs,cL,cD,cB,cM);
	}

	//Export
	@Override public Aht exportGeoJsfWaterSurfaceBasinModel(){return exportStatus(cM);}

	@Override
	public Areas exportGeoJsfWaterSurfaceBasins()
	{
		logger.warn("NYI");
		return null;
	}

	
	//Import
	@Override public DataUpdate importGeoJsfWaterSurfaceBasinModel(Aht models) {return importStatus(cM,null,models);}

	@Override public DataUpdate importGeoJsfWaterSurfaceBasins(Areas basins)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cB.getName(),"DB Import"));
		for(Basin basin : basins.getBasin())
		{
			try
			{
				MODEL model = fUtils.fByCode(cM,basin.getModel().getCode());
				BASIN ejb = efBasin.build(basin, model);
				ejb = fUtils.persist(ejb);
				efLang.persistMissingLangs(fUtils,defaultLangs,ejb);
				efDescription.persistMissingLangs(fUtils,defaultLangs,ejb);
				dut.success();
				
			}
			catch (UtilsNotFoundException e){{dut.fail(e,true);}}
			catch (UtilsConstraintViolationException e) {dut.fail(e,true);}
		}
		return dut.toDataUpdate();
	}
}