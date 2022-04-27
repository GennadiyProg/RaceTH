package com.lifehouse.raceth.gui.competitionpage;

import com.lifehouse.raceth.dao.CompetitionDAO;
import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.gui.competitionpage.impl.DistanceServiceImpl;
import com.lifehouse.raceth.gui.competitionpage.impl.GroupServiceImpl;
import com.lifehouse.raceth.gui.competitionpage.impl.CompetitionServiceImpl;
import com.lifehouse.raceth.gui.search.SearchEngine;
import com.lifehouse.raceth.gui.search.impl.CompetitionTableSearchImpl;
import com.lifehouse.raceth.gui.search.impl.DistanceTableSearchImpl;
import com.lifehouse.raceth.gui.search.impl.GroupTableSearchImpl;
import com.lifehouse.raceth.model.competition.CompetitionPageButton;
import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.viewmodel.GroupView;
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
    private TableView<GroupView> groupTable;
    @FXML
    private TableColumn<GroupView, Boolean> gCheckboxColumn;
    @FXML
    private TableColumn<GroupView, String> gNameColumn;
    @FXML
    private TableColumn<GroupView, Integer> gAgeFromColumn;
    @FXML
    private TableColumn<GroupView, Integer> gAgeToColumn;
    @FXML
    private TableColumn<GroupView, Gender> gGenderColumn;

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

    private SearchEngine competitionSearchEngine;
    private SearchEngine groupSearchEngine;
    private SearchEngine distanceSearchEngine;

    private final StringProperty value = new SimpleStringProperty();
    private CompetitionDAO competitionDAO;
    private GroupDAO groupDAO;
    private DistanceDAO distanceDAO;

    private CompetitionPageElementService competitionPageElementService;

    private Competition currentCompetition;

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
        gCheckboxColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        gNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        gAgeFromColumn.setCellValueFactory(new PropertyValueFactory<>("ageFrom"));
        gAgeToColumn.setCellValueFactory(new PropertyValueFactory<>("ageTo"));
        gGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        ObservableList<GroupView> groups = groupTable.getItems();
        groups.addAll(groupDAO.getAllGroupViews());
    }

    private void initializeDistanceTable(){
        dNameColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dLengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        dHeightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));

        ObservableList<Distance> distances = distanceTable.getItems();
        distances.addAll(distanceDAO.getAllDistances());
    }

    private void searchEngine(){
        competitionSearchEngine = new CompetitionTableSearchImpl(competitionTable, competitionDAO, searchCompetitionTextField);
        groupSearchEngine = new GroupTableSearchImpl(groupTable, groupDAO, searchGroupTextField);
        distanceSearchEngine = new DistanceTableSearchImpl(distanceTable, distanceDAO, searchDistanceTextField);
    }

    @FXML
    public void makeCompetitionCurrent(ActionEvent event){
        currentCompetition = competitionTable.getSelectionModel().getSelectedItem();
        if (currentCompetition == null){
            return;
        }
        value.setValue(currentCompetition.getName());
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

