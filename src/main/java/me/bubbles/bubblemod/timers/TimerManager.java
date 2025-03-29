package me.bubbles.bubblemod.timers;

import java.util.ArrayList;
import java.util.List;

public class TimerManager {

    private List<Timer> timerList = new ArrayList<>();

    public TimerManager() {

    }

    public TimerManager(Timer... timers) {
        this.timerList=List.of(timers);
    }

    public void addTimer(Timer timer) {
        timerList.add(timer);
    }

    public void removeTimer(Timer timer) {
        timerList.remove(timer);
    }

    public void onTick() {
        if(timerList!=null) {
            for(Timer timer : timerList) {
                timer.onTick();
            }
        }
    }

}
