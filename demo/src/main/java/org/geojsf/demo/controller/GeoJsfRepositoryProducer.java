package org.geojsf.demo.controller;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GeoJsfRepositoryProducer
{
    @Produces
    @GeoJsfRepository
    @PersistenceContext
    private EntityManager em;
}
