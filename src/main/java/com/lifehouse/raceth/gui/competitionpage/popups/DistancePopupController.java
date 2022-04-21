package com.lifehouse.raceth.gui.competitionpage.popups;

import com.lifehouse.raceth.model.Distance;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
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

    public TableView<Distance> distanceTable;
    public ObjectProperty<Distance> newDistance = new SimpleObjectProperty<>();

    @FXML
    void createOrUpdateDistance(ActionEvent event) {
        Distance distance = buildNewEntity();

        if (distance == null) return;
        newDistance.setValue(distance);

        cancel(event);
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private Distance buildNewEntity() {
        if (distLocation.getText().equals("") ||
                length.getText().equals("") ||
                height.getText().equals("")
        ) {
            return null;
        }

        int localLength, localHeight;
        try {
            localLength = Integer.parseInt(length.getText());
            localHeight = Integer.parseInt(height.getText());
        } catch (NumberFormatException e){
            return null;
        }

        return new Distance(
                distLocation.getText(),
                localLength,
                localHeight
        );
    }

    private void fillFieldsFromEntity(Distance distance) {
        distLocation.setText(distance.getLocation());
        height.setText(String.valueOf(distance.getHeight()));
        length.setText(String.valueOf(distance.getLength()));
    }

    public void edit(Distance distance) {
        fillFieldsFromEntity(distance);
    }
}


