package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.model.Start;
import com.sun.media.jfxmedia.AudioClip;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Data;
import javafx.scene.control.*;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimerTask;

@Data
public class MarksMonitorCompetitionController<timer> implements Initializable {

    @FXML
    private Button addGroup;

    @FXML
    private  TabPane tabPane;

    @FXML
    private AnchorPane main_pane;

    @FXML
    private TableView<Checkpoint> checkpointTable;

    @FXML
    private TableView<Start> runTable;

    @FXML
    private TableColumn<Start, String> groupColumn;

    @FXML
    private TableColumn<Start, String> startTimeColumn;

    @FXML
    private TableColumn<Start, String> lapColumn;

    @FXML
    private TextField stopwatch;

    @FXML
    void addingGroup(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MarksGroupPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }
// Таймер
    Timeline timeline = new Timeline(
            new KeyFrame(
                    Duration.millis(1000),
                    ae -> {
                        Date dateNow = new Date();
                        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm:ss");
                        System.out.println(formatForDateNow.format(dateNow));
                        stopwatch.setText(formatForDateNow.format(dateNow));
                    }
            )
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        timeline.setCycleCount(Timeline.INDEFINITE);

            groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            lapColumn.setCellValueFactory(new PropertyValueFactory<>("laps"));

            Tab tab = new Tab("Забег " + tabPane.getTabs().size());
            tab.setClosable(true);
            TableView tableView = new TableView();
            tableView.setPrefWidth(1072);
            tableView.setPrefHeight(318);
            TableColumn number = new TableColumn("№");
            number.setPrefWidth(50);
            TableColumn participant = new TableColumn("Участник");
            participant.setPrefWidth(100);
            TableColumn gender = new TableColumn("Пол");
            gender.setPrefWidth(50);
            TableColumn chip = new TableColumn("Чип");
            chip.setPrefWidth(130);
            TableColumn start_number = new TableColumn("Стартовый номер");
            start_number.setPrefWidth(130);
            TableColumn date_birth = new TableColumn("Дата рождения");
            date_birth.setPrefWidth(130);
            TableColumn city = new TableColumn("Город");
            city.setPrefWidth(120);
            TableColumn club = new TableColumn("Клуб");
            club.setPrefWidth(120);
            TableColumn category = new TableColumn("Разряд");
            category.setPrefWidth(120);
            TableColumn group = new TableColumn("Группа");
            group.setPrefWidth(120);
            tableView.getColumns().addAll(number,participant,gender,chip,start_number,date_birth,city,
                    club,category,group);

            // add tableview to the tab
            tab.setContent(tableView);

            tabPane.getTabs().add(tab);

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
// Методы для кнопок
    public void startTimeButton(javafx.event.ActionEvent actionEvent) {
        timeline.play();
    }
    public void stopTimeButton(javafx.event.ActionEvent actionEvent) {
        timeline.stop();
    };
    public void resetTimeButton(javafx.event.ActionEvent actionEvent) {
//
    };

    // Пока просто существуют, в дальнейшем - уберется
//    public class Stopwatch implements ActionListener {
//        int elapsedTime = 0; //3600000
//        int millisecond = 0;
//        int seconds = 0;
//        int minutes = 0;
//        boolean started = false;
//        String milliseconds_string = String.format("%02d", millisecond);
//        String seconds_string = String.format("%02d", seconds);
//        String minutes_string = String.format("%02d", minutes);
//        Timer timer = new Timer(1, new ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                elapsedTime+= 127;
//                minutes = (elapsedTime/3600000) % 60;
//                seconds = (elapsedTime/60000) % 60;
//                millisecond = (elapsedTime/600) % 100;
//                milliseconds_string = String.format("%02d", millisecond);
//                seconds_string = String.format("%02d", seconds);
//                minutes_string = String.format("%02d", minutes);
//                stopwatch.setText(minutes_string + ":" + seconds_string + ":" + milliseconds_string);
}