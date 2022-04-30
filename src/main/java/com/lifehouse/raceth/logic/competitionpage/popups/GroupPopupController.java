package com.lifehouse.raceth.logic.competitionpage.popups;

import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.view.GroupView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.net.URL;
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

    public ObjectProperty<Group> newGroup = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderM.setToggleGroup(gender);
        genderW.setToggleGroup(gender);
    }

    @FXML
    void createOrUpdateGroup(ActionEvent event) {
        Group group = buildNewGroup();
        if (group == null) return;
        newGroup.setValue(group);
        cancel(event);
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private Group buildNewGroup() {
        RadioButton selected = (RadioButton) gender.getSelectedToggle();

        if (groupName.getText().equals("") ||
                ageFrom.getText().equals("") ||
                ageTo.getText().equals("") ||
                selected == null) {
            return null;
        }
        int localAgeFrom, localAgeTo;

        try {
            localAgeFrom = Integer.parseInt(ageFrom.getText());
            localAgeTo = Integer.parseInt(ageTo.getText());
        } catch (NumberFormatException e){
            return null;
        }

        return new Group(
                groupName.getText(),
                localAgeFrom,
                localAgeTo,
                determineGender(selected.getText())
        );
    }

    private void fillFieldsFromEntity(GroupView group) {
        groupName.setText(group.getName());
        ageFrom.setText(String.valueOf(group.getAgeFrom()));
        ageTo.setText(String.valueOf(group.getAgeTo()));
        gender.selectToggle(determineToggle(group.getGender()));
    }

    private Gender determineGender(String gender) {
        return "Мужской".equals(gender) ? Gender.MALE : Gender.FEMALE;
    }

    private RadioButton determineToggle(Gender gender) {
        return gender == Gender.MALE ? genderM : genderW;
    }

    public void edit(GroupView group) {
        fillFieldsFromEntity(group);
    }
}


