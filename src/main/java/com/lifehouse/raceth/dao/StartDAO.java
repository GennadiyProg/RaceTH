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
        TmpStorage.starts.add(run);
    }

    public void update(Start run) {
        Start chRun = getRun(run.getId());
        chRun.setStartTime(run.getStartTime());
        chRun.setLaps(run.getLaps());
    }

    public Start getRun(long id) {
        for (Start item : TmpStorage.starts) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Start> getAllRuns() {
        return TmpStorage.starts;
    }

    public List<Start> getStartsByCompetitionDayId(long id) {
        return TmpStorage.starts.stream().filter(el -> el.getCompetitionDay().getId() == id).toList();
    }

    public List<Start> getStartsByCompetitionId(long id) {
        return TmpStorage.starts.stream().filter(el -> el.getCompetitionDay().getCompetition().getId() == id).toList();
    }

    public void delete(Start run) {
        TmpStorage.starts.remove(run);
    }
}
