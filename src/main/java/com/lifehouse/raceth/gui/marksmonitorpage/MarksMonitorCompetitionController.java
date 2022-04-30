package com.lifehouse.raceth.gui.marksmonitorpage;

import com.lifehouse.raceth.dao.CheckpointDAO;
import com.lifehouse.raceth.dao.ParticipantDAO;
import com.lifehouse.raceth.dao.StartDAO;
import com.lifehouse.raceth.gui.StartPageController;
import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.model.StartTab;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import com.lifehouse.raceth.model.view.ParticipantStartView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;
import javafx.scene.control.*;
import javafx.event.Event;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

@Data
public class MarksMonitorCompetitionController implements Initializable {

    @FXML
    private Button addGroup;
    @FXML
    private  TabPane tabPane;
    @FXML
    private Tab participantTab;
    @FXML
    private TableView<ParticipantCompetitionView> participantCompetitionTable;
    @FXML
    private TableColumn<ParticipantCompetitionView, Integer> pcNumberColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> pcLastnameColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> pcNameColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> pcPatronymicColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> pcGenderColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> pcChipColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, Integer> pcStartNumberColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, Date> pcBirthdateColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> pcCityColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> pcGroupColumn;


    @FXML
    private TableView<ParticipantStartView> participantStartTable;
    @FXML
    private TableColumn<ParticipantStartView, Integer> psNumberColumn;
    @FXML
    private TableColumn<ParticipantStartView, LocalTime> psCurrentTimeColumn;
    @FXML
    private TableColumn<ParticipantStartView, LocalTime> psTimeOnDistanceColumn;
    @FXML
    private TableColumn<ParticipantStartView, String> psChipColumn;
    @FXML
    private TableColumn<ParticipantStartView, Integer> psStartNumberColumn;
    @FXML
    private TableColumn<ParticipantStartView, String> psLastnameColumn;
    @FXML
    private TableColumn<ParticipantStartView, String> psNameColumn;
    @FXML
    private TableColumn<ParticipantStartView, String> psGroupColumn;

    @FXML
    private TableView<Start> checkpointTable;
    @FXML
    private TableColumn<Start, String> groupColumn;
    @FXML
    private TableColumn<Start, String> startTimeColumn;
    @FXML
    private TableColumn<Start, String> lapColumn;

    private ParticipantDAO participantDAO;
    private CheckpointDAO checkpointDAO;
    private StartDAO startDAO;

    private Set<StartTab> openedTabs;
    private Map<Long, List<Start>> startOnTab;
    private Map<Long, List<ParticipantStartView>> participantOnTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        participantDAO = new ParticipantDAO();
        checkpointDAO = new CheckpointDAO();
        startDAO = new StartDAO();

        initializeCheckpointTable();
        initializeParticipantTable();
        initializeStartTab();

        Tab newtab = new Tab("+");
        newtab.setOnSelectionChanged(event -> createNewTab(event));
        tabPane.getTabs().add(newtab);

        List<Start> starts = startDAO.getStartsByCompetitionDayId(StartPageController.currentCompetitionDay.getId());
        openedTabs = new HashSet<>();
        starts.forEach(start -> {
            openedTabs.add(start.getTab());
            long id = start.getTab().getId();
            if (startOnTab.containsKey(id)) {
                startOnTab.get(id).add(start);
            } else {
                startOnTab.put(id, List.of(start));
            }
        });

        initializeTabs();
    }

    @FXML
    void addingGroup(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MarksGroupPopup.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void initializeCheckpointTable(){
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        lapColumn.setCellValueFactory(new PropertyValueFactory<>("laps"));
    }

    public void initializeParticipantTable(){
        pcNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        pcNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pcLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        pcPatronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        pcGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        pcChipColumn.setCellValueFactory(new PropertyValueFactory<>("chip"));
        pcStartNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        pcBirthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        pcCityColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        pcGroupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));

        ObservableList<ParticipantCompetitionView> participantViews = participantCompetitionTable.getItems();
        participantViews.addAll(participantDAO.getAllParticipantViews());
    }

    public void initializeStartTab(){
        psNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        psCurrentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("currentTime"));
        psTimeOnDistanceColumn.setCellValueFactory(new PropertyValueFactory<>("timeOnDistance"));
        psChipColumn.setCellValueFactory(new PropertyValueFactory<>("chip"));
        psStartNumberColumn.setCellValueFactory(new PropertyValueFactory<>("startNumber"));
        psLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        psNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        psGroupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
    }

    public void initializeTabs(){
        for(StartTab startTab : openedTabs){
            Tab tab = new Tab(startTab.getName());
            tab.setOnSelectionChanged(event -> {
                checkpointTable.getItems().clear();
                checkpointTable.getItems().addAll(startOnTab.get(startTab.getId()));

                participantStartTable.getItems().clear();
                participantStartTable.getItems().addAll(participantOnTab.get(startTab.getId()));
                tab.setContent(participantStartTable);
            });
            tab.setOnSelectionChanged(event1 -> {
                tab.setContent(participantStartTable);
            });
            tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
        }
    }

    private void createNewTab(Event event){
        String nameNewTab = "Забег " + (tabPane.getTabs().size() - 1);
        Tab tab = new Tab(nameNewTab);
        StartTab startTab = new StartTab();
        startTab.setName(nameNewTab);
        TableView<ParticipantStartView> table = new TableView<>();
        table.getColumns().setAll(participantStartTable.getColumns());
        tab.setContent(table);

        tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
    }
}