package com.lifehouse.raceth.logic.marksmonitorpage;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.dao.StartDAO;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.model.view.StartView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;

import java.net.URL;
import java.time.LocalTime;
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

    private StartDAO startDAO;

    private long chosenId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDAO = (StartDAO) Main.appContext.getBean("startDAO");
        initTable();
        initListeners();
        addValues();
    }

    private void initTable() {
        runTable.setEditable(true);
        selectionCheckboxColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        lapColumn.setCellValueFactory(new PropertyValueFactory<>("laps"));
    }

    private void initListeners() {
        runTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
        {
            lapTextField.setText(Integer.toString(newValue.getLaps()));
            timeTextField.setText(newValue.getStartTime().toString());
            chosenId = newValue.getId();
        });
    }

    private void addValues() {
        tableList = runTable.getItems();
        for (Start run : startDAO.getAllRuns()) {
            tableList.add(StartView.convertToView(run));
        }
        runTable.refresh();
    }

    @FXML
    void updateTable(ActionEvent event)  {
        StartView startView = null;
        for (StartView item : tableList) {
            if (item.getId() == chosenId) {
                startView = item;
                startView.setLaps(Integer.parseInt(lapTextField.getText()));
                startView.setStartTime(LocalTime.parse(timeTextField.getText()));
                break;
            }
        }

        if (startView != null) {
            Start run = StartView.convertToModel(startView);
            run.setLaps(Integer.parseInt(lapTextField.getText()));
            run.setStartTime(LocalTime.parse(timeTextField.getText()));
            startDAO.update(run);
            runTable.refresh();
        }
    }

    @FXML
    void saving(ActionEvent event) {
        try{
            closePopup(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        try{
            closePopup(event);
        } catch (Exception e) {
            System.out.println("can't loading");
        }
    }

    private void closePopup(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
    }

}


