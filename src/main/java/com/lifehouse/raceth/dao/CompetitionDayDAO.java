package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.CompetitionDay;
import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.repository.CompetitionDayRepository;
import com.lifehouse.raceth.repository.CompetitionRepository;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompetitionDayDAO implements DAO<CompetitionDay> {
    private final CompetitionDayRepository competitionDayRepository;

    public CompetitionDayDAO() {
        this.competitionDayRepository = (CompetitionDayRepository) Main.appContext.getBean("competitionDayRepository");
    }

    public void create(CompetitionDay competitionDay) {
        competitionDayRepository.save(competitionDay);
    }

    public List<CompetitionDay> getAllByCompetition(long competitionId) {
        return competitionDayRepository.findAllByCompetitionId(competitionId);
    }

    public void update(CompetitionDay competitionDay) {
        competitionDayRepository.update(competitionDay.getId(), competitionDay);
    }

    public CompetitionDay getCompetitionDay(long id) {
        return competitionDayRepository.findById(id).orElse(null);
    }

    public void deleteByCompetitionId(long competitionId){
        competitionDayRepository.deleteByCompetitionId(competitionId);
    }

    public void delete(CompetitionDay competitionDay) {
        competitionDayRepository.delete(competitionDay);
    }
}
