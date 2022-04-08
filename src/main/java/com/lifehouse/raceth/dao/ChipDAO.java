package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Chip;
import org.hibernate.Session;

public class ChipDAO implements DAO<Chip> {
    @Override
    public void create(Chip chip) {

    }
//    private final Session session;
//
//    public ChipDAO(final Session session) {
//        this.session = session;
//    }

//    public void create(Chip chip) {
//        session.beginTransaction();
//
//        session.save(chip);
//
//        session.getTransaction().commit();
//    }
}
