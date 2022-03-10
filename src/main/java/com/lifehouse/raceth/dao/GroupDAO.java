package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.CompetitionGroup;
import org.hibernate.Session;

public class GroupDAO implements DAO<CompetitionGroup> {
    private final Session session;

    public GroupDAO(final Session session) {
        this.session = session;
    }

    public void create(CompetitionGroup group) {

        session.beginTransaction();

        session.save(group);

        session.getTransaction().commit();
    }
}
