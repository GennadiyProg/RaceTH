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
//        HibernateUtil.closeSessionFactory();
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

