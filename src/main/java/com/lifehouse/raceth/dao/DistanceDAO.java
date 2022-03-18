package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Distance;
import org.hibernate.Session;

public class DistanceDAO implements DAO<Distance> {
    private final Session session;

    public DistanceDAO(final Session session) {
        this.session = session;
    }

    public void create(Distance distance) {

        session.beginTransaction();

        session.save(distance);

        session.getTransaction().commit();
    }
}
