package com.lifehouse.raceth.gui.marksmonitorpage;

import com.lifehouse.raceth.dao.ParticipantDAO;
import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
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
import javafx.event.EventHandler;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Data
public class MarksMonitorCompetitionController implements Initializable {

    @FXML
    private Button addGroup;
    @FXML
    private  TabPane tabPane;
    @FXML
    private Tab participantTab;
    @FXML
    private TableView<ParticipantCompetitionView> participantTable;
    @FXML
    private TableColumn<ParticipantCompetitionView, Integer> numberColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> nameColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> genderColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> chipColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, Integer> startNumberColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, Date> birthdateColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> cityColumn;

    @FXML
    private TableView<Checkpoint> checkpointTable;
    @FXML
    private TableColumn<Start, String> groupColumn;
    @FXML
    private TableColumn<Start, String> startTimeColumn;
    @FXML
    private TableColumn<Start, String> lapColumn;

    private ParticipantDAO participantDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        participantDAO = new ParticipantDAO();
        initializeCheckpointTable();
        initializeParticipantTable();

        // create a tab which when pressed creates a new tab
        Tab newtab = new Tab("+");

        EventHandler<Event> event = e -> {
            if (newtab.isSelected())
            {
                // create Tab
                Tab tab1 = new Tab("Забег " + tabPane.getTabs().size());

                TableView tableView1 = new TableView();
                tableView1.setPrefWidth(1072);
                tableView1.setPrefHeight(318);
                TableColumn nnumber = new TableColumn("№");
                nnumber.setPrefWidth(50);
                TableColumn nparticipant = new TableColumn("Участник");
                nparticipant.setPrefWidth(100);
                TableColumn ngender = new TableColumn("Пол");
                ngender.setPrefWidth(50);
                TableColumn nchip = new TableColumn("Чип");
                nchip.setPrefWidth(130);
                TableColumn nstart_number = new TableColumn("Стартовый номер");
                nstart_number.setPrefWidth(130);
                TableColumn ndate_birth = new TableColumn("Дата рождения");
                ndate_birth.setPrefWidth(130);
                TableColumn ncity = new TableColumn("Город");
                ncity.setPrefWidth(120);
                TableColumn nclub = new TableColumn("Клуб");
                nclub.setPrefWidth(120);
                TableColumn ncategory = new TableColumn("Разряд");
                ncategory.setPrefWidth(120);
                TableColumn ngroup = new TableColumn("Группа");
                ngroup.setPrefWidth(120);
                tableView1.getColumns().addAll(nnumber,nparticipant,ngender,nchip,nstart_number,ndate_birth,ncity,
                nclub,ncategory,ngroup);

                // add content to the tab
                tab1.setContent(tableView1);
                // add tab
                tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab1);

                // select the last tab
                tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
            }
        };

        // set event handler to the tab
        newtab.setOnSelectionChanged(event);

        tabPane.getTabs().add(newtab);
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
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        chipColumn.setCellValueFactory(new PropertyValueFactory<>("chip"));
        startNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("region"));

        ObservableList<ParticipantCompetitionView> participantViews = participantTable.getItems();
        participantViews.addAll(participantDAO.getAllParticipantViews());
    }
}