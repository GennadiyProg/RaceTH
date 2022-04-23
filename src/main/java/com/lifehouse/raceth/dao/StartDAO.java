package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.tmpstorage.TmpStorage;

import java.util.List;


public class StartDAO implements DAO<Start> {

//    public void Create(@NotNull final Run run) {
//        session.beginTransaction();
//
//        session.save(run);
//
//        session.getTransaction().commit();
//    }

    public void create(Start run) {
        TmpStorage.runs.add(run);
    }

    public void update(Start run) {
        Start chRun = getRun(run.getId());
        chRun.setStartTime(run.getStartTime());
        chRun.setLaps(run.getLaps());
    }

    public Start getRun(long id) {
        for (Start item : TmpStorage.runs) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Start> getAllRuns() {
        return TmpStorage.runs;
    }

    public void Delete(Start run) {
        TmpStorage.runs.remove(run);
    }
}
