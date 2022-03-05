package ru.corporateproduct.raceth;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.text.Text;
import liquibase.Liquibase;
import org.hibernate.Session;
import ru.corporateproduct.raceth.model.Chip;
import ru.corporateproduct.raceth.model.Participant;
import ru.corporateproduct.raceth.model.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Main extends Application{

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Test test = new Test(12,"danya");
//        Chip chip = new Chip(1,0001);
//        Participant participant = new Participant(1,chip);
//Fixme: Ген, ничего не работает(((((((((
//        При добавлении mappingа на другие папки в конфиг оно просто игнорирует новые классы
//        Если оставлять 1 mapping с любым классом, он просто создает таблицу с названием класса
//        Также он создает таблицу даже если тут все в коментариях и только сессия открывается
        session.save(test);
//        session.save(chip);
//        session.save(participant);
        session.getTransaction().commit();

        launch(args);

        session.close();
    }

    @Override
    public void start(Stage stage) throws IOException {
//        File f = new File("filea.txt");
//        System.out.println(f.getAbsolutePath());
//        Properties p = new Properties();
//        p.load(new FileReader("src/main/resources/prop.properties"));
//        System.out.println(p.getProperty("a"));
//        Connection conn = DriverManager.getConnection("url", )
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