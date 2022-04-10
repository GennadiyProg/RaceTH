package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.CompetitionGroupDAO;
import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Sportsman;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.lang.reflect.Executable;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

@Data
public class GroupPopupController implements Initializable {
    @FXML
    private AnchorPane main_pane;
    @FXML
    private TextField ageFrom;
    @FXML
    private TextField ageTo;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton genderM;
    @FXML
    private RadioButton genderW;
    @FXML
    private TextField groupName;
    @FXML
    private TableView<CompetitionGroup> competitionGroupsTable;
    private CompetitionGroupDAO competitionGroupDAO;

    //-----------------------------
    private long changedGroupId = -1L;
    //-----------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderM.setToggleGroup(gender);
        genderW.setToggleGroup(gender);
        competitionGroupDAO = new CompetitionGroupDAO();
    }

    @FXML
    void Saving(ActionEvent event) {
        try{
            CompetitionGroup newCompetitionGroup = buildNewGroup();
            if (newCompetitionGroup == null) {
                return;
            }

            if (changedGroupId >= 0L) {
                competitionGroupDAO.update(newCompetitionGroup);
                changedGroupId = -1;

                ((Node)(event.getSource())).getScene().getWindow().hide();

                competitionGroupsTable.getSelectionModel().getSelectedItem().setFields(newCompetitionGroup);
                competitionGroupsTable.refresh();
                return;
            }

            newCompetitionGroup.setId(competitionGroupsTable.getItems().size());
            competitionGroupDAO.create(newCompetitionGroup);
            competitionGroupsTable.getItems().add(newCompetitionGroup);
            competitionGroupsTable.refresh();

            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        try{
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("can't loading");
        }
    }

    private CompetitionGroup buildNewGroup() {
        CompetitionGroup competitionGroup = new CompetitionGroup();
        RadioButton selected = (RadioButton) gender.getSelectedToggle();

        if (selected == null) {
            // todo: Тут надо пометить что необходимо выбрать RadioButton
            return null;
        }

        competitionGroup.setName(groupName.getText());
        competitionGroup.setGender(determineGender(selected.getText()));
        competitionGroup.setAgeFrom(Integer.parseInt(ageFrom.getText()));
        competitionGroup.setAgeTo(Integer.parseInt(ageTo.getText()));
        return competitionGroup;
    }

    private void fillFieldsFromEntity(CompetitionGroup competitionGroup) {
        groupName.setText(competitionGroup.getName());
        ageFrom.setText(String.valueOf(competitionGroup.getAgeFrom()));
        ageTo.setText(String.valueOf(competitionGroup.getAgeTo()));
        gender.selectToggle(determineToggle(competitionGroup.getGender()));
    }

    private Gender determineGender(String gender) {
        return "Мужской".equals(gender) ? Gender.MALE : Gender.FEMALE;
    }

    private RadioButton determineToggle(Gender gender) {
        return gender == Gender.MALE ? genderM : genderW;
    }

    public void Edit(CompetitionGroup competitionGroup) {
        fillFieldsFromEntity(competitionGroup);
        changedGroupId = competitionGroup.getId();
    }
}


