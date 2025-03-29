package me.bubbles.bubblemod.timers.queue;

import me.bubbles.bubblemod.timers.Timer;

import java.util.ArrayList;
import java.util.List;

public class QueueHandler extends Timer {

    private int delay;
    private Timer queueTime;
    private List<Queue> queueList = new ArrayList<>();

    public QueueHandler(int delay) {
        super(delay);
        this.delay=delay;
    }

    @Override
    public void timeOver() {
        if(!queueList.isEmpty()) {
            queueList.get(0).run();
            queueList.remove(0);
            this.setTime(delay);
        }
    }

    public void addQueue(Queue queue) {
        this.queueList.add(queue);
        if(this.getTime()==0) {
            if(this.queueList.size()==1) {
                timeOver();
            }else{
                this.setTime(delay);
            }
        }
    }

}
