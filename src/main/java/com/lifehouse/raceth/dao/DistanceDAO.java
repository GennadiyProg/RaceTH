package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.competition.Competition;
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
        distanceRepository.save(distance);
    }

    public Distance getDistance(long id) {
        return distanceRepository.findById(id).orElse(null);
    }

    public List<Distance> getAllDistances() {
        return distanceRepository.findAll();
    }

    public void delete(Distance distance) {
        distanceRepository.delete(distance);
    }

    public void update(Distance distance) {
        distanceRepository.save(distance);
    }

    public List<DistanceView> getAllDistanceViews() {
        return distanceRepository.findAll().stream().map(DistanceView::convertToView).toList();
    }

    public void addCompetition(Distance distance, Competition competition) {
        distance.getCompetitions().add(competition);
        var distanceRecord = getDistance(distance.getId());
        distanceRecord.getCompetitions().add(competition);
        distanceRepository.saveAndFlush(distanceRecord);
    }

    public void removeCompetition(Distance distance, Competition competition) {
        var competitions = distance.getCompetitions();
        competitions.remove(
                competitions.stream()
                        .filter((el) -> el.getId() == competition.getId())
                        .findFirst().orElse(null)
        );

        distanceRepository.deleteCompetitionRelation(distance.getId(), competition.getId());
        distanceRepository.saveAndFlush(distance);
    }
}
