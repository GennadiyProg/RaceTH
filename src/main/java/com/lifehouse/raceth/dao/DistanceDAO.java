package com.lifehouse.raceth.dao;

//import com.lifehouse.raceth.HibernateUtil;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.view.DistanceView;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class DistanceDAO implements DAO<Distance> {
//    private final Session session;

    public DistanceDAO() {
//        this.session = HibernateUtil.getSession();
    }

    public void create(Distance distance) {
//        session.beginTransaction();
//        session.save(distance);
//        session.getTransaction().commit();
    }

    public Distance getDistance(long id) {
//        session.beginTransaction();
//        Distance distance = session.get(Distance.class, id);
//        session.getTransaction().commit();
//        return distance;
        return null;
    }

    public List<Distance> getAllDistances() {
//        session.beginTransaction();
//        List<Distance> distances = session.createQuery("FROM Distance", Distance.class).list();
//        session.getTransaction().commit();
//        return distances;
        return null;
    }

    public void delete(Distance distance) {
//        session.beginTransaction();
//        Query selectQuery = session.createQuery("DELETE Distance WHERE id = :id");
//        selectQuery.setParameter("id", distance.getId());
//        selectQuery.executeUpdate();
//        session.getTransaction().commit();
    }

    public void update(Distance distance) {
//        session.beginTransaction();
//        session.merge(distance);
//        session.getTransaction().commit();
    }

    public List<DistanceView> getAllDistanceViews() {
//        List<Distance> distances = getAllDistances();
//        return distances.stream().map(DistanceView::convertToView).toList();
        return null;
    }
}
