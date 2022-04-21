package com.lifehouse.raceth.gui.competitionpage.impl;

import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.gui.competitionpage.CompetitionPageElementService;
import com.lifehouse.raceth.gui.competitionpage.popups.DistancePopupController;
import com.lifehouse.raceth.model.Distance;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DistanceServiceImpl implements CompetitionPageElementService {
    private final DistanceDAO distanceDAO;
    private final TableView<Distance> distanceTable;

    public DistanceServiceImpl(TableView<Distance> distanceTable) {
        this.distanceDAO = new DistanceDAO();
        this.distanceTable = distanceTable;
    }

    @Override
    public void create() {
        DistancePopupController controller = createDistancePopup();
        if(controller == null) return;
        controller.getNewDistance().addListener((observable, oldValue, newValue) -> {
            distanceDAO.create(newValue);
            distanceTable.getItems().add(newValue);
            distanceTable.refresh();
        });
    }

    @Override
    public void edit() {
        Distance distance = distanceTable.getSelectionModel().getSelectedItem();
        if (distance == null){
            return;
        }
        DistancePopupController controller = createDistancePopup();
        if (controller == null) return;
        controller.edit(distance);
        controller.getNewDistance().addListener((observable, oldValue, newValue) -> {
            distanceDAO.update(newValue);
            distanceTable.getSelectionModel().getSelectedItem().setFields(newValue);
            distanceTable.refresh();
        });
    }

    @Override
    public void delete() {
        Distance distance = distanceTable.getSelectionModel().getSelectedItem();
        if (distance == null){
            return;
        }
        distanceTable.getItems().removeAll(distance);
        distanceDAO.delete(distance);
    }

    private DistancePopupController createDistancePopup(){
        FXMLLoader fxmlLoader = loadResources();
        if (fxmlLoader == null){
            return null;
        }
        DistancePopupController distancePopupController = fxmlLoader.getController();
        distancePopupController.setDistanceTable(distanceTable);
        return distancePopupController;
    }

    private FXMLLoader loadResources() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DistancePopup.fxml"));
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        return fxmlLoader;
    }
}
