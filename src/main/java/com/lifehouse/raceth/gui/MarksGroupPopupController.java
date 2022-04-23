package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.RunDAO;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.modeldto.StartDto;
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
    private TableView<StartDto> runTable;

    private ObservableList<StartDto> tableList;

    @FXML
    private TableColumn<StartDto, Boolean> selectionCheckboxColumn;

    @FXML
    private TableColumn<StartDto, String> numberColumn;

    @FXML
    private TableColumn<StartDto, String> groupColumn;

    @FXML
    private TableColumn<StartDto, String> startTimeColumn;

    @FXML
    private TableColumn<StartDto, String> lapColumn;

    private RunDAO runDAO;

    private long chosenId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        runDAO = new RunDAO();
        initTable();
        initListeners();
        addValues();
    }

    private void initTable() {
        runTable.setEditable(true);
        selectionCheckboxColumn.setCellValueFactory(new PropertyValueFactory<>("check"));
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
        for (Start run : runDAO.getAllRuns()) {
            tableList.add(StartDto.convertToDto(run));
        }

        //
        // TODO: ВРЕМЕННЫЙ КОД ДЛЯ ТЕСТОВЫХ ДАННЫХ
        //
        int i = 0;
        while (i < 5) {
            i++;
            StartDto run = new StartDto();
            run.setId(i);
            run.setStartTime(LocalTime.now());
            Group group = new Group();
            group.setName("Group" + i);
            run.setGroup(group);
            run.setLaps(i);
            run.getCheck().selectedProperty().addListener((observableValue, oldValue, newValue) ->
                System.out.println("Hi, I'm a test string " + newValue)
            );
            runTable.getItems().add(run);
            runDAO.create(StartDto.convertToStart(run));
        }
        runTable.refresh();
        //
        //
        //
    }

    @FXML
    void updateTable(ActionEvent event)  {
        StartDto startDto = null;
        for (StartDto item : tableList) {
            if (item.getId() == chosenId) {
                startDto = item;
                startDto.setLaps(Integer.parseInt(lapTextField.getText()));
                startDto.setStartTime(LocalTime.parse(timeTextField.getText()));
                break;
            }
        }

        if (startDto != null) {
            Start run = StartDto.convertToStart(startDto);
            run.setLaps(Integer.parseInt(lapTextField.getText()));
            run.setStartTime(LocalTime.parse(timeTextField.getText()));
            runDAO.update(run);
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


