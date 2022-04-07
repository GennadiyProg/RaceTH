package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.RelayTeam;
import com.lifehouse.raceth.model.Run;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import com.sun.istack.NotNull;
import org.hibernate.Session;

import java.util.List;


public class RunDAO implements DAO<Run> {


//    public void Create(@NotNull final Run run) {
//        session.beginTransaction();
//
//        session.save(run);
//
//        session.getTransaction().commit();
//    }

    public void create(Run run) {
        TmpStorage.runs.add(run);
    }

    public Run GetRun(long id) {
        for (Run item : TmpStorage.runs) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Run> GetAllRuns() {
        return TmpStorage.runs;
    }

    public void Delete(Run run) {
        TmpStorage.runs.remove(run);
    }
}
