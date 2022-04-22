package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.RunDAO;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.Run;
import com.lifehouse.raceth.model.RunDto;
import javafx.beans.value.ChangeListener;
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
    private TableView<RunDto> runTable;

    private ObservableList<RunDto> tableList;

    @FXML
    private TableColumn<RunDto, String> selectionCheckboxColumn;

    @FXML
    private TableColumn<RunDto, String> numberColumn;

    @FXML
    private TableColumn<RunDto, String> groupColumn;

    @FXML
    private TableColumn<RunDto, String> startTimeColumn;

    @FXML
    private TableColumn<RunDto, String> lapColumn;

    private RunDAO runDAO;

    private long chosenId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        runDAO = new RunDAO();

        selectionCheckboxColumn.setCellValueFactory(new PropertyValueFactory<>("check"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        lapColumn.setCellValueFactory(new PropertyValueFactory<>("laps"));

        tableList = runTable.getItems();
        for (Run run : runDAO.getAllRuns()) {
            tableList.add(RunDto.convertToDto(run));
        }

        runTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends RunDto> observableValue, RunDto runDto, RunDto t1) {
                lapTextField.setText(Integer.toString(t1.getLaps()));
                timeTextField.setText(t1.getTime().toString());
                chosenId = t1.getId();
                System.out.println((runTable.getFocusModel().getFocusedCell()));
            }
        });

        //
        // TODO: ВРЕМЕННЫЙ КОД ДЛЯ ДЕМОНСТРАЦИИ ОТОБРАЖЕНИЯ КОДА
        //
        int i = 0;
        while (i < 5) {
            i++;
            RunDto run = new RunDto();
            run.setId(i);
            run.setTime(LocalTime.now());
            Group group = new Group();
            group.setName("Group" + i);
            run.setGroup(group);
            run.setLaps(i);
            runTable.getItems().add(run);
            runDAO.create(RunDto.convertToRun(run));
        }
        runTable.refresh();
    }

    @FXML
    void updateTable(ActionEvent event)  {
        RunDto runDto = null;
        for (RunDto item : tableList) {
            if (item.getId() == chosenId) {
                runDto = item;
                runDto.setLaps(Integer.parseInt(lapTextField.getText()));
                runDto.setTime(LocalTime.parse(timeTextField.getText()));
                break;
            }
        }

        if (runDto != null) {
            Run run = RunDto.convertToRun(runDto);
            run.setLaps(Integer.parseInt(lapTextField.getText()));
            run.setTime(LocalTime.parse(timeTextField.getText()));
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


