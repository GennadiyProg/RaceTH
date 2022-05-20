package com.lifehouse.raceth.logic.marksmonitorpage;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.SportsmanDAO;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.Sportsman;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lombok.Data;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private TableView<Sportsman> sportsmenTableView;
    @FXML
    private TableColumn<Sportsman, String> nameColumn;
    @FXML
    private TableColumn<Sportsman, String> cityColumn;
    @FXML
    private TableColumn<Sportsman, String> patronymicColumn;
    @FXML
    private TableColumn<Sportsman, String> surnameColumn;

    public ObjectProperty<Participant> newParticipant = new SimpleObjectProperty<>();

    private SportsmanDAO sportsmanDAO;
    private List<Sportsman> allSportsmen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sportsmanDAO = (SportsmanDAO) Main.appContext.getBean("sportsmanDAO");
        allSportsmen = sportsmanDAO.getAllSportsmen();
        initializeTable();
        addingListeners();
        addSelectingSportsmanListener();
    }

    private void initializeTable(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        sportsmenTableView.getItems().addAll(allSportsmen);
    }

    private void addingListeners(){
        surnameTextField.textProperty().addListener(this::sortingTable);
        nameTextField.textProperty().addListener(this::sortingTable);
        patronymicTextField.textProperty().addListener(this::sortingTable);
        birthdateDatePicker.valueProperty().addListener(this::sortingTable);
    }

    private void sortingTable(Observable observable, Object oldValue, Object newValue){
        sportsmenTableView.setItems(allSportsmen.stream()
                .filter(sportsman -> sportsman.getLastname().contains(surnameTextField.getText()))
                .filter(sportsman -> sportsman.getName().contains(nameTextField.getText()))
                .filter(sportsman -> sportsman.getPatronymic().contains(patronymicTextField.getText()))
                .filter(sportsman -> {
                    if (birthdateDatePicker.getValue() != null){
                        return sportsman.getBirthdate().isEqual(birthdateDatePicker.getValue());
                    }
                    return true;
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    private void addSelectingSportsmanListener(){
        sportsmenTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            fillFieldsFromSportsman(sportsmenTableView.getSelectionModel().getSelectedItem());
        });
    }

    public void fillFieldsFromSportsman(Sportsman sportsman){
        nameTextField.setText(sportsman.getName());
        patronymicTextField.setText(sportsman.getPatronymic());
        surnameTextField.setText(sportsman.getLastname());
        birthdateDatePicker.setValue(sportsman.getBirthdate());
        genderToggleGroup.selectToggle(determineToggle(sportsman.getGender()));
        cityTextField.setText(sportsman.getRegion());
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
                chipTextField.getText().equals("") ? "" : chipTextField.getText(),
                sportsman,
                startNumberTextField.getText().equals("") ? 0 : Integer.parseInt(startNumberTextField.getText())
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
