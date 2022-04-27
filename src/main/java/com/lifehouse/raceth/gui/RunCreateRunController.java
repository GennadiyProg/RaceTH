package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.CompetitionDayDAO;
import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.dao.StartDAO;
import com.lifehouse.raceth.gui.competitionpage.CompetitionPageController;
import com.lifehouse.raceth.model.CompetitionDay;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.Start;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Data
public class RunCreateRunController implements Initializable {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button cancelButt, saveButt;

    @FXML
    private DatePicker date;

    @FXML
    private ChoiceBox<Distance> distance;

    @FXML
    private ChoiceBox<Group> group;

    @FXML
    private TextField startName, laps, startTime;

    @FXML
    private ChoiceBox<CompetitionDay> competitionDay;

    private TableView<Start> startTable;
    private Start currentStart;
    private DistanceDAO distanceDAO;
    private GroupDAO groupDAO;
    private StartDAO startDAO;
    private CompetitionDayDAO competitionDayDAO;
    private DateTimeFormatter formatter;


    public void initialize(URL var1, ResourceBundle var2) {
        currentStart = null;
        distanceDAO = new DistanceDAO();
        groupDAO = new GroupDAO();
        startDAO = new StartDAO();
        competitionDayDAO = new CompetitionDayDAO();
        distance.setItems(FXCollections.observableList(new ArrayList<>(distanceDAO.getAllDistances())));
        group.setItems(FXCollections.observableList(new ArrayList<>(groupDAO.getAllGroups())));
        competitionDay.setItems(
                FXCollections.observableList(new ArrayList<>(competitionDayDAO.getAllByCompetition(CompetitionPageController.currentCompetition.getId())))
        );
        formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
    }

    @FXML
    void Saving(ActionEvent event) {
        Start newStart = buildNewEntity();

        if (currentStart != null) {
            updateEntity(newStart);
            close(event);
            return;
        }

        createEntity(newStart);
        close(event);
    }

    @FXML
    void close(ActionEvent event) {
        try{
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("cant loading");
        }
    }

    public void startEdit(Start start) {
        fillFieldsFromEntity(start);
        currentStart = start;
    }

    private Start buildNewEntity() {
        Start start = new Start();

        start.setName(startName.getText());
        start.setDistance(distanceDAO.getDistance(distance.getSelectionModel().getSelectedItem().getId()));
        start.setGroup(groupDAO.GetGroup(group.getSelectionModel().getSelectedItem().getId()));
        start.setStartTime(LocalTime.parse(startTime.getText(), formatter));
        start.setLaps(Integer.parseInt(laps.getText()));
        start.setCompetitionDay(competitionDay.getSelectionModel().getSelectedItem());

        return start;
    }

    private void createEntity(Start start) {
        start.setId(startTable.getItems().size());
        startDAO.create(start);
        startTable.getItems().add(start);
        startTable.refresh();
    }

    private void updateEntity(Start start) {
        startDAO.update(start);
        currentStart.setFields(start);
        startTable.refresh();
    }

    private void fillFieldsFromEntity(Start start) {
        startName.setText(start.getName());
        distance.setValue(start.getDistance());
        group.setValue(start.getGroup());
        startTime.setText(start.getStartTime().format(formatter));
        laps.setText(String.valueOf(start.getLaps()));
    }
}


