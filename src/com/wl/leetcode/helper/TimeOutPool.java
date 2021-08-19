package com.wl.leetcode.helper;

import com.wl.leetcode.threads.TimeOutThread;
import java.util.HashMap;

@SuppressWarnings("all")
public class TimeOutPool implements TimeOutThread.TimeOut {

    private HashMap<String, Boolean> isReachedSet;
    private HashMap<String, TimeOutDealer> timeOutSet;
    private long duration;

    public TimeOutPool(){
        this(3000);
    }

    public TimeOutPool(long duration) {
        this.duration = duration;
        this.isReachedSet = new HashMap<>();
        this.timeOutSet = new HashMap<>();
    }

    public void startNewTask(String tag, TimeOutDealer timeOutDealer){
        TimeOutThread thread = new TimeOutThread(this, tag, duration);
        timeOutSet.put(tag, timeOutDealer);
        isReachedSet.put(tag, false);
        thread.start();
    }

    public void setReached(String tag){
        if(isReachedSet.containsKey(tag)){
            isReachedSet.put(tag, true);
        }
    }

    @Override
    public void timeOut(String tag) {
        if(isReachedSet.containsKey(tag) && !isReachedSet.get(tag)){
            timeOutSet.get(tag).timeOut(tag);
        }
        isReachedSet.remove(tag);
        timeOutSet.remove(tag);
    }

    public static interface TimeOutDealer {
        void timeOut(String tag);
    }
}
