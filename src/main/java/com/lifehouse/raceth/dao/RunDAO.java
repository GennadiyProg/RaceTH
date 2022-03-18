package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Run;
import com.sun.istack.NotNull;
import org.hibernate.Session;


public class RunDAO implements DAO<Run> {
    private final Session session;

    public RunDAO(@NotNull final Session session) {
        this.session = session;
    }

    @Override
    public void create(@NotNull final Run run) {
        session.beginTransaction();

        session.save(run);

        session.getTransaction().commit();
    }
}
