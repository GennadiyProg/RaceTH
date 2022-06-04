package com.lifehouse.raceth.logic.marksmonitorpage;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.StartDAO;
import com.lifehouse.raceth.logic.MainPageController;
import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.model.dto.TabDto;
import com.lifehouse.raceth.model.view.StartView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

/*
⠄⡄⡆⡄⠄⠄⠄⠄⠄⢀⡤⠄⠒⠄⠄⠄⣄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⣄⣠⠄
⢱⣼⣿⠇⢀⠄⠄⠄⡰⠅⢀⣴⣾⡿⠿⢷⠝⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⣿⣿⡝
⠸⣿⣿⠗⠋⠄⠄⠠⠂⠄⣿⣿⡧⢒⣂⣼⣿⣄⡚⡄⠄⠄⠄⠄⠄⠈⠲⣿⣿⡯
⠄⣿⡿⠄⠄⠄⠄⠄⠄⠄⣿⣿⣿⣿⡏⣩⣤⣵⡠⡺⡄⠄⠄⠄⠄⠄⠄⢸⣿⠃
⠄⣿⣿⠄⠄⠄⠄⠄⢀⢀⡘⣿⣿⣿⣧⠋⠖⠚⠂⢹⣿⠄⠄⠄⠄⠄⠄⣾⣿⠄
⠄⣿⣷⡇⠄⠄⠄⠄⢁⣏⣷⣿⣿⣿⣿⣿⣿⣟⡲⠾⢻⠄⠄⠄⠄⠄⣸⣿⡟⠄
⠄⢹⣿⣿⡀⠄⠄⠄⠄⠉⢫⣼⣿⣿⣿⣿⣿⠟⠓⠓⡘⠄⠄⠄⠄⣰⣿⣿⠁⠄
⠄⠈⠿⠿⠿⠄⠄⠄⠄⠄⠄⠻⠿⠿⠿⠟⠉⠄⠄⠹⠇⠄⠄⠄⠺⠿⠿⠏⠄⠄
 */

@Data
public class MarksGroupPopupController implements Initializable {

    @FXML
    private Button addInTable;
    @FXML
    private TextField lapTextField;
    @FXML
    private TextField timeTextField;

    @FXML
    private TableView<StartView> runTable;
    private ObservableList<StartView> tableList;

    @FXML
    private TableColumn<StartView, Boolean> selectionCheckboxColumn;
    @FXML
    private TableColumn<StartView, String> numberColumn;
    @FXML
    private TableColumn<StartView, String> groupColumn;
    @FXML
    private TableColumn<StartView, String> startTimeColumn;
    @FXML
    private TableColumn<StartView, String> lapColumn;

    private TabDto currentTab;
    private StartDAO startDAO;
    private long chosenId;
    public MarksMonitorCompetitionController marksMonitorCompetitionController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDAO = (StartDAO) Main.appContext.getBean("startDAO");
        initTable();
        initListeners();
    }

    private void initTable() {
        runTable.setEditable(true);
        selectionCheckboxColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        numberColumn.setCellValueFactory(start -> new SimpleStringProperty(Long.toString(start.getValue().getStart().getId())));
        groupColumn.setCellValueFactory(start -> new SimpleStringProperty(start.getValue().getStart().getGroup().toString()));
        startTimeColumn.setCellValueFactory(start -> new SimpleStringProperty(start.getValue().getStart().getStartTime().toString()));
        lapColumn.setCellValueFactory(start -> new SimpleStringProperty(Integer.toString(start.getValue().getStart().getLaps())));
    }

    private void initListeners() {
        runTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
        {
            lapTextField.setText(Integer.toString(newValue.getStart().getLaps()));
            timeTextField.setText(newValue.getStart().getStartTime().toString());
            chosenId = newValue.getStart().getId();
        });
    }

    // НЕ ВЫЗЫВАТЬ ИЗ КОНСТРУКТОРА И initialize()
    public void assignCurrentTab(TabDto tab) {
        this.currentTab = tab;
        initializeRecords();
    }

    // ЭТО ТОЖЕ НЕ ВЫЗЫВАТЬ ИЗ КОНСТРУКТОРА И initialize()
    private void initializeRecords() {
        tableList = runTable.getItems();
        List<Start> starts = startDAO.getStartsByCompetitionDayId(marksMonitorCompetitionController.competitionDay.getValue().getId());
        for (Start run : starts) {
            if (run.getTab() != null && run.getTab().getId() != currentTab.getTabInfo().getId())
                continue;

            StartView startView = new StartView(run);
            if (run.getTab() != null && run.getTab().getId() == currentTab.getTabInfo().getId()) {
                startView.getCheckBox().setSelected(true);
            }

            tableList.add(startView);
        }
        runTable.refresh();
    }

    @FXML
    private void updateRecord(ActionEvent event)  {
        StartView startView = null;
        for (StartView item : tableList) {
            if (item.getStart().getId() == chosenId) {
                startView = item;
                startView.getStart().setLaps(Integer.parseInt(lapTextField.getText()));
                startView.getStart().setStartTime(LocalTime.parse(timeTextField.getText()));
                break;
            }
        }

        if (startView != null) {
            Start run = startView.getStart();
            run.setLaps(Integer.parseInt(lapTextField.getText()));
            run.setStartTime(LocalTime.parse(timeTextField.getText()));
            startDAO.update(run);
            runTable.refresh();
        }
    }

    @FXML
    private void saving(ActionEvent event) {
        try{
            updateStartToTabAttachments();
            closePopup(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStartToTabAttachments() {
        for (StartView startView : tableList) {
            if (startView.getCheckBox().isSelected() && !currentTab.contains(startView)) {
                startView.getStart().setTab(currentTab.getTabInfo());
                currentTab.getStarts().add(startView.getStart());
                startDAO.update(startView.getStart());
            }

            if (!startView.getCheckBox().isSelected() && currentTab.contains(startView)) {
                startView.getStart().setTab(null);
                currentTab.removeStart(startView.getStart());
                startDAO.update(startView.getStart());
            }
        }

        marksMonitorCompetitionController.updateStartsTable(currentTab.getReferenceTab());
    }

    private void containStart(TabDto tab) {

    }

    @FXML
    private void cancel(ActionEvent event) {
        try{
            closePopup(event);
        } catch (Exception e) {
            System.out.println("Class: MarksGroupPopupController\n" +
                               "Method: cancel\n" +
                               "Error happened while closing the popup");
        }
    }

    private void closePopup(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
    }

}


