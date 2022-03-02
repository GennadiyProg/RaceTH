package ru.corporateproduct.raceth;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.text.Text;
import liquibase.configuration.LiquibaseConfiguration;
import org.hibernate.Session;
import ru.corporateproduct.raceth.model.Test;

public class Main extends Application{

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Test test = new Test();

        session.save(test);
        session.getTransaction().commit();

        launch(args);
        session.close();
    }

    @Override
    public void start(Stage stage) {

        // установка надписи
        Text text = new Text("Hello my student work!");
        text.setLayoutY(80);    // установка положения надписи по оси Y
        text.setLayoutX(80);   // установка положения надписи по оси X

        Group group = new Group(text);

        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setTitle("JavaFX Application");
        stage.setWidth(300);
        stage.setHeight(250);
        stage.show();
    }
}