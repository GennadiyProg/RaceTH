package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Modifying
    @Query("UPDATE Group g SET g = :group WHERE g.id = :id")
    void update(@Param("id") long id, @Param("group") Group group);
}
