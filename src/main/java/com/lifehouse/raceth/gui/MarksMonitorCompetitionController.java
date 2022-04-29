package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.rfid.RFID;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

@Data
public class MarksMonitorCompetitionController implements Initializable {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private TextField lastNumber;

    @FXML
    private Button startButton;

    //Кнопки ввода/вывода(желтые)
    @FXML
    private Button inputFromFileButton;
    @FXML
    private Button inputFromDBButton;
    @FXML
    private Button inputManuallyButton;
    @FXML
    private Button printProtocolButton;

    private Boolean isButtonGreen = true;

    private EventHandler<MouseEvent> startButtonPressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (isButtonGreen) {
                startButton.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 10px, 0, 0, 5px);" +
                        "-fx-background-color: #00C781;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-translate-y: 2px");
            } else {
                startButton.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 10px, 0, 0, 5px);" +
                        "-fx-background-color: #FF4040;" +
                        "-fx-background-radius: 15px;" +
                        "-fx-translate-y: 2px;");
            }
        }};


    //Ивенты на нажатие желтых кнопок
    private EventHandler<MouseEvent> inputFromFileButtonPressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            inputFromFileButton.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 10px, 0, 0, 5px);" +
                                         "-fx-background-color: #ffbb00;" +
                                         "-fx-background-radius: 8px;" +
                                         "-fx-translate-y: 2px");}};

    private EventHandler<MouseEvent> inputFromDBButtonPressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            inputFromDBButton.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 10px, 0, 0, 5px);" +
                                       "-fx-background-color: #ffbb00;" +
                                       "-fx-background-radius: 8px;" +
                                       "-fx-translate-y: 2px");}};

    private EventHandler<MouseEvent> inputManuallyButtonPressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            inputManuallyButton.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 10px, 0, 0, 5px);" +
                                         "-fx-background-color: #ffbb00;" +
                                         "-fx-background-radius: 8px;" +
                                         "-fx-translate-y: 2px");}};

    private EventHandler<MouseEvent> printProtocolButtonPressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            printProtocolButton.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 10px, 0, 0, 5px);" +
                                         "-fx-background-color: #ffbb00;" +
                                         "-fx-background-radius: 8px;" +
                                         "-fx-translate-y: 2px");}};


    //Ивенты на отпускание желтых кнопок
    private EventHandler<MouseEvent> inputFromFileButtonReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            inputFromFileButton.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 20px, 0, 0, 10px);" +
                                         "-fx-background-color: #ffbb00;" +
                                         "-fx-background-radius: 8px;");}};

    private EventHandler<MouseEvent> inputFromDBButtonReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            inputFromDBButton.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 20px, 0, 0, 10px);" +
                                       "-fx-background-color: #ffbb00;" +
                                       "-fx-background-radius: 8px;");}};

    private EventHandler<MouseEvent> inputManuallyButtonReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            inputManuallyButton.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 20px, 0, 0, 10px);" +
                                         "-fx-background-color: #ffbb00;" +
                                         "-fx-background-radius: 8px;");}};

    private EventHandler<MouseEvent> printProtocolButtonReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            printProtocolButton.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 20px, 0, 0, 10px);" +
                                         "-fx-background-color: #ffbb00;" +
                                         "-fx-background-radius: 8px;");}};




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.        setOnMousePressed(startButtonPressHandler);
        inputFromFileButton.setOnMousePressed(inputFromFileButtonPressHandler);
        inputFromDBButton.  setOnMousePressed(inputFromDBButtonPressHandler);
        inputManuallyButton.setOnMousePressed(inputManuallyButtonPressHandler);
        printProtocolButton.setOnMousePressed(printProtocolButtonPressHandler);

        inputFromFileButton.setOnMouseReleased(inputFromFileButtonReleaseHandler);
        inputFromDBButton.  setOnMouseReleased(inputFromDBButtonReleaseHandler);
        inputManuallyButton.setOnMouseReleased(inputManuallyButtonReleaseHandler);
        printProtocolButton.setOnMouseReleased(printProtocolButtonReleaseHandler);
    }


    public class getTagThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    lastNumber.setText(RFID.getTag());
                } catch(SocketException e) {

                }

            }
        }
    }

    //Обновление последнего номера при прохождении(пока по нажатию кнопки)
    public void updateLastNumber() {
//        Runnable task = () -> lastNumber.setText(RFID.getTag()); //Задача для потока
//        CompletableFuture.runAsync(task); //Запуск future-потока

        getTagThread getThread = new getTagThread();
        getThread.start();
    }
    getTagThread getThread = new getTagThread();

    public void startButtonClick() {
//        updateLastNumber(); //Включение потока на считывание данных с рамки

        //Изменение цвета и текста кнопки при нажатиее
        if(isButtonGreen) { //Если зеленая, поменять на красную
            startButton.setStyle("-fx-background-color: #FF4040;" +
                                 "-fx-background-radius: 15px;" +
                                 "-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 20px, 0, 0, 10px)");
            startButton.setText("Стоп");
            getThread.start();
            isButtonGreen = false;
        } else if (!isButtonGreen) { //Если красная, поменять на зеленую
            startButton.setStyle("-fx-background-color: #00C781;" +
                                 "-fx-background-radius: 15px;" +
                                 "-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 20px, 0, 0, 10px)");
            startButton.setText("Старт");
            getThread.interrupt();
            isButtonGreen = true;
        }



    }


}