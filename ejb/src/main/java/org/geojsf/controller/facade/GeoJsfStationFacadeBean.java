package org.geojsf.controller.facade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.geojsf.interfaces.facade.GeoJsfStationFacade;
import org.geojsf.interfaces.model.obervation.station.GeoStation;
import org.geojsf.interfaces.model.obervation.station.GeoStationCapability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class GeoJsfStationFacadeBean <L extends UtilsLang, D extends UtilsDescription,
										STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>,
										TYPE extends UtilsStatus<TYPE,L,D>, SUBTYPE extends UtilsStatus<SUBTYPE,L,D>,
										SCHEME extends UtilsStatus<SCHEME,L,D>,
										CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>,
										CAPT extends UtilsStatus<CAPT,L,D>,
										CAPS extends UtilsStatus<CAPS,L,D>>
				extends UtilsFacadeBean
				implements GeoJsfStationFacade<L,D,STATION,TYPE,SUBTYPE,SCHEME,CAP,CAPT,CAPS>
{	
	final static Logger logger = LoggerFactory.getLogger(GeoJsfStationFacadeBean.class);
	
	private final Class<STATION> cStation;
	
	public GeoJsfStationFacadeBean(EntityManager em, final Class<STATION> cStation)
	{
		super(em);
		this.cStation=cStation;
	}
	
	@Override public STATION load(STATION station)
	{
		station = em.find(cStation, station.getId());
		station.getCapabilities().size();
		return station;
	}

	@Override public List<STATION> fStations(CAPT capabilityType)
	{
		List<CAPT> capabilityTypes = new ArrayList<CAPT>();
		capabilityTypes.add(capabilityType);
		return fStations(capabilityTypes);
	}

	@Override public List<STATION> fStations(List<CAPT> capabilityTypes)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<STATION> cQ = cB.createQuery(cStation);
		Root<STATION> station = cQ.from(cStation);
		
		ListJoin<STATION,CAP> jCapability = station.joinList(GeoStation.Attributes.capabilities.toString());
		Join<CAP,CAPT> jCapType = jCapability.join(GeoStationCapability.Attributes.type.toString());
		predicates.add(jCapType.in(capabilityTypes));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(station);
		
		return em.createQuery(cQ).getResultList();
	}
}