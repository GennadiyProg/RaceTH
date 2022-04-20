package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.tmpstorage.TmpStorage;

import java.util.List;

public class CompetitionDAO implements DAO<Competition> {
//    private final Session session;
//
//    public CompetitionDAO(@NotNull final Session session) {
//        this.session = session;
//    }

//    public void Create(@NotNull final Competition competition) {
//
//        session.beginTransaction();
//
//        session.save(competition);
//
//        session.getTransaction().commit();
//
//    }

    public void create(Competition competition) {
        TmpStorage.competitions.add(competition);
    }

    public void update(Competition competition) {
        TmpStorage.competitions.set(TmpStorage.competitions.indexOf(
                TmpStorage.competitions.stream().filter(
                        g -> g.getId() == competition.getId()
                ).findFirst().orElse(null)
        ), competition);
    }

    public Competition getCompetition(long id) {
        for (Competition item : TmpStorage.competitions) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Competition> getAllCompetitions() {
        return TmpStorage.competitions;
    }

    public void delete(Competition competition) {
        TmpStorage.competitions.remove(competition);
    }
}
