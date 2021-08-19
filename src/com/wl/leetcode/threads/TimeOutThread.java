package com.wl.leetcode.threads;

@SuppressWarnings("all")
public class TimeOutThread extends Thread{

    private TimeOut timeOut;
    private long duration;
    private String tag;

    public TimeOutThread(TimeOut timeOut, String tag, long duaration) {
        this.timeOut = timeOut;
        this.tag = tag;
        this.duration = duaration;
    }

    @Override
    public void run() {
        if (timeOut == null) {
            return;
        }
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime != duration);
        timeOut.timeOut(tag);
    }

    public static interface TimeOut{
        void timeOut(String tag);
    }
}
