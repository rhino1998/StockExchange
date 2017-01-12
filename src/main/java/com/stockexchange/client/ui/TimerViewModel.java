package com.stockexchange.client.ui;

import java.util.Timer;

public class TimerViewModel {
    private Timer timer;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

}
