package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Competition;
import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import org.hibernate.Session;

import java.util.List;

public class DistanceDAO implements DAO<Distance> {
    private final Session session;

    public DistanceDAO(final Session session) {
        this.session = session;
    }

//    public void Create(Distance distance) {
//
//        session.beginTransaction();
//
//        session.save(distance);
//
//        session.getTransaction().commit();
//    }

    public void Create(Distance distance) {
        TmpStorage.distances.add(distance);
    }

    public Distance GetDistance(long id) {
        for (Distance item : TmpStorage.distances) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Distance> GetAllDistances() {
        return TmpStorage.distances;
    }

    public void Delete(Distance distance) {
        TmpStorage.distances.remove(distance);
    }
}
