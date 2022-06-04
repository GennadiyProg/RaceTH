package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.logic.MainPageController;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.model.view.GroupView;
import com.lifehouse.raceth.repository.GroupRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupDAO {
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
        groupRepository.save(group);
    }

    public List<GroupView> getAllGroupViews() {
        return groupRepository.findAll().stream().map(GroupView::convertToView).toList();
    }

    public void addCompetition(Group group, Competition competition) {
        group.getCompetitions().add(competition);
        var groupRecord = getGroup(group.getId());
        groupRecord.getCompetitions().add(competition);
        groupRepository.saveAndFlush(groupRecord);
    }

    public void removeCompetition(Group group, Competition competition) {
        var competitions = group.getCompetitions();
        competitions.remove(
                competitions.stream()
                        .filter((el) -> el.getId() == competition.getId())
                        .findFirst().orElse(null)
        );

        groupRepository.deleteCompetitionRelation(group.getId(), competition.getId());
        groupRepository.saveAndFlush(group);
    }

    public List<Group> getCurrentCompetitionGroups() {
        return groupRepository.findAllByCompetitions(MainPageController.currentCompetition);
    }
}
