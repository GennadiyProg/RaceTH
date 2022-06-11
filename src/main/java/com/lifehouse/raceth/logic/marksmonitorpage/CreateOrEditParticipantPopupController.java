package com.lifehouse.raceth.logic.marksmonitorpage;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.dao.SportsmanDAO;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.Sportsman;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import com.lifehouse.raceth.usbreader.UsbReader;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private ChoiceBox<Distance> distanceChoiceBox;

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
    private DistanceDAO distanceDAO;
    private List<Sportsman> allSportsmen;
    private String inputChip = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sportsmanDAO = (SportsmanDAO) Main.appContext.getBean("sportsmanDAO");
        distanceDAO = (DistanceDAO) Main.appContext.getBean("distanceDAO");
        allSportsmen = sportsmanDAO.getAllSportsmen();
        initializeTable();
        addingListeners();
        addSelectingSportsmanListener();
        distanceChoiceBox.setItems(FXCollections.observableList(new ArrayList<>(distanceDAO.getCurrentCompetitionDistances())));
    }

    private void initializeTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        sportsmenTableView.getItems().addAll(allSportsmen);
    }

    private void addingListeners() {
        surnameTextField.textProperty().addListener(this::sortingTable);
        nameTextField.textProperty().addListener(this::sortingTable);
        patronymicTextField.textProperty().addListener(this::sortingTable);
        birthdateDatePicker.valueProperty().addListener(this::sortingTable);
    }

    private void sortingTable(Observable observable, Object oldValue, Object newValue) {
        sportsmenTableView.setItems(allSportsmen.stream()
                .filter(sportsman -> sportsman.getLastname().contains(surnameTextField.getText()))
                .filter(sportsman -> sportsman.getName().contains(nameTextField.getText()))
                .filter(sportsman -> sportsman.getPatronymic().contains(patronymicTextField.getText()))
                .filter(sportsman -> {
                    if (birthdateDatePicker.getValue() != null) {
                        return sportsman.getBirthdate().isEqual(birthdateDatePicker.getValue());
                    }
                    return true;
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    private void addSelectingSportsmanListener() {
        sportsmenTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            fillFieldsFromSportsman(sportsmenTableView.getSelectionModel().getSelectedItem());
        });
    }

    public void fillFieldsFromSportsman(Sportsman sportsman) {
        if (sportsman == null) return;
        nameTextField.setText(sportsman.getName());
        patronymicTextField.setText(sportsman.getPatronymic());
        surnameTextField.setText(sportsman.getLastname());
        birthdateDatePicker.setValue(sportsman.getBirthdate());
        genderToggleGroup.selectToggle(determineToggle(sportsman.getGender()));
        cityTextField.setText(sportsman.getRegion());
    }

    @FXML
    public void saving(ActionEvent event) {
        if (nameTextField.getText().isEmpty() ||
                surnameTextField.getText().isEmpty() ||
                patronymicTextField.getText().isEmpty() ||
                birthdateDatePicker.getValue() == null ||
                cityTextField.getText().isEmpty() ||
                chipTextField.getText().isEmpty() ||
                startNumberTextField.getText().isEmpty() ||
                distanceChoiceBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Невозможно создать участника, так как не заполнены все поля");
            alert.show();
            return;
        }
        RadioButton selectedGender = (RadioButton) genderToggleGroup.getSelectedToggle();
        Sportsman sportsman = new Sportsman(
                nameTextField.getText(),
                surnameTextField.getText(),
                patronymicTextField.getText(),
                birthdateDatePicker.getValue(),
                switch (selectedGender.getText()) {
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

    private RadioButton determineToggle(Gender gender) {
        return gender == Gender.MALE ? maleGenderRadioButton : femaleGenderRadioButton;
    }

    @FXML
    private void cancel(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void readChip() {
        try {
            UsbReader usbReader = new UsbReader();
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.
            StackPane layout = new StackPane();
            Text text = new Text("Сканируйте чип");
            layout.getChildren().add(text);
            Scene scene = new Scene(layout, 150, 100);

            scene.setOnKeyPressed((event) -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    chipTextField.setText(usbReader.parseChip(inputChip));
                    inputChip = "";
                    popup.close();
                    return;
                }

                inputChip += event.getText();
            });

            popup.setScene(scene);
            popup.show();
        } catch (Exception e) {
            inputChip = "";
            e.printStackTrace();
        }
    }
}
