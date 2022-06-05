package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.Sportsman;
import com.lifehouse.raceth.repository.SportsmanRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SportsmanDAO {
    private final SportsmanRepository sportsmanRepository;

    public SportsmanDAO() {
        this.sportsmanRepository = (SportsmanRepository) Main.appContext.getBean("sportsmanRepository");
    }

    public void create(Sportsman sportsman) {
        sportsmanRepository.save(sportsman);
    }

    public Sportsman getSportsman(long id) {
        return sportsmanRepository.findById(id).orElse(null);
    }

    public List<Sportsman> getAllSportsmen() {
        return sportsmanRepository.findAll();
    }

    public void delete(Sportsman sportsman) {
        sportsmanRepository.delete(sportsman);
    }

    public Sportsman getSportsmenByFioAndBirthdate(String lastname, String name, String patronymic, LocalDate birthdate) {
        return sportsmanRepository.findSportsmanByLastnameAndNameAndPatronymicAndBirthdate(lastname, name, patronymic, birthdate);
    }
}
