package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.RelayTeam;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import org.hibernate.Session;

import java.util.List;

public class RelayTeamDAO implements DAO<RelayTeam> {
//    private final Session session;
//
//    public RelayTeamDAO(final Session session) {
//        this.session = session;
//    }

//    public void Create(RelayTeam relayTeam) {
//        session.beginTransaction();
//
//        session.save(relayTeam);
//
//        session.getTransaction().commit();
//    }

    public void create(RelayTeam relayTeam) {
        TmpStorage.relayTeams.add(relayTeam);
    }

    public RelayTeam GetRelayTeam(long id) {
        for (RelayTeam item : TmpStorage.relayTeams) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<RelayTeam> GetAllTeams() {
        return TmpStorage.relayTeams;
    }

    public void Delete(RelayTeam relayTeam) {
        TmpStorage.relayTeams.remove(relayTeam);
    }

    public void update(RelayTeam relayTeam) {
        try {
            TmpStorage.relayTeams.set(TmpStorage.relayTeams.indexOf(
                    TmpStorage.relayTeams.stream().filter(
                            g -> g.getId() == relayTeam.getId()
                    ).findFirst().orElse(null)
            ), relayTeam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
