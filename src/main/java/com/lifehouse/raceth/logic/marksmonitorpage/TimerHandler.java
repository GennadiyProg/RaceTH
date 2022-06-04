package com.lifehouse.raceth.logic.marksmonitorpage;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Data
public class TimerHandler {
    private TextField stopwatch, startTimeInput;
    private Button startTimerButton;

    private LocalTime timeOnTimer;
    private Timeline timeline;

    public TimerHandler(TextField stopwatch, TextField startTimeInput, Button startTimerButton) {
        this.stopwatch = stopwatch;
        this.startTimeInput = startTimeInput;
        this.startTimerButton = startTimerButton;
        initTimeline();
    }

    public void initTimeline() {
        timeOnTimer = LocalTime.MIN;
        DateTimeFormatter formatForDateNow = DateTimeFormatter.ofPattern("HH:mm:ss:SS");
        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(10),
                        ae -> {
                            timeOnTimer = timeOnTimer.plusNanos(10000000);
                            stopwatch.setText(timeOnTimer.format(formatForDateNow));
                        }
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void schedule() {
        if (startTimeInput.getText().equals("")) return;
        LocalTime currentTime = LocalTime.now();
        LocalTime readTime = LocalTime.parse(startTimeInput.getText());
        if (readTime.isBefore(currentTime)){
            timeOnTimer = LocalTime.ofNanoOfDay(readTime.until(currentTime, ChronoUnit.NANOS));
            startOrStop();
        } else {
            stopwatch.setText(startTimeInput.getText());
            new Timeline(
                    new KeyFrame(Duration.millis(currentTime.until(readTime, ChronoUnit.MILLIS)), ae -> startOrStop()
                    )
            ).play();
        }
    }

    public void reset() {
        stopwatch.setText("00:00:00:00");
        timeOnTimer = LocalTime.MIN;
    }

    public void startOrStop() {
        if (timeline.getStatus() == Animation.Status.STOPPED) {
            timeline.play();
            startTimerButton.setText("Стоп");
            startTimerButton.getStyleClass().set(3, "btn-danger");
        } else {
            timeline.stop();
            startTimerButton.setText("Старт");
            startTimerButton.getStyleClass().set(3, "btn-success");
        }
    }
}
