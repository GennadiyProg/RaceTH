package ru.corporateproduct.raceth.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.corporateproduct.raceth.model.Participant;

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
