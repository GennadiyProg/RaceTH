package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.repository.CompetitionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompetitionDAO {
    private final CompetitionRepository competitionRepository;

    public CompetitionDAO() {
        this.competitionRepository = (CompetitionRepository) Main.appContext.getBean("competitionRepository");
    }

    public void create(Competition competition) {
        competitionRepository.save(competition);
    }

    public void update(Competition competition) {
        competitionRepository.save(competition);
    }

    public Competition getCompetition(long id) {
        return competitionRepository.findById(id).orElse(null);
    }

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public void delete(Competition competition) {
        competitionRepository.delete(competition);
    }
}
