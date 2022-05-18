package com.lifehouse.raceth.model.dto;

import com.lifehouse.raceth.model.Start;
import com.lifehouse.raceth.model.StartTab;
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
}
