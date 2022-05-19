package com.lifehouse.raceth.logic.marksmonitorpage;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.*;
import com.lifehouse.raceth.logic.MainPageController;
import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.model.StartTab;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import com.lifehouse.raceth.model.view.ParticipantStartView;
import com.lifehouse.raceth.readingfiles.ExcelRead;
import com.lifehouse.raceth.rfid.RFID;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import lombok.Data;
import javafx.scene.control.*;
import javafx.event.Event;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    public TextField lastNumber;
    @FXML
    private Button startButton;

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
    private TableView<Start> startTable;
    @FXML
    private TableColumn<Start, String> groupColumn;
    @FXML
    private TableColumn<Start, String> startTimeColumn;
    @FXML
    private TableColumn<Start, String> lapColumn;
    @FXML
    private TextField stopwatch, timeStarted;

    private ParticipantDAO participantDAO;
    private CheckpointDAO checkpointDAO;
    private StartDAO startDAO;
    private StartTabDAO startTabDAO;

    private Set<StartTab> openedTabs;
    private Map<Long, List<Start>> startOnTab;

    private Boolean isButtonGreen = true;
    private RFID thread;

    private LocalTime localTime;
    private int timing = 0;
    private String hours, minutes, seconds, milliseconds;
    private Timeline timeline;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        participantDAO = (ParticipantDAO) Main.appContext.getBean("participantDAO");
        checkpointDAO = (CheckpointDAO) Main.appContext.getBean("checkpointDAO");
        startDAO = (StartDAO) Main.appContext.getBean("startDAO");
        startTabDAO = (StartTabDAO) Main.appContext.getBean("startTabDAO");

        initializeStartTable();
        initializeParticipantTable();
        initializeStartTab();
        initializeTimeline();

        // fillingStartList(); могуть быть null'ы

        Tab newtab = new Tab("+");
        newtab.setOnSelectionChanged(this::createNewTab);
        tabPane.getTabs().add(newtab);

        List<Start> starts = startDAO.getStartsByCompetitionDayId(MainPageController.currentCompetitionDay.getId());
        openedTabs = new HashSet<>();
        starts.forEach(start -> {
            if (start.getTab() == null) {
                // Дописать проверку на null
            }
            else {
                openedTabs.add(start.getTab());
                long id = start.getTab().getId();
                if (startOnTab.containsKey(id)) {
                    startOnTab.get(id).add(start);
                } else {
                    startOnTab.put(id, List.of(start));
                }
            }
        });
        initializeTabs();
    }

    @FXML
    void addingGroup(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/marksmonitor/MarksGroupPopup.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void initializeTimeline(){
        DateTimeFormatter formatForDateNow = DateTimeFormatter.ofPattern("HH:mm:ss:SS");
        localTime = LocalTime.of(0, 0, 0, 0);
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(10),
                        ae -> {
                            localTime = localTime.plusNanos(10000000);
                            stopwatch.setText(localTime.format(formatForDateNow));
                        }
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void initializeStartTable(){
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

    private void fillingStartList(){
        List<Start> starts = startDAO.getStartsByCompetitionDayId(MainPageController.currentCompetitionDay.getId());
        openedTabs = new HashSet<>();
        startOnTab = new HashMap<>();
        starts.forEach(start -> {
            openedTabs.add(start.getTab());
            long id = start.getTab().getId();
            if (startOnTab.containsKey(id)) {
                startOnTab.get(id).add(start);
            } else {
                startOnTab.put(id, List.of(start));
            }
        });
    }

    public void initializeTabs(){
        for(StartTab startTab : openedTabs){
            Tab tab = new Tab(startTab.getName());
            createTableOnTab(tab, startTab.getId());
            tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
        }
    }

    private void createNewTab(Event event){
        StartTab startTab = new StartTab();
        openedTabs.add(startTabDAO.create(startTab));
        startOnTab.put(startTab.getId(), List.of());

        Tab tab = new Tab(startTab.getName());
        createTableOnTab(tab, startTab.getId());

        tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
    }

    private void switchingTab(Event event){
        Tab tab = (Tab) event.getSource();
        if (tab.isSelected()){
            String nameTab = tab.getText();
            long id = openedTabs.stream().filter(startTab -> startTab.getName().equals(nameTab)).findFirst().orElse(null).getId();
            startTable.getItems().clear();
            startTable.getItems().addAll(startOnTab.get(id));
        }
    }

    private void createTableOnTab(Tab tab, long id){
        TableView<ParticipantStartView> table = new TableView<>();
        table.getColumns().setAll(participantStartTable.getColumns());
        List<Long> startsIdOnTab = new ArrayList<>();
        startOnTab.get(id).forEach(start -> startsIdOnTab.add(start.getId()));
        table.getItems().addAll(participantDAO.getAllParticipantViewsByStarts(startsIdOnTab));
        tab.setContent(table);
        tab.setOnSelectionChanged(this::switchingTab);
    }

    public void startButtonClick() {
        //Изменение цвета и текста кнопки при нажатии
        if(isButtonGreen) {
            startButton.setText("Стоп");
            timeline.play();
            startButton.getStyleClass().set(3,"btn-danger");
            // Для будущего использования потоков
//            if (thread == null) {
//                 thread = new RFID("Potok dlya metok",this);
//            }
//            thread.threadResume();

            isButtonGreen = false;
        } else if (!isButtonGreen) {
            startButton.setText("Старт");
            startButton.getStyleClass().set(3,"btn-success");
            timeline.stop();
//            thread.threadSuspend();
            isButtonGreen = true;
        }
    }

    public void resetTimeButton(javafx.event.ActionEvent actionEvent) {
        stopwatch.setText("00:00:00:00");
        timing = 0;
        localTime = LocalTime.of(0, 0, 0, 0);
    };
    public void timeStartButton(javafx.event.ActionEvent actionEvent) {
        timeStarted.setText("В разработке");
        System.out.println(LocalTime.now());
    };
    public void addFileExcel(){
        System.out.println("bebebebe");
        new ExcelRead();
    };
}