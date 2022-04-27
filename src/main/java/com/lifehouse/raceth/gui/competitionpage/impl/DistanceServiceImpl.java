package com.lifehouse.raceth.gui.competitionpage.impl;

import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.gui.competitionpage.CompetitionPageController;
import com.lifehouse.raceth.gui.competitionpage.CompetitionPageElementService;
import com.lifehouse.raceth.gui.competitionpage.popups.DistancePopupController;
import com.lifehouse.raceth.model.competition.Competition;
import com.lifehouse.raceth.model.viewmodel.DistanceView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;

public class DistanceServiceImpl implements CompetitionPageElementService {
    private final DistanceDAO distanceDAO;
    private final TableView<DistanceView> distanceTable;

    public DistanceServiceImpl(TableView<DistanceView> distanceTable) {
        this.distanceDAO = new DistanceDAO();
        this.distanceTable = distanceTable;
    }

    @Override
    public void create() {
        DistancePopupController controller = createDistancePopup();
        if (controller == null) return;
        controller.getNewDistance().addListener((observable, oldValue, newValue) -> {
            distanceDAO.create(newValue);
            DistanceView distanceView = DistanceView.convertToView(newValue);
            distanceView.getCheckBox().selectedProperty().addListener((observableList, oldStatus, newStatus) -> {
                Competition curCompetition = CompetitionPageController.currentCompetition;
                attachCompetition(distanceView, curCompetition, newStatus);
                System.out.println("ASdas");
            });
            distanceTable.getItems().add(distanceView);
            distanceTable.refresh();
        });
    }

    private void attachCompetition(DistanceView distanceView, Competition competition, Boolean status) {
        if (competition != null) {
            if (status) {
                distanceView.getCompetitions().add(competition);
            } else {
                distanceView.getCompetitions().remove(
                        distanceView.getCompetitions().stream()
                                .filter((el) -> el.getId() == competition.getId())
                                .findFirst().orElse(null)
                );
                distanceDAO.update(DistanceView.convertToModel(distanceView));
            }
        } else {
            distanceView.getCheckBox().setSelected(false);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Не указано текущее соревнование");
            alert.show();
        }
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
        DistancePopupController distancePopupController = fxmlLoader.getController();
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
