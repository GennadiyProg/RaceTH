package ru.corporateproduct.raceth.dao;

import com.sun.istack.NotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.corporateproduct.raceth.model.Competition;

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
