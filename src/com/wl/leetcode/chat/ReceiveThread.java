package com.wl.leetcode.chat;

import com.wl.leetcode.helper.SocketHelper;

import java.net.Socket;

public class ReceiveThread extends Thread {

    private Socket socket;
    private ReceiveManager receiveManager;

    private boolean isStop = false;

    public ReceiveThread(Socket socket, ReceiveManager receiveManager) {
        this.socket = socket;
        this.receiveManager = receiveManager;
    }

    @Override
    public void run() {
        if (receiveManager == null) {
            return;
        }
        while (!isStop) {
            String msg = SocketHelper.getMsg(socket);
            if (msg != null){
                receiveManager.receiveMsg(msg);
            }
        }
    }

    public interface ReceiveManager {
        void receiveMsg(String msg);
    }
}
