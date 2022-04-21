package com.lifehouse.raceth.gui.competitionpage;

import com.lifehouse.raceth.dao.CompetitionDAO;
import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.gui.competitionpage.impl.DistanceServiceImpl;
import com.lifehouse.raceth.gui.competitionpage.impl.GroupServiceImpl;
import com.lifehouse.raceth.gui.competitionpage.impl.CompetitionServiceImpl;
import com.lifehouse.raceth.model.competition.CompetitionPageButton;
import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Gender;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Data
public class CompetitionPageController implements Initializable {


    @FXML
    private TableView<Competition> competitionTable;
    @FXML
    private TableColumn<Competition, String> cNameColumn;
    @FXML
    private TableColumn<Competition, String> cOrganizerColumn;
    @FXML
    private TableColumn<Competition, String> cLocationColumn;
    @FXML
    private TableColumn<Competition, Date> cDateColumn;
    @FXML
    private TableColumn<Competition, String> cMainJudgeColumn;
    @FXML
    private TableColumn<Competition, String> cMainSecretaryColumn;

    @FXML
    private TableView<Group> groupTable;
    @FXML
    private TableColumn<Group, String> gNameColumn;
    @FXML
    private TableColumn<Group, Integer> gAgeFromColumn;
    @FXML
    private TableColumn<Group, Integer> gAgeToColumn;
    @FXML
    private TableColumn<Group, Gender> gGenderColumn;

    @FXML
    private TableView<Distance> distanceTable;
    @FXML
    private TableColumn<Distance, String> dNameColumn;
    @FXML
    private TableColumn<Distance, Integer> dLengthColumn;
    @FXML
    private TableColumn<Distance, Integer> dHeightColumn;

    @FXML
    private TextField searchCompetitionTextField;
    @FXML
    private TextField searchGroupTextField;
    @FXML
    private TextField searchDistanceTextField;

    private final List<Competition> competitions = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();
    private final List<Distance> distances = new ArrayList<>();

    private final StringProperty value = new SimpleStringProperty();
    private CompetitionDAO competitionDAO;
    private GroupDAO groupDAO;
    private DistanceDAO distanceDAO;

    private CompetitionPageElementService competitionPageElementService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        competitionDAO = new CompetitionDAO();
        groupDAO = new GroupDAO();
        distanceDAO = new DistanceDAO();

        initializeCompetitionTable();
        initializeGroupTable();
        initializeDistanceTable();

        searchEngine();
    }

    private void initializeCompetitionTable(){
        cNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cOrganizerColumn.setCellValueFactory(new PropertyValueFactory<>("organizer"));
        cLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        cDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        cMainJudgeColumn.setCellValueFactory(new PropertyValueFactory<>("mainJudge"));
        cMainSecretaryColumn.setCellValueFactory(new PropertyValueFactory<>("mainSecretary"));

        ObservableList<Competition> competitions = competitionTable.getItems();
        competitions.addAll(competitionDAO.getAllCompetitions());
    }

    private void initializeGroupTable(){
        gNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gAgeFromColumn.setCellValueFactory(new PropertyValueFactory<>("ageFrom"));
        gAgeToColumn.setCellValueFactory(new PropertyValueFactory<>("ageTo"));
        gGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        ObservableList<Group> groups = groupTable.getItems();
        groups.addAll(groupDAO.getAllGroups());
    }

    private void initializeDistanceTable(){
        dNameColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dLengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        dHeightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));

        ObservableList<Distance> distances = distanceTable.getItems();
        distances.addAll(distanceDAO.getAllDistances());
    }

    private void searchEngine(){
        searchCompetitionTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    competitions.addAll(competitionDAO.getAllCompetitions());
                } else {
                    competitions.clear();
                }
            }
        );
        searchCompetitionTextField.textProperty().addListener((observable, oldValue, newValue) -> competitionTable.setItems(
               competitions
                        .stream()
                        .filter(c -> c.getName().contains(newValue))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)))
        );

        searchGroupTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    groups.addAll(groupDAO.getAllGroups());
                } else {
                    groups.clear();
                }
            }
        );
        searchGroupTextField.textProperty().addListener((observable, oldValue, newValue) -> groupTable.setItems(
                groups
                        .stream()
                        .filter(c -> c.getName().contains(newValue))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)))
        );

        searchDistanceTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    distances.addAll(distanceDAO.getAllDistances());
                } else {
                    distances.clear();
                }
            }
        );
        searchDistanceTextField.textProperty().addListener((observable, oldValue, newValue) -> distanceTable.setItems(
                distances
                        .stream()
                        .filter(c -> c.getLocation().contains(newValue))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)))
        );
    }

    @FXML
    public void makeCompetitionCurrent(ActionEvent event){
        Competition competition = competitionTable.getSelectionModel().getSelectedItem();
        if (competition == null){
            return;
        }
        value.setValue(competition.getName());
    }

    @FXML
    void create(ActionEvent event) {
        setCompetitionPageElementService(((Node)event.getSource()).getId().toLowerCase());
        competitionPageElementService.create();
    }

    @FXML
    void edit(ActionEvent event) {
        setCompetitionPageElementService(((Node)event.getSource()).getId().toLowerCase());
        competitionPageElementService.edit();
    }

    @FXML
    void delete(ActionEvent event) {
        setCompetitionPageElementService(((Node)event.getSource()).getId().toLowerCase());
        competitionPageElementService.delete();
    }

    private void setCompetitionPageElementService(String id){
        if (id.equals(CompetitionPageButton.COMPETITIONBUTTON.toString())){
            competitionPageElementService = new CompetitionServiceImpl(competitionTable);
        } else if (id.equals(CompetitionPageButton.GROUPBUTTON.toString())){
            competitionPageElementService = new GroupServiceImpl(groupTable);
        } else if (id.equals(CompetitionPageButton.DISTANCEBUTTON.toString())){
            competitionPageElementService = new DistanceServiceImpl(distanceTable);
        }
    }
}

