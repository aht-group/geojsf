package org.geojsf.web.rest.area;

import org.geojsf.api.rest.area.basin.surface.GeoJsfAreaBasinSurfaceRestExport;
import org.geojsf.api.rest.area.basin.surface.GeoJsfAreaBasinSurfaceRestImport;
import org.geojsf.factory.ejb.area.EjbBasinFactory;
import org.geojsf.interfaces.model.domain.area.GeoBasin;
import org.geojsf.model.xml.area.Areas;
import org.geojsf.model.xml.area.Basin;
import org.jeesl.controller.monitoring.counter.DataUpdateTracker;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.xml.io.locale.status.XmlTypeFactory;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.rest.AbstractUtilsRest;
import org.jeesl.model.xml.io.ssi.sync.DataUpdate;

public class GeoJsfAreaBasinRest <L extends JeeslLang,D extends JeeslDescription,BASIN extends GeoBasin<L,D,BASIN,MODEL>,MODEL extends JeeslStatus<L,D,MODEL>>
	extends AbstractUtilsRest<L,D>
	implements GeoJsfAreaBasinSurfaceRestExport,GeoJsfAreaBasinSurfaceRestImport
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfAreaBasinRest.class);

	private final Class<MODEL> cM;
	private final Class<BASIN> cB;
	
	private EjbBasinFactory<L,D,BASIN,MODEL> efBasin;
	
	public GeoJsfAreaBasinRest(JeeslFacade fUtils, final String[] defaultLangs, final Class<L> cL, final Class<D> cD, final Class<BASIN> cB, final Class<MODEL> cM)
	{
		super(fUtils,defaultLangs,cL,cD);
		this.cB=cB;
		this.cM=cM;
		efBasin = EjbBasinFactory.factory(cL, cD, cB);
	}
	
	public static <L extends JeeslLang,D extends JeeslDescription,BASIN extends GeoBasin<L,D,BASIN,MODEL>,MODEL extends JeeslStatus<L,D,MODEL>>
		GeoJsfAreaBasinRest<L,D,BASIN,MODEL>
		factory(JeeslFacade fGeoMonitoring, final String[] defaultLangs, final Class<L> cL, final Class<D> cD,final Class<BASIN> cB,final Class<MODEL> cM)
	{
		return new GeoJsfAreaBasinRest<L,D,BASIN,MODEL>(fGeoMonitoring,defaultLangs,cL,cD,cB,cM);
	}

	//Model
	
	
	
	//Surface
	@Override public DataUpdate importGeoJsfAreaBasinSurface(Areas basins){return importBasins(basins);}
	@Override public Areas exportGeoJsfWaterSurfaceBasins() {return exportBasins();}
	
	
	//Implementation
	private Areas exportBasins()
	{
		logger.warn("NYI");
		return null;
	}

	private DataUpdate importBasins(Areas basins)
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
			catch (JeeslNotFoundException e){{dut.fail(e,true);}}
			catch (JeeslConstraintViolationException e) {dut.fail(e,true);}
		}
		return dut.toDataUpdate();
	}
}