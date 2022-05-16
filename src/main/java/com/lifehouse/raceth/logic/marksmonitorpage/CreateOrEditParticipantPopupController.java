package com.lifehouse.raceth.logic.marksmonitorpage;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.SportsmanDAO;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.Sportsman;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public class CreateOrEditParticipantPopupController implements Initializable {
    @FXML
    private DatePicker birthdateDatePicker;
    @FXML
    private TextField chipTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField patronymicTextField;
    @FXML
    private TextField startNumberTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private RadioButton femaleGenderRadioButton;
    @FXML
    private ToggleGroup genderToggleGroup;
    @FXML
    private RadioButton maleGenderRadioButton;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> cityColumn;

    @FXML
    private TableColumn<?, ?> patronymicColumn;

    @FXML
    private TableColumn<?, ?> surnameColumn;

    public ObjectProperty<Participant> newParticipant = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void saving(ActionEvent event) {
        RadioButton selectedGender = (RadioButton) genderToggleGroup.getSelectedToggle();
        Sportsman sportsman = new Sportsman(
                nameTextField.getText(),
                surnameTextField.getText(),
                patronymicTextField.getText(),
                birthdateDatePicker.getValue(),
                switch (selectedGender.getText()){
                    case "М" -> Gender.MALE;
                    case "Ж" -> Gender.FEMALE;
                    default -> null;
                },
                cityTextField.getText()
        );
        newParticipant.setValue(new Participant(
                chipTextField.getText(),
                sportsman,
                Integer.parseInt(startNumberTextField.getText())
        ));
        cancel(event);
    }

    public void fillFieldsFromEntity(ParticipantCompetitionView participant) {
        chipTextField.setText(participant.getChip());
        cityTextField.setText(participant.getRegion());
        nameTextField.setText(participant.getName());
        patronymicTextField.setText(participant.getPatronymic());
        startNumberTextField.setText(String.valueOf(participant.getStartNumber()));
        surnameTextField.setText(participant.getLastname());
        birthdateDatePicker.setValue(participant.getBirthdate());
        genderToggleGroup.selectToggle(determineToggle(participant.getGender()));
    }

    private RadioButton determineToggle(Gender gender){
        return gender == Gender.MALE ? maleGenderRadioButton : femaleGenderRadioButton;
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
