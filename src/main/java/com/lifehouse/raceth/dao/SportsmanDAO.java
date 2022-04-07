package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Run;
import com.lifehouse.raceth.model.Sportsman;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import org.hibernate.Session;

import java.util.List;

public class SportsmanDAO implements DAO<Sportsman> {


//    public void Create(Sportsman sportsman) {
//        session.beginTransaction();
//
//        session.save(sportsman);
//
//        session.getTransaction().commit();
//    }

    public void create(Sportsman sportsman) {
        TmpStorage.sportsmen.add(sportsman);
    }

    public Sportsman GetSportsman(long id) {
        for (Sportsman item : TmpStorage.sportsmen) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Sportsman> GetAllSportsmen() {
        return TmpStorage.sportsmen;
    }


    public void Delete(Sportsman sportsman) {
        TmpStorage.sportsmen.remove(sportsman);
    }
}
