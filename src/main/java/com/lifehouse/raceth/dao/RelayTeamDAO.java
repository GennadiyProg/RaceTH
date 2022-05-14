package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.RelayTeam;
import com.lifehouse.raceth.repository.RelayTeamRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RelayTeamDAO {
    private final RelayTeamRepository relayTeamRepository;

    public RelayTeamDAO() {
        this.relayTeamRepository = (RelayTeamRepository) Main.appContext.getBean("relayTeamRepository");
    }

    public void create(RelayTeam relayTeam) {
        relayTeamRepository.save(relayTeam);
    }

    public RelayTeam getRelayTeam(long id) {
        return relayTeamRepository.findById(id).orElse(null);
    }

    public List<RelayTeam> getAllTeams() {
        return relayTeamRepository.findAll();
    }

    public void delete(RelayTeam relayTeam) {
        relayTeamRepository.delete(relayTeam);
    }

    public void update(RelayTeam relayTeam) {
        relayTeamRepository.update(relayTeam.getId(), relayTeam);
    }
}
