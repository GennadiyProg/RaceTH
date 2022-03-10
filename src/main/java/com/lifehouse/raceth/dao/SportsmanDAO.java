package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Sportsman;
import org.hibernate.Session;

public class SportsmanDAO implements DAO<Sportsman> {
    private final Session session;

    public SportsmanDAO(final Session session) {
        this.session = session;
    }

    public void create(Sportsman sportsman) {
        session.beginTransaction();

        session.save(sportsman);

        session.getTransaction().commit();
    }
}
