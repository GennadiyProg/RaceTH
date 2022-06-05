package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
@Transactional
public interface SportsmanRepository extends JpaRepository<Sportsman, Long> {
    Sportsman findSportsmanByLastnameAndNameAndPatronymicAndBirthdate(String lastname, String name, String patronymic, LocalDate birthdate);
}
