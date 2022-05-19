package com.lifehouse.raceth.model.view;

import com.lifehouse.raceth.model.*;
import javafx.scene.control.CheckBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartView {
    private CheckBox checkBox = new CheckBox();
    private Start start;

    public StartView(Start start) {
        this.start = start;
    }
}
