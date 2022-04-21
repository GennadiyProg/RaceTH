package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.tmpstorage.TmpStorage;

import java.util.List;

public class GroupDAO implements DAO<Group> {
//    private final Session session;
//
//    public CompetitionGroupDAO(final Session session) {
//        this.session = session;
//    }

//    public void Create(CompetitionGroup group) {
//
//        session.beginTransaction();
//
//        session.save(group);
//
//        session.getTransaction().commit();
//    }

    public void create(Group group) {
        group.setId(TmpStorage.groups.size());
        TmpStorage.groups.add(group);
    }
    public Group GetGroup(long id) {
        for (Group item : TmpStorage.groups) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Group> getAllGroups() {
        return TmpStorage.groups;
    }

    public void delete(Group group) {
        TmpStorage.groups.remove(group);
    }

    public void update(Group group) {
        try {
            TmpStorage.groups.set(TmpStorage.groups.indexOf(
                    TmpStorage.groups.stream().filter(
                            g -> g.getId() == group.getId()
                    ).findFirst().orElse(null)
            ), group);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
