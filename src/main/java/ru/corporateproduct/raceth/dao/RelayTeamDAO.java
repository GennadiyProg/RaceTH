package ru.corporateproduct.raceth.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.corporateproduct.raceth.model.RelayTeam;

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
