package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.StartDAO;
import com.lifehouse.raceth.gui.competitionpage.CompetitionPageController;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Group;
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
import java.time.LocalTime;
import java.util.Date;
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
    private TableColumn<Start, Group> categoryColumn;
    @FXML
    private TableColumn<Start, Distance> distanceColumn;
    @FXML
    private TableColumn<Start, LocalTime> starttimeColumn;
    @FXML
    private TableColumn<Start, Integer> countlapsColumn;
    @FXML
    private TableColumn<Start, Date> compDayColumn;

    private StartDAO startDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDAO = new StartDAO();
        namestartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        starttimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        countlapsColumn.setCellValueFactory(new PropertyValueFactory<>("laps"));
        compDayColumn.setCellValueFactory(new PropertyValueFactory<>("competitionDay"));
        startTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void onUpdateSelectedCompetition() {
        startTable.getItems().clear();
        startTable.getItems().addAll(startDAO.getCompetitionsRuns(CompetitionPageController.currentCompetition.getId()));
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


                /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CompetitionPage.fxml"));
                Parent root2 = loader.load();
                CompetitionPageController competitionPageController = loader.getController();
                competitionPageController.getValue().addListener((observable, oldValue, newValue) -> onUpdateSelectedCompetition());*/



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
            runCreateRunController.startEdit(selectedStart);

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    private void RemoveRows(ActionEvent event) {
        Start selectedDistance = startTable.getSelectionModel().getSelectedItem();
        startTable.getItems().remove(selectedDistance);
        startTable.refresh();
    }

}
