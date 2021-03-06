package com.lifehouse.raceth.logic.competitionpage.impl;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.logic.competitionpage.CompetitionPageElementService;
import com.lifehouse.raceth.logic.competitionpage.popups.DistancePopupController;
import com.lifehouse.raceth.model.view.DistanceView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DistanceServiceImpl implements CompetitionPageElementService {
    private final DistanceDAO distanceDAO;
    private final TableView<DistanceView> distanceTable;
    private DistanceView lastEditedDistance;

    public DistanceServiceImpl(TableView<DistanceView> distanceTable) {
        this.distanceDAO = (DistanceDAO) Main.appContext.getBean("distanceDAO");
        this.distanceTable = distanceTable;
    }

    @Override
    public void create() {
        DistancePopupController controller = createDistancePopup();
        if(controller == null) return;
        controller.getNewDistance().addListener((observable, oldValue, newValue) -> {
            distanceDAO.create(newValue);
            lastEditedDistance = DistanceView.convertToView(newValue);
            distanceTable.getItems().add(lastEditedDistance);
            distanceTable.refresh();
        });
    }

    @Override
    public void edit() {
        DistanceView distance = distanceTable.getSelectionModel().getSelectedItem();
        if (distance == null){
            return;
        }
        DistancePopupController controller = createDistancePopup();
        if (controller == null) return;
        controller.edit(distance);
        controller.getNewDistance().addListener((observable, oldValue, newValue) -> {
            newValue.setId(distance.getId());
            newValue.setCompetitions(distance.getCompetitions());
            distanceDAO.update(newValue);
            distanceTable.getSelectionModel().getSelectedItem().setFields(DistanceView.convertToView(newValue));
            distanceTable.refresh();
        });
    }

    @Override
    public void delete() {
        DistanceView distanceView = distanceTable.getSelectionModel().getSelectedItem();
        if (distanceView == null){
            return;
        }
        distanceTable.getItems().removeAll(distanceView);
        distanceDAO.delete(DistanceView.convertToModel(distanceView));
    }

    private DistancePopupController createDistancePopup(){
        FXMLLoader fxmlLoader = loadResources();
        if (fxmlLoader == null){
            return null;
        }
        return fxmlLoader.getController();
    }

    private FXMLLoader loadResources() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/competition/DistancePopup.fxml"));
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
