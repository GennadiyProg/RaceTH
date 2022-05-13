package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.view.DistanceView;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import com.lifehouse.raceth.repository.DistanceRepository;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Component
public class DistanceDAO implements DAO<Distance> {
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
