package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Competition;
import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import org.hibernate.Session;

import java.util.List;

public class CompetitionGroupDAO implements DAO<CompetitionGroup> {
//    private final Session session;
//
//    public CompetitionGroupDAO(final Session session) {
//        this.session = session;
//    }

//    public void Create(CompetitionGroup group) {
//
//        session.beginTransaction();
//
//        session.save(group);
//
//        session.getTransaction().commit();
//    }

    public void create(CompetitionGroup group) {
        TmpStorage.competitionGroups.add(group);
    }
    public CompetitionGroup GetGroup(long id) {
        for (CompetitionGroup item : TmpStorage.competitionGroups) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<CompetitionGroup> GetAllGroups() {
        return TmpStorage.competitionGroups;
    }

    public void Delete(CompetitionGroup group) {
        TmpStorage.competitionGroups.remove(group);
    }

    public void update(CompetitionGroup group) {
        try {
            TmpStorage.competitionGroups.set(TmpStorage.competitionGroups.indexOf(
                    TmpStorage.competitionGroups.stream().filter(
                            g -> g.getId() == group.getId()
                    ).findFirst().orElse(null)
            ), group);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
