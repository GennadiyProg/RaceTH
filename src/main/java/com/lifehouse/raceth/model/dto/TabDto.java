package com.lifehouse.raceth.model.dto;

import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.model.StartTab;
import com.lifehouse.raceth.model.view.StartView;
import javafx.scene.control.Tab;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TabDto {
    private StartTab tabInfo;
    private List<Start> starts;
    private Tab referenceTab;

    public TabDto(StartTab startTab) {
        this.tabInfo = startTab;
        starts = new ArrayList<>();
        referenceTab = new Tab();
    }

    public void removeStart(StartView startView) {
        removeStart(startView.getStart());
    }

    public void removeStart(Start start) {
        starts.remove(starts.stream().filter(el -> el.getId() == start.getId()).findFirst().orElse(null));
    }

    public boolean contains(StartView startView) {
        return contains(startView.getStart());
    }

    public boolean contains(Start start) {
        return starts.stream().anyMatch(el -> el.getId() == start.getId());
    }
}
