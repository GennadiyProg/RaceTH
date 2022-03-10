package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.RelayTeam;
import org.hibernate.Session;

public class RelayTeamDAO implements DAO<RelayTeam> {
    private final Session session;

    public RelayTeamDAO(final Session session) {
        this.session = session;
    }

    public void create(RelayTeam relayTeam) {
        session.beginTransaction();

        session.save(relayTeam);

        session.getTransaction().commit();
    }
}
