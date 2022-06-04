package com.lifehouse.raceth.logic.startpage;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.StartDAO;
import com.lifehouse.raceth.logic.MainPageController;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

@Data
public class StartPageController implements Initializable {
    @FXML
    private Button addButton; // -

    @FXML
    private Button editButton;

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

    private boolean hasSelectedStarts;

    private StartDAO startDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDAO = (StartDAO) Main.appContext.getBean("startDAO");
        defineStartsTable();
        loadStarts();
    }

    public void onMounted() { // хук, вызываемый при переходе на вкладку
        loadStarts();
    }

    private void loadStarts() {
        startTable.getItems().clear();

        if (MainPageController.currentCompetition == null) {
            return;
        }

        ObservableList<Start> starts = startTable.getItems();
        starts.addAll(startDAO.getStartsByCompetitionId(MainPageController.currentCompetition.getId()));
    }

    @FXML
    private void addRun(ActionEvent event) {
        if (MainPageController.currentCompetition == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Ошибка");
            alert.setHeaderText("Пожалуйста выберите текущее соревнование во вкладке 'Соревнования'");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/start/CreateStartPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            CreateStartPopupController createStartPopupController = fxmlLoader.getController();
            createStartPopupController.setStartTable(startTable);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editRun(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/start/CreateStartPopup.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL); //Блокирует основное окно, пока выведен попап.

            Start selectedStart = startTable.getSelectionModel().getSelectedItem();

            CreateStartPopupController createStartPopupController = fxmlLoader.getController();
            createStartPopupController.setStartTable(startTable);
            createStartPopupController.startEdit(selectedStart);

            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load");
        }
    }

    @FXML
    private void removeRows(ActionEvent event) {
        Start selectedStart = startTable.getSelectionModel().getSelectedItem();
        startTable.getItems().remove(selectedStart);
        startDAO.delete(selectedStart);
        startTable.refresh();
    }

    private void defineStartsTable() {
        namestartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        starttimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        countlapsColumn.setCellValueFactory(new PropertyValueFactory<>("laps"));
        compDayColumn.setCellValueFactory(new PropertyValueFactory<>("competitionDay"));
        startTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
