package com.lifehouse.raceth.gui.competitionpage.popups;

import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.model.Distance;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

@Data
public class DistancePopupController {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private TextField distLocation;

    @FXML
    private TextField height;

    @FXML
    private TextField length;

    public TableView<Distance> distancesTable;
    private Distance selectedDistance = null;

    private DistanceDAO distanceDAO = new DistanceDAO();

    @FXML
    void Saving(ActionEvent event) {
        try{
            Distance newDistance = buildNewEntity();

            if (selectedDistance != null) {
                updateEntity(newDistance);
                closePopup(event);
                return;
            }

            createEntity(newDistance);
            closePopup(event);

        } catch (Exception e) {
            System.out.println("cant loading");
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        try{
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            System.out.println("cant loading");
        }
    }

    private Distance buildNewEntity() {
        Distance distance = new Distance();

        distance.setLocation(distLocation.getText());
        distance.setLength(Integer.parseInt(length.getText()));
        distance.setHeight(Integer.parseInt(height.getText()));

        return distance;
    }

    private void fillFieldsFromEntity(Distance distance) {
        distLocation.setText(distance.getLocation());
        height.setText(String.valueOf(distance.getHeight()));
        length.setText(String.valueOf(distance.getLength()));
    }

    public void startEdit(Distance distance) {
        fillFieldsFromEntity(distance);
        selectedDistance = distance;
    }

    private void updateEntity(Distance distance) {
        System.out.println(distance);
        distanceDAO.update(distance);
        selectedDistance.setFields(distance);
        distancesTable.refresh();
    }

    private void createEntity(Distance distance) {
        distance.setId(distancesTable.getItems().size());
        distanceDAO.create(distance);
        distancesTable.getItems().add(distance);
        distancesTable.refresh();
    }

    public void closePopup(Event e) {
        ((Node)(e.getSource())).getScene().getWindow().hide();
    }
}


