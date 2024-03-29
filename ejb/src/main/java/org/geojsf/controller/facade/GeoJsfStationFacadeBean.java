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

import org.geojsf.api.facade.GeoJsfStationFacade;
import org.geojsf.interfaces.model.domain.station.GeoStation;
import org.geojsf.interfaces.model.domain.station.GeoStationCapability;
import org.geojsf.interfaces.model.domain.station.GeoStationCode;
import org.jeesl.controller.facade.jx.JeeslFacadeBean;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfStationFacadeBean <L extends JeeslLang, D extends JeeslDescription,
										STATION extends GeoStation<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										TYPE extends JeeslStatus<L,D,TYPE>, SUBTYPE extends JeeslStatus<L,D,SUBTYPE>,
										SCHEME extends JeeslStatus<L,D,SCHEME>,
										CODE extends GeoStationCode<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAP extends GeoStationCapability<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>,
										CAPT extends JeeslStatus<L,D,CAPT>,
										CAPS extends JeeslStatus<L,D,CAPS>>
				extends JeeslFacadeBean
				implements GeoJsfStationFacade<L,D,STATION,TYPE,SUBTYPE,SCHEME,CODE,CAP,CAPT,CAPS>
{	
	private static final long serialVersionUID = 1L;

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