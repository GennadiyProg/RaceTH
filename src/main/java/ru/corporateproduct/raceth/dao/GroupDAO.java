package ru.corporateproduct.raceth.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.corporateproduct.raceth.model.CompetitionGroup;

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
