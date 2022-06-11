package com.lifehouse.raceth.logic.marksmonitorpage;

import java.lang.*;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.*;
import com.lifehouse.raceth.finalprotocol.FinalProtocol;
import com.lifehouse.raceth.logic.MainPageController;
import com.lifehouse.raceth.model.*;
import com.lifehouse.raceth.model.view.ParticipantCompetitionView;
import com.lifehouse.raceth.model.view.ParticipantStartView;
import com.lifehouse.raceth.model.dto.TabDto;
import com.lifehouse.raceth.readingfiles.ExcelRead;
import com.lifehouse.raceth.rfid.RFID;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;
import javafx.scene.control.*;
import javafx.event.Event;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private Button startTimerButton;
    @FXML
    private Button startReadingButton;

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
    private TableColumn<ParticipantCompetitionView, String> pcClubColumn;
    @FXML
    private TableColumn<ParticipantCompetitionView, String> pcDischargeColumn;
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
    private TableColumn<ParticipantStartView, String> psStartNumberColumn;
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
    @FXML
    public ChoiceBox<CompetitionDay> competitionDay;

    private ParticipantDAO participantDAO;
    private CheckpointDAO checkpointDAO;
    private StartDAO startDAO;
    private StartTabDAO startTabDAO;
    private SportsmanDAO sportsmanDAO;
    private CompetitionDayDAO competitionDayDAO;
    private DistanceDAO distanceDAO;

    private List<TabDto> openedTabs;
    private Boolean isRunningReader = true;
    private RFID thread;

    TimerHandler timerHandler;

    ExcelRead excelRead = new ExcelRead(this);

    FinalProtocol finalProtocol = new FinalProtocol();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        participantDAO = (ParticipantDAO) Main.appContext.getBean("participantDAO");
        checkpointDAO = (CheckpointDAO) Main.appContext.getBean("checkpointDAO");
        startDAO = (StartDAO) Main.appContext.getBean("startDAO");
        startTabDAO = (StartTabDAO) Main.appContext.getBean("startTabDAO");
        sportsmanDAO = (SportsmanDAO) Main.appContext.getBean("sportsmanDAO");
        competitionDayDAO = (CompetitionDayDAO) Main.appContext.getBean("competitionDayDAO");
        distanceDAO = (DistanceDAO) Main.appContext.getBean("distanceDAO");
        openedTabs = new ArrayList<>();

        competitionDay.setOnAction(event -> {
            initTabs();
            initTabAddButton();
            updateStartsTable(participantTab);
        });

        setCompetitionDays();
        initStartTable();
        initParticipantTable();
        initStartTab();

        participantTab.setOnSelectionChanged(this::updateStartsTable);
        timerHandler = new TimerHandler(stopwatch, timeStarted, startTimerButton);
    }

    public void onMounted() {
        setCompetitionDays();
    }

    @FXML
    private void attachStart(ActionEvent event) {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        // Поиск нужного Dto экземпляра в
        TabDto currentTabDto = openedTabs
                .stream()
                .filter(tabDto -> tabDto.getReferenceTab().equals(currentTab))
                .findFirst().orElse(null);
        if (currentTab.equals(participantTab)) {
            alertWrongTab();
            return;
        }

        FXMLLoader popup = openPopup("/view/marksmonitor/MarksGroupPopup.fxml");
        if (popup == null) {
            System.out.println("Class: MarksMonitorCompetitionController\nMethod: attachStart\n Error opening popup,");
            return;
        }
        MarksGroupPopupController popupController = popup.getController();
        popupController.marksMonitorCompetitionController = this;
        popupController.assignCurrentTab(currentTabDto);
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
            } else {
                List<Start> starts = startDAO.getStartsByCompetitionDayId(competitionDay.getSelectionModel().getSelectedItem().getId());
                starts = starts.stream().filter(start -> newValue.getSportsman().getGender() == start.getGroup().getGender()).toList();
                int ageParticipant = LocalDate.now().getYear() - newValue.getSportsman().getBirthdate().getYear();
                Distance distance = controller.getDistanceChoiceBox().getValue();
                Start participantStart = starts.stream()
                        .filter(start -> ageParticipant < start.getGroup().getAgeTo() &&
                                ageParticipant > start.getGroup().getAgeFrom() &&
                                start.getDistance().getId() == distance.getId())
                        .findFirst().orElse(null);
                newValue.setStart(participantStart);
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
        pcClubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        pcDischargeColumn.setCellValueFactory(new PropertyValueFactory<>("discharge"));
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
        psStartNumberColumn.setCellValueFactory(el -> new SimpleStringProperty(Integer.toString(el.getValue().getStartNumber())));
        psLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        psNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        psGroupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        psLapColumn.setCellValueFactory(new PropertyValueFactory<>("lap"));
        psPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        psBehindTheLeaderColumn.setCellValueFactory(new PropertyValueFactory<>("behindTheLeader"));
        psLapTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lapTime"));

        psStartNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        psStartNumberColumn.setOnEditCommit(this::updateCheckpoint);
    }

    private void updateCheckpoint(CellEditEvent<ParticipantStartView, String> newParticipantNumber) {
        try {
            // Получение изменяемой отсечки (строки) из таблицы
            var currentCheckpointView = newParticipantNumber.getTableView().getSelectionModel().getSelectedItem();
            Checkpoint checkpoint = checkpointDAO.getCheckpoint(currentCheckpointView.getId());

            if (newParticipantNumber.getNewValue().equals("")) {
                currentCheckpointView.attachParticipant(null);
                checkpoint.setParticipant(null);
                checkpointDAO.update(checkpoint);
                participantStartTable.refresh();

                // Обновление таблицы с выбранной меткой
                newParticipantNumber.getTableView().refresh();
                return;
            }

            int newParticipantNum = Integer.parseInt(newParticipantNumber.getNewValue());

            TabDto currentTab = openedTabs.stream().filter(el -> el.getReferenceTab().equals(tabPane.getSelectionModel().getSelectedItem())).findFirst().orElse(null);
            if (currentTab == null) return;

            // В массив participants складываем всех участников из вкладки и среди них находим нужного
            var participants = new ArrayList<Participant>();
            for (var start : currentTab.getStarts()) {
                participants.addAll(participantDAO.getParticipantsByStart(start));
            }

            Participant participant = participants.stream().filter(p -> p.getStartNumber() == newParticipantNum).findFirst().orElse(null);
            if (participant == null) {
                currentCheckpointView.setStartNumber(-1);
                throw new Exception();
            }

            // Обновление информации о чекпоинте
            if (checkpoint.getLap() == 0) {
                checkpoint.setLap(checkpointDAO.getCountCheakpointByParticipiant(participant) + 1);
            }
            checkpoint.setParticipant(participant);
            checkpointDAO.update(checkpoint);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
            // Привязка участника за вьюхой и отсечкой
            currentCheckpointView.attachParticipant(participant);
            currentCheckpointView.setTimeOnDistance(LocalTime.parse(calculateTimeToNow(participant.getStart().getStartTime()).format(formatter)));
            currentCheckpointView.setLap(checkpoint.getLap());
            currentCheckpointView.setBehindTheLeader(LocalTime.parse(calculateTime(
                    checkpointDAO.getLastCheckpointByParticipant(participant).getCrossingTime(),
                    checkpointDAO.getLeaderOfGroup(checkpoint.getLap(), participant).getCrossingTime()).format(formatter)));
            currentCheckpointView.setLapTime(checkpoint.getLap() > 1
                    ? LocalTime.parse(calculateTimeToNow(checkpointDAO.getlastLapTime(participant, checkpoint.getLap() - 1)).format(formatter))
                    : LocalTime.parse(calculateTimeToNow(participant.getStart().getStartTime()).format(formatter)));

            // Обновление таблицы с выбранной меткой
            newParticipantNumber.getTableView().refresh();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Некорректный номер участника").show();
            newParticipantNumber.getTableView().refresh();
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Участник с выбранным номером не существует").show();
            newParticipantNumber.getTableView().refresh();
            e.printStackTrace();
        }
    }

    @FXML
    private void createCheckpoint() {
        Checkpoint checkpoint = new Checkpoint(null, LocalTime.now(), 0);
        checkpointDAO.create(checkpoint);

        var participantView = new ParticipantStartView(checkpoint.getId(), LocalTime.now());

        TableView<ParticipantStartView> table = (TableView<ParticipantStartView>) tabPane.getTabs().stream().filter(tab -> tab.equals(tabPane.getSelectionModel().getSelectedItem())).findFirst().orElse(null).getContent();
        if (table == null) return;
        table.getItems().add(participantView);
        table.refresh();
    }

    @FXML
    private void removeCheckpoint() {
        var table = (TableView<ParticipantStartView>) tabPane.getTabs().stream().filter(tab -> tab.equals(tabPane.getSelectionModel().getSelectedItem())).findFirst().orElse(null).getContent();
        var removableRow = table.getSelectionModel().getSelectedItem();
        checkpointDAO.removeCheckpoint(removableRow.getId());
        table.getItems().remove(removableRow);
    }

    private void initTabs() {
        openedTabs.clear();
        tabPane.getTabs().removeAll(tabPane.getTabs().stream().filter(tab -> !tab.equals(participantTab)).toList());

        List<Start> starts = startDAO.getStartsByCompetitionDayId(competitionDay.getValue().getId());
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
        newTab.setId("addNewTab");
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
        updateStartsTable((Tab) event.getSource());
    }

    public void updateStartsTable(Tab tab) {
//        Tab tab = (Tab) event.getSource();
        if (tab.isSelected()) {
            startTable.getItems().clear();

            if (tab.equals(participantTab)) {
                startTable.getItems().addAll(startDAO.getStartsByCompetitionDayId(competitionDay.getValue().getId()));
                return;
            }

            TabDto tabDto = openedTabs.stream().filter(el -> el.getReferenceTab().equals(tab)).findFirst().orElse(null);
            if (tabDto == null) return;
            startTable.getItems().addAll(tabDto.getStarts());
            startTable.refresh();
        }
    }

    private void createTableOnTab(Tab tab) {
        TableView<ParticipantStartView> table = new TableView<>();
//        table.getColumns().setAll(participantStartTable.getColumns());
        table.getColumns().setAll(copyStartColumns());
        table.setEditable(true);
        tab.setContent(table);
//        tab.setOnSelectionChanged(this::switchingTab);

        List<Long> startsIdOnTab = new ArrayList<>();
        table.getItems().addAll(participantDAO.getAllParticipantViewsByStart(startsIdOnTab));

        tab.setOnSelectionChanged(this::updateStartsTable);
    }

    private List<TableColumn<ParticipantStartView, ?>> copyStartColumns() {
        TableColumn<ParticipantStartView, Integer> psNumberColumn = new TableColumn<>("№");
        TableColumn<ParticipantStartView, LocalTime> psCurrentTimeColumn = new TableColumn<>("Время текущее");
        TableColumn<ParticipantStartView, LocalTime> psTimeOnDistanceColumn = new TableColumn<>("Время на дистанции");
        TableColumn<ParticipantStartView, String> psChipColumn = new TableColumn<>("Метка");
        TableColumn<ParticipantStartView, String> psStartNumberColumn = new TableColumn<>("Стартовый номер");
        TableColumn<ParticipantStartView, String> psLastnameColumn = new TableColumn<>("Фамилия");
        TableColumn<ParticipantStartView, String> psNameColumn = new TableColumn<>("Имя");
        TableColumn<ParticipantStartView, String> psGroupColumn = new TableColumn<>("Группа");
        TableColumn<ParticipantStartView, Integer> psLapColumn = new TableColumn<>("Круг");
        TableColumn<ParticipantStartView, Integer> psPlaceColumn = new TableColumn<>("Место");
        TableColumn<ParticipantStartView, LocalTime> psBehindTheLeaderColumn = new TableColumn<>("Отставание от лидера");
        TableColumn<ParticipantStartView, LocalTime> psLapTimeColumn = new TableColumn<>("Время круга");

        setColumnWidth(psNumberColumn, 25);
        setColumnWidth(psCurrentTimeColumn, 100);
        setColumnWidth(psTimeOnDistanceColumn, 130);
        setColumnWidth(psChipColumn, 70);
        setColumnWidth(psStartNumberColumn, 110);
        setColumnWidth(psLastnameColumn, 100);
        setColumnWidth(psNameColumn, 85);
        setColumnWidth(psGroupColumn, 80);
        setColumnWidth(psLapColumn, 50);
        setColumnWidth(psPlaceColumn, 50);
        setColumnWidth(psBehindTheLeaderColumn, 150);
        setColumnWidth(psLapTimeColumn, 110);

        psNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        psCurrentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("currentTime"));
        psTimeOnDistanceColumn.setCellValueFactory(new PropertyValueFactory<>("timeOnDistance"));
        psChipColumn.setCellValueFactory(new PropertyValueFactory<>("chip"));
        psStartNumberColumn.setCellValueFactory(el -> new SimpleStringProperty(Integer.toString(el.getValue().getStartNumber())));
        psLastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        psNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        psGroupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        psLapColumn.setCellValueFactory(new PropertyValueFactory<>("lap"));
        psPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        psBehindTheLeaderColumn.setCellValueFactory(new PropertyValueFactory<>("behindTheLeader"));
        psLapTimeColumn.setCellValueFactory(new PropertyValueFactory<>("lapTime"));

        psStartNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        psStartNumberColumn.setOnEditCommit(this::updateCheckpoint);

        return new ArrayList<>(Arrays.asList(
                psNumberColumn,
                psCurrentTimeColumn,
                psTimeOnDistanceColumn,
                psChipColumn,
                psStartNumberColumn,
                psLastnameColumn,
                psNameColumn,
                psGroupColumn,
                psLapColumn,
                psPlaceColumn,
                psBehindTheLeaderColumn,
                psLapTimeColumn
        ));
    }

    private void setColumnWidth(TableColumn column, double width) {
        column.setMaxWidth(5000);
        column.setMinWidth(10);
        column.setPrefWidth(width);
    }

    public void addNewCheckpoint(String chip) {
        Participant participant = participantDAO.getParticipantByChip(chip);
        if (checkpointDAO.getCountCheakpointByParticipiant(participant) == participant.getStart().getLaps()) return;
        int lap = checkpointDAO.getCountCheakpointByParticipiant(participant) + 1;
        if (participant == null) return;
        if (lap != 1) {
            if (ChronoUnit.SECONDS.between(checkpointDAO.getlastLapTime(participant, lap - 1), LocalTime.now()) < 10)
                return;
        }

        //Поиск искомой вкладки
        TableView<ParticipantStartView> table = null;
        for (TabDto tab : openedTabs) {
            if (tab.getStarts().stream().anyMatch(start -> start.getId() == participant.getStart().getId())) {
                //Получение ссылки на таблицу внутри вкладки
                table = (TableView<ParticipantStartView>) tab.getReferenceTab().getContent();
                break;
            }
        }

        lastNumber.setText(Integer.toString(participant.getStartNumber()));

        Checkpoint checkpoint = new Checkpoint(participant, LocalTime.now(), lap);

        checkpointDAO.create(checkpoint);

        if (table != null) buildNewEntityPS(participant, lap, table, checkpoint);
    }

    private void buildNewEntityPS(Participant participant, int lap, TableView<ParticipantStartView> tab, Checkpoint checkpoint) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        ParticipantStartView participantStartView = new ParticipantStartView(
                checkpoint.getId(), //id
                LocalTime.parse(LocalTime.now().format(formatter)), //текущее время
                LocalTime.parse(calculateTimeToNow(participant.getStart().getStartTime()).format(formatter)), //время на дистанции
                participant.getChip(), //чип
                participant.getStartNumber(), //стартовый номер
                participant.getSportsman().getLastname(), //фамилия
                participant.getSportsman().getName(), //имя
                participant.getStart().getGroup().getName(), //название группы
                lap, //круг
                checkpointDAO.getParticipantPlaceOfGroup(participant, lap), //место
                LocalTime.parse(calculateTime( //отставание от лидера
                        checkpointDAO.getLastCheckpointByParticipant(participant).getCrossingTime(),
                        checkpointDAO.getLeaderOfGroup(lap, participant).getCrossingTime()).format(formatter)
                ),
                lap > 1 //время круга
                        ? LocalTime.parse(calculateTimeToNow(checkpointDAO.getlastLapTime(participant, lap - 1)).format(formatter))
                        : LocalTime.parse(calculateTimeToNow(participant.getStart().getStartTime()).format(formatter))
        );
        tab.getItems().add(participantStartView);
    }

    private LocalTime calculateTime(LocalTime participant, LocalTime leader) {
        participant = participant.minusHours(leader.getHour());
        participant = participant.minusMinutes(leader.getMinute());
        participant = participant.minusSeconds(leader.getSecond());
        participant = participant.minusNanos(leader.getNano());
        return participant;
    }

    private LocalTime calculateTimeToNow(LocalTime startTime) {
        LocalTime now = LocalTime.now();
        now = now.minusHours(startTime.getHour());
        now = now.minusMinutes(startTime.getMinute());
        now = now.minusSeconds(startTime.getSecond());
        return now;
    }

    @FXML
    public void startWithTimerTimer() {
        timerHandler.schedule();
    }

    @FXML
    public void resetTimer() {
        timerHandler.reset();
    }

    @FXML
    public void startTimer() {
        timerHandler.startOrStop();
    }

    @FXML
    public void changeReadingStatus() {
        if (isRunningReader) {
            startReadingButton.setText("Остановить считывание");
            startReadingButton.getStyleClass().set(3, "btn-danger");

            // Для будущего использования потоков
            if (thread == null) {
                thread = new RFID("Potok dlya metok", this);
            }
            thread.threadResume();

            isRunningReader = false;
        } else {
            startReadingButton.setText("Начать считывание");
            startReadingButton.getStyleClass().set(3, "btn-success");
            thread.threadSuspend();
            isRunningReader = true;
        }
    }

    private void setCompetitionDays() {
        if (MainPageController.currentCompetition == null) {
            return;
        }
        competitionDay.setItems(FXCollections.observableList(new ArrayList<>(competitionDayDAO.getAllByCompetition(MainPageController.currentCompetition.getId()))));
        if (competitionDay.getValue() == null || competitionDay.getValue().getCompetition().getId() != MainPageController.currentCompetition.getId()) {
            competitionDay.setValue(competitionDay.getItems().get(0));
        }
    }

    public void addFileExcel() {
        excelRead.readExcel();
    }

    public void addNewParticipant(List<String> rowValue) {
        Sportsman sportsman = sportsmanDAO.getSportsmenByFioAndBirthdate(rowValue.get(0), rowValue.get(1), rowValue.get(2), LocalDate.parse(rowValue.get(6)));

        if (sportsman == null) {
            sportsmanDAO.create(new Sportsman(
                    rowValue.get(1),
                    rowValue.get(0),
                    rowValue.get(2),
                    LocalDate.parse(rowValue.get(6)),
                    Gender.valueOf(rowValue.get(3).toUpperCase(Locale.ROOT)),
                    rowValue.get(7),
                    rowValue.get(9)
            ));
            sportsman = sportsmanDAO.getSportsmenByFioAndBirthdate(rowValue.get(0), rowValue.get(1), rowValue.get(2), LocalDate.parse(rowValue.get(6)));
        }
        List<Start> starts = startDAO.getStartsByCompetitionDayId(competitionDay.getSelectionModel().getSelectedItem().getId());
        Sportsman finalSportsman = sportsman;
        starts = starts.stream().filter(start -> finalSportsman.getGender() == start.getGroup().getGender()).toList();
        int ageParticipant = LocalDate.now().getYear() - finalSportsman.getBirthdate().getYear();
        Distance distance = distanceDAO.getDistanceByLength(Integer.parseInt(rowValue.get(10)));
        Start participantStart = starts.stream()
                .filter(start -> ageParticipant < start.getGroup().getAgeTo() &&
                        ageParticipant > start.getGroup().getAgeFrom() &&
                        start.getDistance().getId() == distance.getId())
                .findFirst().orElse(null);
        if (participantStart == null) return;

        Participant participant = new Participant(
                buildChipString(rowValue.get(4)),
                finalSportsman,
                participantStart,
                Integer.parseInt(rowValue.get(5)),
                rowValue.get(8)
        );

        participantDAO.update(participant);

        buildNewEntityPC(participant, participantCompetitionTable);

    }

    private void buildNewEntityPC(Participant participant, TableView<ParticipantCompetitionView> participantCompetitionTable) {
        participantCompetitionTable.getItems().add(
                new ParticipantCompetitionView(
                        participant.getId(),
                        participant.getSportsman().getLastname(),
                        participant.getSportsman().getName(),
                        participant.getSportsman().getPatronymic(),
                        participant.getSportsman().getGender(),
                        participant.getChip(),
                        participant.getStartNumber(),
                        participant.getSportsman().getBirthdate(),
                        participant.getSportsman().getRegion(),
                        participant.getClub(),
                        participant.getSportsman().getDischarge(),
                        participant.getStart().getGroup().getName()
                )
        );
    }

    private String buildChipString(String chip) {
        if (chip.length() < 8) {
            char[] arr = new char[8 - chip.length()];
            Arrays.fill(arr, '0');
            return new String(arr) + chip;
        } else {
            return chip;
        }
    }

    public void printFinalProtocol() {
        try {
            finalProtocol.createFinalProtocol(checkpointDAO);
            System.out.println("Файл сформирован");
        } catch (IOException e) {
            System.out.print("Cant create final protocol:");
            System.out.println(e.getMessage());
        }
    }
}