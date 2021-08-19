package com.wl.leetcode.chat;

public class SendTimeOutThread extends Thread {

    private final long outTime;
    private final SendTimeOut sendTimeOut;

    public SendTimeOutThread(long millisecond, SendTimeOut sendTimeOut) {
        this.outTime = millisecond;
        this.sendTimeOut = sendTimeOut;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long deltaTime = 0;
        while (deltaTime < outTime) {
            deltaTime = System.currentTimeMillis() - startTime;
        }

        if (sendTimeOut != null) {
            // 说明超时
            sendTimeOut.sendTimeOut();
        }
    }

    public interface SendTimeOut {
        void sendTimeOut();
    }
}
