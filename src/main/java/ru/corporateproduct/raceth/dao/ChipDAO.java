package ru.corporateproduct.raceth.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.corporateproduct.raceth.model.Chip;

public class ChipDAO implements DAO<Chip> {
    private final Session session;

    public ChipDAO(final Session session) {
        this.session = session;
    }

    public void create(Chip chip) {
        session.beginTransaction();

        session.save(chip);

        session.getTransaction().commit();
    }
}
