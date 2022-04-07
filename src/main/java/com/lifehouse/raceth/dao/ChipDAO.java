package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Chip;
import org.hibernate.Session;

public class ChipDAO implements DAO<Chip> {
    private final Session session;

    public ChipDAO(final Session session) {
        this.session = session;
    }

    public void Create(Chip chip) {
        session.beginTransaction();

        session.save(chip);

        session.getTransaction().commit();
    }
}
