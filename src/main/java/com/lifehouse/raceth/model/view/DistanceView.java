package com.lifehouse.raceth.model.view;

import com.lifehouse.raceth.dao.DistanceDAO;
import com.lifehouse.raceth.logic.MainPageController;
import com.lifehouse.raceth.logic.competitionpage.CompetitionPageController;
import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.competition.Competition;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceView {
    private CheckBox checkBox = new CheckBox();
    private long id;
    private String location;
    private int length;
    private int height; // Набор высоты
    private List<Competition> competitions = new ArrayList<>();

    private ChangeListener<Boolean> checkboxListener = ((observableList, oldStatus, newStatus) ->
        attachCompetition(newStatus)
    );

    public DistanceView(long id, String location, int length, int height, List<Competition> competitions) {
        checkBox.selectedProperty().addListener(checkboxListener);
        this.id = id;
        this.location = location;
        this.length = length;
        this.height = height;
        this.competitions = competitions;
    }

    private void attachCompetition(Boolean status) {
        DistanceDAO distanceDAO = new DistanceDAO();
        Competition curCompetition = MainPageController.currentCompetition;
        if (curCompetition == null) {
            callAlert();
            return;
        }

        if (status) {
            attachCompetitionUtil(curCompetition, distanceDAO);
        } else {
            detachCompetitionUtil(curCompetition, distanceDAO);
        }
    }

    private void attachCompetitionUtil(Competition curCompetition, DistanceDAO distanceDAO) {
        boolean relationExists = this.competitions.stream().anyMatch((el) -> el.getId() == curCompetition.getId());
        if (!relationExists) {
            distanceDAO.addCompetition(DistanceView.convertToModel(this), curCompetition);
        }
    }

    private void detachCompetitionUtil(Competition curCompetition, DistanceDAO distanceDAO) {
        boolean relationExists = this.competitions.stream().anyMatch((el) -> el.getId() == curCompetition.getId());
        if (relationExists) {
            distanceDAO.removeCompetition(DistanceView.convertToModel(this), curCompetition);
        }
    }

    private void callAlert() {
        checkBox.selectedProperty().removeListener(checkboxListener);
        checkBox.setSelected(false);
        checkBox.selectedProperty().addListener(checkboxListener);
        new Alert(Alert.AlertType.ERROR, "Не указано текущее соревнование").show();
    }

    public static DistanceView convertToView(Distance distance) {
        return new DistanceView(
                distance.getId(),
                distance.getLocation(),
                distance.getLength(),
                distance.getHeight(),
                distance.getCompetitions()
        );
    }

    public static Distance convertToModel(DistanceView distanceView) {
        return new Distance(
                distanceView.getId(),
                distanceView.getLocation(),
                distanceView.getLength(),
                distanceView.getHeight(),
                distanceView.getCompetitions()
        );
    }

    public void setFields(DistanceView distance) {
        this.location = distance.getLocation();
        this.length = distance.getLength();
        this.height = distance.getHeight();
    }
}
