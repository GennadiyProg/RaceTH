package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.tmpstorage.TmpStorage;

import java.util.List;

public class DistanceDAO implements DAO<Distance> {
//    private final Session session;
//
//    public DistanceDAO(final Session session) {
//        this.session = session;
//    }

//    public void Create(Distance distance) {
//
//        session.beginTransaction();
//
//        session.save(distance);
//
//        session.getTransaction().commit();
//    }

    public void create(Distance distance) {
        TmpStorage.distances.add(distance);
    }

    public Distance getDistance(long id) {
        for (Distance item : TmpStorage.distances) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Distance> getAllDistances() {
        return TmpStorage.distances;
    }

    public void delete(Distance distance) {
        TmpStorage.distances.remove(distance);
    }

    public void update(Distance distance) {
        try {
            TmpStorage.distances.set(TmpStorage.distances.indexOf(
                    TmpStorage.distances.stream().filter(
                            g -> g.getId() == distance.getId()
                    ).findFirst().orElse(null)
            ), distance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
