package com.lifehouse.raceth.logic.marksmonitorpage;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.*;
import com.lifehouse.raceth.logic.MainPageController;
import com.lifehouse.raceth.model.*;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import com.lifehouse.raceth.model.view.ParticipantStartView;
import com.lifehouse.raceth.model.dto.TabDto;
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
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
public class MarksMonitorCompetitionController implements Initializable {

    @FXML
    private Button addGroup;
    @FXML
    private TabPane tabPane;
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
    private TableColumn<ParticipantStartView, Integer> psLapColumn;
    @FXML
    private TableColumn<ParticipantStartView, Integer> psPlaceColumn;
    @FXML
    private TableColumn<ParticipantStartView, LocalTime> psBehindTheLeaderColumn;
    @FXML
    private TableColumn<ParticipantStartView, LocalTime> psLapTimeColumn;

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
    private SportsmanDAO sportsmanDAO;

    private List<TabDto> openedTabs;

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
        sportsmanDAO = (SportsmanDAO) Main.appContext.getBean("sportsmanDAO");
        openedTabs = new ArrayList<>();

        initStartTable();
        initParticipantTable();
        initStartTab();
        initTimeline();
        initTabs();
        initTabAddButton();
    }

    @FXML
    private void attachStart(ActionEvent event) {
        var currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab.equals(participantTab)) {
            alertWrongTab();
            return;
        }

        var popup = openPopup("/view/marksmonitor/MarksGroupPopup.fxml");
        MarksGroupPopupController popupController = popup.getController();
        popupController.setCurrentTab(currentTab);
    }

    private void alertWrongTab() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Невозможно привязать старты, так как выбрана таблица \"Участники\"");
        alert.show();
    }

    @FXML
    public void createOrEditParticipant(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = openPopup("/view/marksmonitor/CreateOrEditParticipantPopup.fxml");
        if (fxmlLoader == null) {
            return;
        }

        CreateOrEditParticipantPopupController controller = fxmlLoader.getController();
        boolean isSelected = !participantCompetitionTable.getSelectionModel().isEmpty();
        if (isSelected) {
            controller.fillFieldsFromEntity(participantCompetitionTable.getSelectionModel().getSelectedItem());
        }

        controller.getNewParticipant().addListener((observable, oldValue, newValue) -> {
            if (isSelected) {
                Participant storedParticipant = participantDAO.getParticipant(
                        participantCompetitionTable.getSelectionModel().getSelectedItem().getId());
                newValue.setStart(storedParticipant.getStart());
                newValue.getSportsman().setId(storedParticipant.getSportsman().getId());
                newValue.setId(storedParticipant.getId());
            }
            sportsmanDAO.create(newValue.getSportsman());
            ParticipantCompetitionView newParticipant = ParticipantCompetitionView.convertToView(participantDAO.update(newValue));
            if (!isSelected) {
                participantCompetitionTable.getItems().add(newParticipant);
                participantCompetitionTable.refresh();
            } else {
                participantCompetitionTable.getItems().stream()
                        .filter(part -> part.getId() == newParticipant.getId())
                        .forEach(part -> part.setFields(newParticipant));
                participantCompetitionTable.refresh();
            }
        });
    }

    private FXMLLoader openPopup(String source) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        return fxmlLoader;
    }

    public void initTimeline() {
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

    public void initStartTable() {
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        lapColumn.setCellValueFactory(new PropertyValueFactory<>("laps"));
    }

    public void initParticipantTable() {
        pcNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        pcNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pcLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        pcPatronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        pcGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        pcChipColumn.setCellValueFactory(new PropertyValueFactory<>("chip"));
        pcStartNumberColumn.setCellValueFactory(new PropertyValueFactory<>("startNumber"));
        pcBirthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        pcCityColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        pcGroupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));

        ObservableList<ParticipantCompetitionView> participantViews = participantCompetitionTable.getItems();
        //TODO: Добавить метод для получения участников текущего соревнования
        participantViews.addAll(participantDAO.getAllParticipantViews());
    }

    public void initStartTab() {
        psNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        psCurrentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("currentTime"));
        psTimeOnDistanceColumn.setCellValueFactory(new PropertyValueFactory<>("timeOnDistance"));
        psChipColumn.setCellValueFactory(new PropertyValueFactory<>("chip"));
        psStartNumberColumn.setCellValueFactory(new PropertyValueFactory<>("startNumber"));
        psLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        psNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        psGroupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        psLapColumn.setCellValueFactory(new PropertyValueFactory<>("lap"));
        psPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        psBehindTheLeaderColumn.setCellValueFactory(new PropertyValueFactory<>("behindTheLeader"));
        psLapTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lapTime"));
    }

    private void initTabs() {
        List<Start> starts = startDAO.getStartsByCompetitionDayId(MainPageController.currentCompetitionDay.getId());
        starts.forEach(start -> {
            if (start.getTab() == null) return;

            // Проверяем существует ли вкладка с данным ID
            TabDto tab = findTab(start.getTab());
            if (tab == null) {
                tab = new TabDto(start.getTab());
                openedTabs.add(tab);
                tab.getReferenceTab().setText(tab.getTabInfo().getName());
                createTableOnTab(tab.getReferenceTab());
                tabPane.getTabs().add(tab.getReferenceTab());
            }
            tab.getStarts().add(start);
        });
    }

    private void initTabAddButton() {
        Tab newTab = new Tab("+");
        newTab.setOnSelectionChanged(this::createNewTab);
        tabPane.getTabs().add(newTab);
        participantCompetitionTable.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                participantCompetitionTable.getSelectionModel().clearSelection();
            }
        });
    }

    private TabDto findTab(StartTab tab) {
        return openedTabs.stream().filter(el -> el.getTabInfo().getId() == tab.getId()).findFirst().orElse(null);
    }

    private void createNewTab(Event event) {
        StartTab startTab = startTabDAO.create(new StartTab());
        TabDto tab = new TabDto(startTab);
        tab.getReferenceTab().setText(startTab.getName());
        openedTabs.add(tab);

        createTableOnTab(tab.getReferenceTab());

        tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab.getReferenceTab());
        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
    }

    private void updateStartsTable(Event event) {
        Tab tab = (Tab) event.getSource();
        if (tab.isSelected()) {
            TabDto tabDto = openedTabs.stream().filter(el -> el.getReferenceTab().equals(tab)).findFirst().orElse(null);
            if (tabDto == null) return;
            startTable.getItems().clear();
            startTable.getItems().addAll(tabDto.getStarts());
        }
    }

    private void createTableOnTab(Tab tab) {
        TableView<ParticipantStartView> table = new TableView<>();
        table.getColumns().setAll(participantStartTable.getColumns());
        tab.setContent(table);
//        tab.setOnSelectionChanged(this::switchingTab);

        List<Long> startsIdOnTab = new ArrayList<>();
        table.getItems().addAll(participantDAO.getAllParticipantViewsByStarts(startsIdOnTab));

        tab.setOnSelectionChanged(this::updateStartsTable);
    }

    public void startButtonClick() {
        //Изменение цвета и текста кнопки при нажатии
        if (isButtonGreen) {
            startButton.setText("Стоп");
            timeline.play();
            startButton.getStyleClass().set(3, "btn-danger");

            // Для будущего использования потоков
            if (thread == null) {
                thread = new RFID("Potok dlya metok", this);
            }
            thread.threadResume();

            isButtonGreen = false;
        } else if (!isButtonGreen) {
            startButton.setText("Старт");
            startButton.getStyleClass().set(3, "btn-success");
            timeline.stop();
            thread.threadSuspend();
            isButtonGreen = true;
        }
    }

    private void buildNewEntityPC(Participant participant) {
        ParticipantCompetitionView participantCompetitionView = new ParticipantCompetitionView();

        participantCompetitionView.setName(participant.getSportsman().getName());
        participantCompetitionView.setLastname(participant.getSportsman().getLastname());
        participantCompetitionView.setPatronymic(participant.getSportsman().getPatronymic());
        participantCompetitionView.setGender(participant.getSportsman().getGender());
        participantCompetitionView.setChip(participant.getChip());
        participantCompetitionView.setStartNumber(participant.getStartNumber());
        participantCompetitionView.setBirthdate(participant.getSportsman().getBirthdate());
        participantCompetitionView.setRegion(participant.getSportsman().getRegion());
        participantCompetitionView.setGroup(participant.getGroup().getName());

        participantCompetitionTable.getItems().add(participantCompetitionView);
        participantCompetitionTable.refresh();

    }

    private void createEntityPC(ParticipantCompetitionView participantCompetitionView) {

    }

    private LocalTime calculateTimeOnDistance(LocalTime startTime) {
        LocalTime now = LocalTime.now();
        now = now.minusHours(startTime.getHour());
        now = now.minusMinutes(startTime.getMinute());
        now = now.minusSeconds(startTime.getSecond());
        return now;
    }

    private void buildNewEntityPS(Participant participant, int lap,TableView tab) {
        ParticipantStartView participantStartView = new ParticipantStartView(
                participant.getId(),
                LocalTime.now(),
                calculateTimeOnDistance(participant.getStart().getStartTime()),
                participant.getChip(),
                participant.getStartNumber(),
                participant.getSportsman().getLastname(),
                participant.getSportsman().getName(),
                participant.getGroup().getName(),
                lap,
                checkpointDAO.getParticipiantPlace(participant, lap)
        );



//        participantStartView.setId(participant.getId());
//        participantStartView.setCurrentTime(LocalTime.now());
//        participantStartView.setTimeOnDistance(calculateTimeOnDistance(participant.getStart().getStartTime()));
//        participantStartView.setChip(participant.getChip());
//        participantStartView.setStartNumber(participant.getStartNumber());
//        participantStartView.setLastname(participant.getSportsman().getLastname());
//        participantStartView.setName(participant.getSportsman().getName());
//        participantStartView.setGroup(participant.getGroup().getName());
//        participantStartView.setLap(lap);
//        participantStartView.setPlace(checkpointDAO.getParticipiantPlace(participant, lap));
//        participantStartView.setBehindTheLeader();
//        participantStartView.setLapTime();

        tab.getItems().add(participantStartView);
    }


    public void addNewCheakpoint(String chip) {

        Participant participant = participantDAO.getParticipantByChip(chip);

        //Поиск искомой вкладки
        TableView table = null;
        for (TabDto tab : openedTabs) {
            if (tab.getStarts().stream().anyMatch(start -> start.getId() == participant.getStart().getId())) {
                //Получение ссылки на таблицу внутри вкладки
                table = (TableView) tab.getReferenceTab().getContent();
                break;
            }
        }

        lastNumber.setText(Integer.toString(participant.getStartNumber()));
        int lap = checkpointDAO.getCountCheakpointByParticipiant(participant) + 1;
        checkpointDAO.create(new Checkpoint(participant, LocalTime.now(), lap));

        buildNewEntityPS(participant,lap,table);


        //Создает новую запись в табе "Участники"
//        ParticipantCompetitionView newPartitionCompetition = buildNewEntityPC(participant);
//        createEntityPC(newPartitionCompetition);
    }


    public void resetTimeButton(ActionEvent actionEvent) {
        stopwatch.setText("00:00:00:00");
        timing = 0;
        localTime = LocalTime.of(0, 0, 0, 0);
    }

    public void timeStartButton(ActionEvent actionEvent) {
        timeStarted.setText("В разработке");
        System.out.println(LocalTime.now());
    }
}