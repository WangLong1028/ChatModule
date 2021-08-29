package com.wl.leetcode.chat;

import com.wl.leetcode.helper.SocketHelper;

import java.net.Socket;

public class ReceiveThread extends Thread {

    private Socket socket;
    private ReceiveManager receiveManager;

    private boolean isPause = false;


    public ReceiveThread(Socket socket, ReceiveManager receiveManager) {
        this.socket = socket;
        this.receiveManager = receiveManager;
    }


    @Override
    public void run() {
        if (receiveManager == null) {
            return;
        }
        while (true) {
            if (!isPause) {
                String msg = SocketHelper.getMsg(socket);
                if (msg != null) {
                    receiveManager.receiveMsg(msg);
                }
            }
        }
    }

    public void pauseThread() {
        isPause = true;
    }

    public void resumeThread() {
        isPause = false;
    }

    public interface ReceiveManager {
        void receiveMsg(String msg);
    }
}
