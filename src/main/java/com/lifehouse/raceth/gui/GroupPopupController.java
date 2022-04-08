package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.CompetitionGroupDAO;
import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Gender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.net.URL;
import java.util.Enumeration;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // toDo: Поменять текст гендера на русский, сложность в том что русские символы криво считыываются.
        // toDo: В таком случае надо будет менять еще функцию determineGender
        genderM.setText("Male");
        genderW.setText("Female");
        genderM.setToggleGroup(gender);
        genderW.setToggleGroup(gender);
    }

    @FXML
    void Saving(ActionEvent event) {
        try{
            RadioButton selected = (RadioButton) gender.getSelectedToggle();
            CompetitionGroup competitionGroup = new CompetitionGroup();
            CompetitionGroupDAO competitionGroupDAO = new CompetitionGroupDAO();

            competitionGroup.setName(groupName.getText());
            competitionGroup.setGender(determineGender(selected.getText()));
            competitionGroup.setAgeFrom(Integer.parseInt(ageFrom.getText()));
            competitionGroup.setAgeTo(Integer.parseInt(ageTo.getText()));
            competitionGroup.setId(competitionGroupsTable.getItems().size());

            competitionGroupDAO.Create(competitionGroup);

            competitionGroupsTable.getItems().add(competitionGroup);
            competitionGroupsTable.refresh();

            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("cant loading");
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        try{
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("cant loading");
        }
    }

    public Gender determineGender(String gender) {
        return gender == "Male" ? Gender.MALE : Gender.FEMALE;
    }
}


