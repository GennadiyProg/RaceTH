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

    private Boolean isButtonGreen = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        if(isButtonGreen) {
            startButton.setText("Стоп");
            getThread.start();
            isButtonGreen = false;
        } else if (!isButtonGreen) {
            startButton.setText("Старт");
            getThread.stop();
//            getThread.interrupt();
            isButtonGreen = true;
        }



    }


}