package com.lifehouse.raceth.gui.competitionpage;

import com.lifehouse.raceth.dao.CompetitionDAO;
import com.lifehouse.raceth.dao.CompetitionGroupDAO;
import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.gui.competitionpage.impl.DistanceServiceImpl;
import com.lifehouse.raceth.gui.competitionpage.impl.GroupServiceImpl;
import com.lifehouse.raceth.gui.competitionpage.popups.CompetitionPopupController;
import com.lifehouse.raceth.gui.competitionpage.impl.CompetitionServiceImpl;
import com.lifehouse.raceth.model.competition.CompetitionButtons;
import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.model.CompetitionGroup;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.competition.DistanceButtons;
import com.lifehouse.raceth.model.competition.GroupButtons;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
    private TableView<CompetitionGroup> groupTable;
    @FXML
    private TableColumn<CompetitionGroup, String> gNameColumn;
    @FXML
    private TableColumn<CompetitionGroup, Integer> gAgeFromColumn;
    @FXML
    private TableColumn<CompetitionGroup, Integer> gAgeToColumn;
    @FXML
    private TableColumn<CompetitionGroup, Gender> gGenderColumn;

    @FXML
    private TableView<Distance> distanceTable;
    @FXML
    private TableColumn<Distance, String> dNameColumn;
    @FXML
    private TableColumn<Distance, Integer> dLengthColumn;
    @FXML
    private TableColumn<Distance, Integer> dHeightColumn;

    @FXML
    private TextField searchTextField;

    private final StringProperty value = new SimpleStringProperty();
    private CompetitionDAO competitionDAO;
    private CompetitionGroupDAO competitionGroupDAO;
    private DistanceDAO distanceDAO;

    private CompetitionPageElementService competitionPageElementService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        competitionDAO = new CompetitionDAO();
        competitionGroupDAO = new CompetitionGroupDAO();
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

        ObservableList<CompetitionGroup> competitionGroups = groupTable.getItems();
        competitionGroups.addAll(competitionGroupDAO.getAllGroups());
    }

    private void initializeDistanceTable(){
        dNameColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dLengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        dHeightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));

        ObservableList<Distance> distances = distanceTable.getItems();
        distances.addAll(distanceDAO.getAllDistances());
    }

    private void searchEngine(){
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> competitionTable.setItems(
                competitionDAO.getAllCompetitions()
                        .stream()
                        .filter(c -> c.getName().contains(newValue))
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
        String id = ((Node)event.getSource()).getId().toLowerCase();

        if (id.equals(CompetitionButtons.CREATECOMPETITIONBUTTON.toString().toLowerCase())){
            competitionPageElementService = new CompetitionServiceImpl();
            competitionPageElementService.create(competitionTable);
        } else if (id.equals(GroupButtons.CREATEGROUPBUTTON.toString())){
            competitionPageElementService = new GroupServiceImpl();
            competitionPageElementService.create(groupTable);
        } else if (id.equals(DistanceButtons.CREATEDISTANCEBUTTON.toString())){
            competitionPageElementService = new DistanceServiceImpl();
            competitionPageElementService.create(distanceTable);
        }
    }

    @FXML
    void edit(ActionEvent event) {
        String id = ((Node)event.getSource()).getId().toLowerCase();

        if (id.equals(CompetitionButtons.EDITCOMPETITIONBUTTON.toString())){
            competitionPageElementService = new CompetitionServiceImpl();
            competitionPageElementService.edit(competitionTable);
        } else if (id.equals(GroupButtons.EDITGROUPBUTTON.toString())){
            competitionPageElementService = new GroupServiceImpl();
            competitionPageElementService.edit(groupTable);
        } else if (id.equals(DistanceButtons.EDITDISTANCEBUTTON.toString())){
            competitionPageElementService = new DistanceServiceImpl();
            competitionPageElementService.edit(distanceTable);
        }
    }

    @FXML
    void delete(ActionEvent event) {
        String id = ((Node)event.getSource()).getId().toLowerCase();

        if (id.equals(CompetitionButtons.DELETECOMPETITIONBUTTON.toString())){
            competitionPageElementService = new CompetitionServiceImpl();
            competitionPageElementService.delete(competitionTable);
        } else if (id.equals(GroupButtons.DELETEGROUPBUTTON.toString())){
            competitionPageElementService = new GroupServiceImpl();
            competitionPageElementService.delete(groupTable);
        } else if (id.equals(DistanceButtons.DELETEDISTANCEBUTTON.toString())){
            competitionPageElementService = new DistanceServiceImpl();
            competitionPageElementService.delete(distanceTable);
        }
    }
}

