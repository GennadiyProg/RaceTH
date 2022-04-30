package com.lifehouse.raceth.logic.relayrunpage;

import com.lifehouse.raceth.dao.RelayTeamDAO;
import com.lifehouse.raceth.model.RelayTeam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    private TableView<RelayTeam> relayTeamTable;
    private RelayTeam selectedRelayTeam = null;
    private RelayTeamDAO relayTeamDAO = new RelayTeamDAO();

    @FXML
    void Saving(ActionEvent event) {
        try{
            if (selectedRelayTeam == null) {
                createRelayTeam();
            }
            else {
                updateRelayTeam();
            }

            closePopup(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createRelayTeam() {
        selectedRelayTeam = new RelayTeam();
        selectedRelayTeam.setId(relayTeamTable.getItems().size());
        selectedRelayTeam.setName(commandName.getText());
        relayTeamTable.getItems().add(selectedRelayTeam);
        relayTeamDAO.create(selectedRelayTeam);
    }

    private void updateRelayTeam() {
        selectedRelayTeam.setName(commandName.getText());
        relayTeamTable.refresh();
        relayTeamDAO.update(selectedRelayTeam);
    }

    @FXML
    void Cancel(ActionEvent event) {
        try{
            ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startEdit(RelayTeam team) {
        selectedRelayTeam = team;
        commandName.setText(team.getName());
    }

    private void closePopup(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide(); //Закрытие окна
    }
}


