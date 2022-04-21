package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.tmpstorage.TmpStorage;

import java.util.List;

public class ParticipantDAO implements DAO<Participant> {
//    private final Session session;
//
//    public ParticipantDAO(final Session session) {
//        this.session = session;
//    }

//    public void Create(Participant participant) {
//        session.beginTransaction();
//
//        session.save(participant);
//
//        session.getTransaction().commit();
//    }

    public void create(Participant participant) {
        TmpStorage.participants.add(participant);
    }

    public Participant GetParticipant(long id) {
        for (Participant item : TmpStorage.participants) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Participant> GetAllParticipants() {
        return TmpStorage.participants;
    }

    public void Delete(Participant participant) {
        TmpStorage.participants.remove(participant);
    }
}
