package ru.corporateproduct.raceth.dao;

import org.hibernate.Session;
import ru.corporateproduct.raceth.model.Sportsman;

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
