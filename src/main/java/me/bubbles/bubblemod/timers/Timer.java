package me.bubbles.bubblemod.timers;

public class Timer {

    private int time;
    private int timePassed=0;
    private int startTime;

    public Timer() {
        this.time=0;
    }

    public Timer(int time) {
        this.time=time;
        this.startTime=time;
    }

    public void onTick() {
        time=clamp(time-1,0,time);
        timePassed++;

        if(time==0) {
            timeOver();
        }

    }

    public void onTime(int time) {
        if(!(this.timePassed==time)) {
            return;
        }
    }

    public void timeOver() {

    }

    public Timer setTime(int time) {
        this.time=time;
        return this;
    }

    public Timer setStartTime(int time) {
        this.startTime=time;
        return this;
    }

    public int getTime() {
        return time;
    }

    public int getTimePassed() {
        return timePassed;
    }

    public Timer reset() {
        this.time=startTime;
        return this;
    }

    public boolean isTime(int time) {
        return this.time==time;
    }

    public boolean isTimePassed(int time) {
        return this.timePassed==time;
    }

    private int clamp(int in, int min, int max) {
        if(in<min) {
            return min;
        }else if(in>max) {
            return max;
        }
        return in;
    }

}
