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
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
/*
    public class Sample1 extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            Scene scene = new Scene(new Timer1(), 300, 200);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public class Timer1 extends VBox {

        public Timer1() {
            Text time = new Text();
            Button startButton = new Button("Start");
            Button stopButton = new Button("Stop");
            getChildren().addAll(time, startButton, stopButton);
            startButton.setOnAction(startEvt -> {
                Task<Integer> timerFxTask = new Task<>() {

                    {
                        updateValue(0);
                    }

                    @Override
                    protected Integer call() throws Exception {
                        for (int counter = 0; counter <= 1000; counter++) {
                            sleep(1000);
                            updateValue(counter);
                        }
                        return 1000;
                    }
                };
                stopButton.setOnAction(stopEvt -> timerFxTask.cancel());
                time.textProperty().bind(Bindings.createStringBinding(() -> timerFxTask.getValue().toString(),
                        timerFxTask.valueProperty()));
                Thread timerThread = new Thread(timerFxTask);
                timerThread.start();
            });
        }
    }

    public class Sample2 extends Application
    {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            Scene scene = new Scene(new Timer2(), 300, 200);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public class Timer2 extends VBox {

        public Timer2() {
            Text time = new Text();
            Button startButton = new Button("Start");
            Button stopButton = new Button("Stop");
            getChildren().addAll(time, startButton, stopButton);
            startButton.setOnAction(startEvt -> {
                IntegerProperty counter = new SimpleIntegerProperty(0);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1000), new KeyValue(counter, 1000)));
                stopButton.setOnAction(stopEvt -> timeline.stop());
                time.textProperty().bind(Bindings.createStringBinding(() -> Integer.toString(counter.get()), counter));
                timeline.play();
            });
        }
    }*/

/*public class Dispatcher extends Application {
    GraphicsContext gc ;
    Timer t;
    Button btn;
    int i=1;
    public static void main(String[] args) {
        launch(args);
    }
    public void start (Stage myStage){
        myStage.setTitle("Game");
        FlowPane rootNode = new FlowPane();
        rootNode.setAlignment(Pos.CENTER);
        Scene myScene = new Scene(rootNode,1000,900);
        myStage.setScene(myScene);
        Canvas myCanvas = new Canvas(900,800);
        gc = myCanvas.getGraphicsContext2D();
        final TimerTask animation = new TimerTask() {
            public void run() {
                gc.setFill(Color.WHITE);
                gc.fillRect(0,0,900,800 );
                i++;
                gc.setFill(Color.BLACK);
                gc.fillOval(i ,i+1 ,i ,i+1 );
            }
        };
        t = new Timer();
        Button btn = new Button("0");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                t.schedule(animation, 0, 10);
            }
        });
        rootNode. getChildren ().addAll(myCanvas, btn);
        myStage.show ( ) ;
    }
}
*/

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
}