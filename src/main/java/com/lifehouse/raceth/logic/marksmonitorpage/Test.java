package com.lifehouse.raceth.logic.marksmonitorpage;

import javafx.scene.control.TextField;
import lombok.Data;

@Data
public class Test {
    private TextField stopwatch;

    public void changeText(String str){
        stopwatch.setText(str);
    }
}
