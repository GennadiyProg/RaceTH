package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Run;
import com.lifehouse.raceth.tmpstorage.TmpStorage;

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

    public void update(Run run) {
        Run chRun = getRun(run.getId());
        chRun.setTime(run.getTime());
        chRun.setLaps(run.getLaps());
    }

    public Run getRun(long id) {
        for (Run item : TmpStorage.runs) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Run> getAllRuns() {
        return TmpStorage.runs;
    }

    public void Delete(Run run) {
        TmpStorage.runs.remove(run);
    }
}
