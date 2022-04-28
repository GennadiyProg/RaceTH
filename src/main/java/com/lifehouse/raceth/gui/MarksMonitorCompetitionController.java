package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.model.StartTab;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Data;
import javafx.scene.control.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

@Data
public class MarksMonitorCompetitionController implements Initializable {

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
    private Button startTime;


    @FXML
    void addingGroup(ActionEvent event) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    Stopwatch Stopwatch = new Stopwatch();
    public class Stopwatch implements ActionListener {

        JFrame frame = new JFrame();
        JButton startButton = new JButton("START");
        JButton resetButton = new JButton("RESET");
        JLabel timeLabel = new JLabel();
        int elapsedTime = 0; //3600000
        int millisecond = 0;
        int seconds = 0;
        int minutes = 0;
        boolean started = false;
        String milliseconds_string = String.format("%02d", millisecond);
        String seconds_string = String.format("%02d", seconds);
        String minutes_string = String.format("%02d", minutes);

        javax.swing.Timer timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                elapsedTime+= 120;
                minutes = (elapsedTime/3600000) % 60;
                seconds = (elapsedTime/60000) % 60;
                millisecond = (elapsedTime/1000) % 100;
                milliseconds_string = String.format("%02d", millisecond);
                seconds_string = String.format("%02d", seconds);
                minutes_string = String.format("%02d", minutes);
                timeLabel.setText(minutes_string + ":" + seconds_string + ":" + milliseconds_string);
            }
        });

        Stopwatch(){

            timeLabel.setText(minutes_string + ":" + seconds_string + ":" + milliseconds_string);
            timeLabel.setBounds(100,100,200,100);
            timeLabel.setFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN,35));
            timeLabel.setOpaque(true);
            timeLabel.setHorizontalAlignment(JTextField.CENTER);

            startButton.setBounds(100,200,100,50);
            startButton.setFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN,20));
            startButton.setFocusable(false);
            startButton.addActionListener(this);

            resetButton.setBounds(200,200,100,50);
            resetButton.setFont(new java.awt.Font("Verdana", Font.PLAIN,20));
            resetButton.setFocusable(false);
            resetButton.addActionListener(this);

            frame.add(startButton);
            frame.add(resetButton);
            frame.add(timeLabel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(420,420);
            frame.setLayout(null);
            frame.setVisible(true);
        }
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if(e.getSource() == startButton){
                start();
                if(started == false) {
                    started = true;
                    startButton.setText("STOP");
                    start();
                }
                else {
                    started = false;
                    startButton.setText("START");
                    stop();
                }
            }
            if(e.getSource() == resetButton){
                started = false;
                startButton.setText("START");
                reset();
            }
        }
        void start() {
            timer.start();
        }
        void stop() {
            timer.stop();
        }
        void reset() {
            timer.stop();
            elapsedTime = 0;
            millisecond = 0;
            seconds = 0;
            minutes = 0;

            milliseconds_string = String.format("%02d", millisecond);
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            timeLabel.setText(minutes_string + ":" + seconds_string + ":" + milliseconds_string);
        }
    }
}