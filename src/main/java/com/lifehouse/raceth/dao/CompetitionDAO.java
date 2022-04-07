package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.model.Competition;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import com.sun.istack.NotNull;
import org.hibernate.Session;

import java.util.List;

public class CompetitionDAO implements DAO<Competition> {
    private final Session session;

    public CompetitionDAO(@NotNull final Session session) {
        this.session = session;
    }

//    public void Create(@NotNull final Competition competition) {
//
//        session.beginTransaction();
//
//        session.save(competition);
//
//        session.getTransaction().commit();
//
//    }

    public void Create(Competition competition) {
        TmpStorage.competitions.add(competition);
    }

    public Competition GetCompetition(long id) {
        for (Competition item : TmpStorage.competitions) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Competition> GetAllCompetitions() {
        return TmpStorage.competitions;
    }

    public void Delete(Competition competition) {
        TmpStorage.competitions.remove(competition);
    }
}
