package com.lifehouse.raceth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


public class Main extends Application{
    public static AnnotationConfigApplicationContext appContext;

    public static void main(String[] args) {
        appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.lifehouse.raceth");
        appContext.refresh();
        launch(args);
        appContext.close();
        closeSocket();

//        session.close();
//        HibernateUtil.closeSessionFactory();
    }

    private static void closeSocket() {
        try {
            Process process = Runtime.getRuntime().exec("netstat -ano");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while((line = reader.readLine()) != null) {
                if (line.contains(Integer.toString(RFID.SERVICE_PORT))) {
                    break;
                }
            }
            RFID.threadFlag = false;
            if (line != "" && line != null) {
                String[] array = line.split(" ");
                Runtime.getRuntime().exec("taskkill /PID " + array[array.length-1] + " /F" );
            }
            if (RFID.getTagThread != null) {
                RFID.getTagThread.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));

//        Image imageOk = new Image(getClass().getResourceAsStream("123.jpg"));

        stage.setResizable(false); //Отключение изменения размеров окна
        AnchorPane pain = loader.load();
        stage.setScene(new Scene(pain));
        stage.getIcons().add(new Image("/assets/images/icons/more.png"));
        stage.show();
    }
}

