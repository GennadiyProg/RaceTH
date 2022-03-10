package ru.corporateproduct.raceth.dao;

import com.sun.istack.NotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.corporateproduct.raceth.model.Run;


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
