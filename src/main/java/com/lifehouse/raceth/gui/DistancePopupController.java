package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.model.Distance;
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
    private TextField high;

    @FXML
    private TextField length;

    public TableView<Distance> distancesTable;

    @FXML
    void Saving(ActionEvent event) {
        try{
            Distance distance = new Distance();
            DistanceDAO distanceDAO = new DistanceDAO();

            distance.setId(distancesTable.getItems().size());
            distance.setLocation(distLocation.getText());
            distance.setLength(Integer.parseInt(length.getText()));
            distance.setHeight(Integer.parseInt(high.getText()));

            distanceDAO.create(distance);

            distancesTable.getItems().add(distance);
            distancesTable.refresh();

            ((Node)(event.getSource())).getScene().getWindow().hide();
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
}


