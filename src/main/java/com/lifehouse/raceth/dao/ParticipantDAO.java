package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Participant;
import org.hibernate.Session;

public class ParticipantDAO implements DAO<Participant> {
    private final Session session;

    public ParticipantDAO(final Session session) {
        this.session = session;
    }

    public void create(Participant participant) {
        session.beginTransaction();

        session.save(participant);

        session.getTransaction().commit();
    }
}
