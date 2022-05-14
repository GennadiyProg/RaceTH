package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.repository.StartRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartDAO {
    private final StartRepository startRepository;

    public StartDAO() {
        this.startRepository = (StartRepository) Main.appContext.getBean("startRepository");
    }

    public void create(Start start) {
        startRepository.save(start);
    }

    public void update(Start start) {
        startRepository.update(start.getId(), start);
    }

    public Start getStart(long id) {
        return startRepository.findById(id).orElse(null);
    }

    public List<Start> getAllRuns() {
        return startRepository.findAll();
    }

    public List<Start> getStartsByCompetitionDayId(long id) {
        return startRepository.findAllByCompetitionDayId(id);
    }

    public List<Start> getStartsByCompetitionId(long id) {
        return startRepository.findAllByCompetitionId(id);
    }

    public void delete(Start start) {
        startRepository.delete(start);
    }
}
