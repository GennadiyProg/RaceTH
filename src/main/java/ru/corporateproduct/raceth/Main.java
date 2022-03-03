package ru.corporateproduct.raceth;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.text.Text;
import ru.corporateproduct.raceth.usbreader.UsbReader;

import javax.usb.UsbException;
import java.io.UnsupportedEncodingException;

public class Main extends Application{

    public static void main(String[] args) throws UsbException {
        UsbReader usbReader = new UsbReader((short) 0x09da, (short) 0xf613);
        usbReader.main();
//        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // установка надписи
        Text text = new Text("Sample");
        text.setLayoutY(80);    // установка положения надписи по оси Y
        text.setLayoutX(80);   // установка положения надписи по оси X

        Group group = new Group(text);

        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setTitle("JavaFX Application");
        stage.setWidth(500);
        stage.setHeight(250);
        stage.show();
    }
}

