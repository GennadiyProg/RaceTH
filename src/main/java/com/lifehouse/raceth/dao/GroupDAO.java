package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.view.GroupView;
import com.lifehouse.raceth.repository.GroupRepository;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupDAO implements DAO<Group> {
    private final GroupRepository groupRepository;

    public GroupDAO() {
        this.groupRepository = (GroupRepository) Main.appContext.getBean("groupRepository");
    }

    public void create(Group group) {
        groupRepository.save(group);
    }
    public Group getGroup(long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public void delete(Group group) {
        groupRepository.delete(group);
    }

    public void update(Group group) {
        groupRepository.update(group.getId(), group);
    }

    public List<GroupView> getAllGroupViews() {
        return  groupRepository.findAll().stream().map(GroupView::convertToView).toList();
    }
}
