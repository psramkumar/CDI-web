package com.packtpub.pf.blueprint.persistence;

import com.packtpub.pf.blueprint.bean.GenericDAO;
import com.packtpub.pf.blueprint.model.Location;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Created by psramkumar on 4/21/14.
 */
@Named
@Singleton
public class LocationDAO extends GenericDAO implements Serializable {

    @Inject
    private EntityManager em;

    public List<Location> resultSet() {
        Query query = em.createQuery("select l from Location l");
        return query.getResultList();
    }


}
