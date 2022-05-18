package com.lifehouse.raceth.model.view;

import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.logic.MainPageController;
import com.lifehouse.raceth.logic.competitionpage.CompetitionPageController;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.competition.Competition;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupView {
    private CheckBox checkBox = new CheckBox();
    private long id;
    private String name;
    private int ageFrom;
    private int ageTo;
    private Gender gender;
    private List<Competition> competitions = new ArrayList<>();

    private ChangeListener<Boolean> checkboxListener = ((observableList, oldStatus, newStatus) ->
            attachCompetition(newStatus)
    );

    public GroupView(long id, String name, int ageFrom, int ageTo, Gender gender, List<Competition> competitions) {
        checkBox.selectedProperty().addListener(checkboxListener);
        this.id = id;
        this.name = name;
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
        this.gender = gender;
        this.competitions = competitions;
    }

    private void attachCompetition(Boolean status) {
        GroupDAO groupDAO = new GroupDAO();
        Competition curCompetition = MainPageController.currentCompetition;
        if (curCompetition == null) {
            callAlert();
            return;
        }

        if (status) {
            attachCompetitionUtil(curCompetition, groupDAO);
        } else {
            detachCompetitionUtil(curCompetition, groupDAO);
        }
    }

    private void attachCompetitionUtil(Competition curCompetition, GroupDAO groupDAO) {
        boolean relationExists = this.competitions.stream().anyMatch((el) -> el.getId() == curCompetition.getId());
        if (!relationExists) {
            groupDAO.addCompetition(GroupView.convertToModel(this), curCompetition);
        }
    }

    private void detachCompetitionUtil(Competition curCompetition, GroupDAO groupDAO) {
        boolean relationExists = this.competitions.stream().anyMatch((el) -> el.getId() == curCompetition.getId());
        if (relationExists) {
            groupDAO.removeCompetition(GroupView.convertToModel(this), curCompetition);
        }
    }

    private void callAlert() {
        checkBox.selectedProperty().removeListener(checkboxListener);
        checkBox.setSelected(false);
        checkBox.selectedProperty().addListener(checkboxListener);
        new Alert(Alert.AlertType.ERROR, "Не указано текущее соревнование").show();
    }

    public static GroupView convertToView(Group group) {
        return new GroupView(
                group.getId(),
                group.getName(),
                group.getAgeFrom(),
                group.getAgeTo(),
                group.getGender(),
                group.getCompetitions()
        );
    }

    public static Group convertToModel(GroupView groupView) {
        return new Group(
                groupView.getId(),
                groupView.getName(),
                groupView.getAgeFrom(),
                groupView.getAgeTo(),
                groupView.getGender(),
                groupView.getCompetitions()
        );
    }

    public void setFields(GroupView groupView) {
        this.name = groupView.getName();
        this.gender = groupView.getGender();
        this.ageFrom = groupView.getAgeFrom();
        this.ageTo = groupView.getAgeTo();
    }
}
