package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.Start;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;
import net.bytebuddy.implementation.bytecode.Throw;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public class RunPageController implements Initializable {
    @FXML
    private AnchorPane main_pane;
    @FXML
    private Button addButton;

    //Табличка
    @FXML
    private TableView<Start> startTable;

    //todo: ДОРАБОТАТЬ МОДЕЛЬ УЧАСТНИКА И ПОМЕНЯТЬ МОДЕЛЬ СПОРТСМЕНА НА УЧАСТНИКА
    //Столбцы
    @FXML
    private TableColumn<Start, String> namestartColumn;
    @FXML
    private TableColumn<Start, String> categoryColumn;
    @FXML
    private TableColumn<Start, String> distanceColumn;
    @FXML
    private TableColumn<Start, String> starttimeColumn;
    @FXML
    private TableColumn<Start, String> countlapsColumn;
    @FXML
    private TableColumn<Start, String> compDayColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        namestartColumn.setCellValueFactory(new PropertyValueFactory<Start, String>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Start, String>("group"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<Start, String>("distance"));
        starttimeColumn.setCellValueFactory(new PropertyValueFactory<Start, String>("startTime"));
        countlapsColumn.setCellValueFactory(new PropertyValueFactory<Start, String>("laps"));
        compDayColumn.setCellValueFactory(new PropertyValueFactory<Start, String>("competitionDay"));
        startTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    //todo: РЕАЛИЗОВАТЬ ЧТЕНИЕ С РЕАЛЬНЫХ ФАЙЛОВ (ЗДЕСЬ ФАЙЛЫ ГЕНЕРИРУЮТСЯ В ЦИКЛЕ)
    private void AddExternalData(ActionEvent event) {
        /*ObservableList<Sportsman> sportsmans = runTable.getItems();
        for (Integer i = 0; i < 15; i++)
        {
            Sportsman man = new Sportsman(
                    i,
                    "SampleSportsman" + i,
                    "SampleLastname" + i,
                    new java.sql.Date(Calendar.getInstance().getTime().getTime()),
                    Gender.MALE,
                    "RU"
                    );

            sportsmans.add(man);
        }*/
    }

    @FXML
    private void AddRun(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/RunCreateRunPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            RunCreateRunController runCreateRunController = fxmlLoader.getController();
            runCreateRunController.setStartTable(startTable);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editRun(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/RunCreateRunPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            Start selectedStart = startTable.getSelectionModel().getSelectedItem();

            RunCreateRunController runCreateRunController = fxmlLoader.getController();
            runCreateRunController.setStartTable(startTable);
            runCreateRunController.edit(selectedStart);

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    //todo: РЕАЛИЗОВАТЬ ОКНО ДОБАВЛЕНИЯ УЧАСТНИКА И ДОРАБОТАТЬ ЭТОТ МЕТОД
    private void AddRow(ActionEvent event) {
        //Start start = ВызовОкнаДобавленияСпортсмена();
        //runTable.getItems().add(sportsman);
    }

    @FXML
    //todo: РЕАЛИЗОВАТЬ ОКНО РЕДАКТИРОВАНИЯ УЧАСТНИКА
    private void UpdateRow(ActionEvent event) {
        Start start = startTable.getSelectionModel().getSelectedItem();
        //ВызовОкнаРедактирования(start)
        startTable.refresh();
    }

    @FXML
    private void RemoveRows(ActionEvent event) {
        ObservableList<Start> starts = startTable.getSelectionModel().getSelectedItems();
        startTable.getItems().removeAll(starts);
    }


}
