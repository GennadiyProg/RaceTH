package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import com.lifehouse.raceth.tmpstorage.TmpStorage;

import java.util.ArrayList;
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

    public Participant getParticipant(long id) {
        for (Participant item : TmpStorage.participants) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Participant> getAllParticipants() {
        return TmpStorage.participants;
    }

    public List<ParticipantCompetitionView> getAllParticipantViews(){
        List<Participant> participants = getAllParticipants();
        return  participants.stream().map(ParticipantCompetitionView::convertToView).toList();
    }

    public void delete(Participant participant) {
        TmpStorage.participants.remove(participant);
    }
}
