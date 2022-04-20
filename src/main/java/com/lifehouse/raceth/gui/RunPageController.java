package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.Gender;
import com.lifehouse.raceth.model.Sportsman;
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

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

@Data
public class RunPageController implements Initializable {
    @FXML
    private AnchorPane main_pane;
    @FXML
    private Button addButton;

    //Табличка
    @FXML
    private TableView<Sportsman> runTable;

    //todo: ДОРАБОТАТЬ МОДЕЛЬ УЧАСТНИКА И ПОМЕНЯТЬ МОДЕЛЬ СПОРТСМЕНА НА УЧАСТНИКА
    //Столбцы
    @FXML
    private TableColumn<Sportsman, String> lastnameColumn; // = new TableColumn<SportsmanDto, String>();
    @FXML
    private TableColumn<Sportsman, String> nameColumn; // = new TableColumn<SportsmanDto, String>();
    @FXML
    private TableColumn<Sportsman, String> birthdateColumn; // = new TableColumn<SportsmanDto, String>();
    @FXML
    private TableColumn<Sportsman, String> genderColumn; // = new TableColumn<SportsmanDto, String>();
    @FXML
    private TableColumn<Sportsman, String> regionColumn; // = new TableColumn<SportsmanDto, String>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<Sportsman, String>("lastname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Sportsman, String>("name"));
        birthdateColumn.setCellValueFactory(new PropertyValueFactory<Sportsman, String>("birthdate"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Sportsman, String>("gender"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<Sportsman, String>("region"));
        runTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    //todo: РЕАЛИЗОВАТЬ ЧТЕНИЕ С РЕАЛЬНЫХ ФАЙЛОВ (ЗДЕСЬ ФАЙЛЫ ГЕНЕРИРУЮТСЯ В ЦИКЛЕ)
    private void AddExternalData(ActionEvent event) {
        ObservableList<Sportsman> sportsmans = runTable.getItems();
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
        }
    }

    //Создание нового забега
    @FXML
    private void AddRun(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Run/RunCreateRunPopup.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    //todo: РЕАЛИЗОВАТЬ ОКНО ДОБАВЛЕНИЯ УЧАСТНИКА И ДОРАБОТАТЬ ЭТОТ МЕТОД
    private void AddRow(ActionEvent event) {
        //Sportsman sportsman = ВызовОкнаДобавленияСпортсмена();
        //runTable.getItems().add(sportsman);
    }

    @FXML
    //todo: РЕАЛИЗОВАТЬ ОКНО РЕДАКТИРОВАНИЯ УЧАСТНИКА
    private void UpdateRow(ActionEvent event) {
        Sportsman sportsman = runTable.getSelectionModel().getSelectedItem();
        //ВызовОкнаРедактирования(sportsman)
        runTable.refresh();
    }

    @FXML
    private void RemoveRows(ActionEvent event) {
        ObservableList<Sportsman> sportsmen = runTable.getSelectionModel().getSelectedItems();
        runTable.getItems().removeAll(sportsmen);
    }


}
