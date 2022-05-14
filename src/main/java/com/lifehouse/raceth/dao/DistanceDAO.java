package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.view.DistanceView;
import com.lifehouse.raceth.repository.DistanceRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DistanceDAO {
    private final DistanceRepository distanceRepository;

    public DistanceDAO() {
        this.distanceRepository = (DistanceRepository) Main.appContext.getBean("distanceRepository");
    }

    public void create(Distance distance) {
//        session.beginTransaction();
//        session.save(distance);
//        session.getTransaction().commit();
        distanceRepository.save(distance);
    }

    public Distance getDistance(long id) {
//        session.beginTransaction();
//        Distance distance = session.get(Distance.class, id);
//        session.getTransaction().commit();
//        return distance;
        return distanceRepository.findById(id).orElse(null);
    }

    public List<Distance> getAllDistances() {
//        session.beginTransaction();
//        List<Distance> distances = session.createQuery("FROM Distance", Distance.class).list();
//        session.getTransaction().commit();
//        return distances;
        return distanceRepository.findAll();
    }

    public void delete(Distance distance) {
//        session.beginTransaction();
//        Query selectQuery = session.createQuery("DELETE Distance WHERE id = :id");
//        selectQuery.setParameter("id", distance.getId());
//        selectQuery.executeUpdate();
//        session.getTransaction().commit();
        distanceRepository.delete(distance);
    }

    public void update(Distance distance) {
//        session.beginTransaction();
//        session.merge(distance);
//        session.getTransaction().commit();
        distanceRepository.update(distance.getId(), distance);
    }

    public List<DistanceView> getAllDistanceViews() {
        return distanceRepository.findAll().stream().map(DistanceView::convertToView).toList();
    }
}
