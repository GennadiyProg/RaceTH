package ru.corporateproduct.raceth.dao;
import com.sun.istack.NotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.corporateproduct.raceth.model.Test;

public class TestDAO implements DAO<Test> {
    private final Session session;

    public TestDAO(@NotNull final Session session) {
        this.session = session;
    }

    /**
     * Creating a new Test in a Tests Table
     *
     * @param test for add
     */
    @Override
    public void create(@NotNull final Test test) {
        session.beginTransaction();

        session.save(test);

        session.getTransaction().commit();
    }
}
