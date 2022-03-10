package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Competition;
import com.sun.istack.NotNull;
import org.hibernate.Session;

public class CompetitionDAO implements DAO<Competition> {
    private final Session session;

    public CompetitionDAO(@NotNull final Session session) {
        this.session = session;
    }

    @Override
    public void create(@NotNull final Competition competition) {

        session.beginTransaction();

        session.save(competition);

        session.getTransaction().commit();

    }
}
