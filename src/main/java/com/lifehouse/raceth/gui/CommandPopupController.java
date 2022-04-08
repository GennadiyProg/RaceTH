package com.lifehouse.raceth.gui;

import com.lifehouse.raceth.model.RelayTeam;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lombok.Data;

@Data
public class CommandPopupController {
    @FXML
    private AnchorPane main_pane;

    @FXML
    private Button cancelButt;

    @FXML
    private Button saveButt;

    @FXML
    private TextField commandName;

    public TableView<RelayTeam> relayTeamTable;

    @FXML
    void Saving(ActionEvent event) {
        try{
            RelayTeam team = new RelayTeam();
            team.setId(relayTeamTable.getItems().size());
            team.setName(commandName.getText());

            TmpStorage.relayTeams.add(team);
            relayTeamTable.getItems().add(team);

            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
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


