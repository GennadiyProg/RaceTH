package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.CompetitionDay;
import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.tmpstorage.TmpStorage;

import java.util.List;

public class CompetitionDayDAO implements DAO<CompetitionDay> {
    //    private final Session session;
//
//    public CompetitionDayDAO(@NotNull final Session session) {
//        this.session = session;
//    }

//    public void Create(@NotNull final CompetitionDay competitionDay) {
//
//        session.beginTransaction();
//
//        session.save(competitionDay);
//
//        session.getTransaction().commit();
//
//    }

    public void create(CompetitionDay competitionDay) {
        competitionDay.setId(TmpStorage.competitionDays.size());
        TmpStorage.competitionDays.add(competitionDay);
    }

    public void update(CompetitionDay competitionDay) {
        TmpStorage.competitionDays.set(TmpStorage.competitionDays.indexOf(
                TmpStorage.competitionDays.stream().filter(
                        g -> g.getId() == competitionDay.getId()
                ).findFirst().orElse(null)
        ), competitionDay);
    }

    public CompetitionDay getCompetitionDay(long id) {
        for (CompetitionDay item : TmpStorage.competitionDays) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<CompetitionDay> getAllCompetitionDays() {
        return TmpStorage.competitionDays;
    }

    public void delete(CompetitionDay competitionDay) {
        TmpStorage.competitionDays.remove(competitionDay);
    }
}
