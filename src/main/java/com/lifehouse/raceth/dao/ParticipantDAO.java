package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import com.lifehouse.raceth.model.view.ParticipantStartView;
import com.lifehouse.raceth.repository.ParticipantRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParticipantDAO {
    private final ParticipantRepository participantRepository;

    public ParticipantDAO() {
        this.participantRepository = (ParticipantRepository) Main.appContext.getBean("participantRepository");
    }

    public void create(Participant participant) {
        participantRepository.save(participant);
    }

    public Participant getParticipant(long id) {
        return participantRepository.findById(id).orElse(null);
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public List<ParticipantCompetitionView> getAllParticipantViews(){
        return  participantRepository.findAll().stream().map(ParticipantCompetitionView::convertToView).toList();
    }

    public List<ParticipantStartView> getAllParticipantViewsByStarts(List<Long> startsId){
        return  participantRepository.findAllByStartsId(startsId).stream()
                .map(ParticipantStartView::convertToView).toList();
    }

    public void delete(Participant participant) {
        participantRepository.delete(participant);
    }
}
