package com.lifehouse.raceth.logic.startpage;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.CompetitionDayDAO;
import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.dao.GroupDAO;
import com.lifehouse.raceth.dao.StartDAO;
import com.lifehouse.raceth.logic.MainPageController;
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
public class CreateStartPopupController implements Initializable {
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
        distanceDAO = (DistanceDAO) Main.appContext.getBean("distanceDAO");
        groupDAO = (GroupDAO) Main.appContext.getBean("groupDAO");
        startDAO = (StartDAO) Main.appContext.getBean("startDAO");
        competitionDayDAO = (CompetitionDayDAO) Main.appContext.getBean("competitionDayDAO");
        distance.setItems(FXCollections.observableList(new ArrayList<>(distanceDAO.getCurrentCompetitionDistances())));
        group.setItems(FXCollections.observableList(new ArrayList<>(groupDAO.getCurrentCompetitionGroups())));
        competitionDay.setItems(FXCollections.observableList(new ArrayList<>(competitionDayDAO.getAllByCompetition(MainPageController.currentCompetition.getId()))));
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
        start.setGroup(groupDAO.getGroup(group.getSelectionModel().getSelectedItem().getId()));
        start.setLaps(Integer.parseInt(laps.getText()));
        start.setCompetitionDay(competitionDay.getSelectionModel().getSelectedItem());


        //start.setStartTime(LocalTime.parse(startTime.getText(), formatter));
        if (startTime.getText().length() == 8) {
            String g = startTime.getText();
            LocalTime localTime = LocalTime.parse(g, formatter);
            start.setStartTime(localTime);
        }
        else if (startTime.getText().length() == 5) {
            String s = startTime.getText() + ":00";
            LocalTime localTime = LocalTime.parse(s,formatter);
            start.setStartTime(localTime);
        }
        //String s = startTime.getText() + ":00";
        //formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        //LocalTime localTime = LocalTime.parse(startTime.getText() + ":00", formatter);

        //localTime.format(formatter);
        //localTime.format(formatter);
        //start.setStartTime(localTime);

        return start;
    }

    private void createEntity(Start start) {
        // start.setId(startTable.getItems().size());
        startDAO.create(start);
        startTable.getItems().add(start);
        startTable.refresh();
    }

    private void updateEntity(Start start) {
        currentStart.setFields(start);
        startDAO.update(currentStart);
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


