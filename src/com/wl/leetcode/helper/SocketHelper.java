package com.wl.leetcode.helper;

import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketHelper {
    public static String getMsg(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            int len = socket.getInputStream().read(bytes);
            return new String(bytes, StandardCharsets.UTF_8).substring(0, len);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decodeMsg(String msg){
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == '\0'){
                return msg.substring(0, i);
            }
        }
        return msg;
    }

    public static boolean sendMsg(Socket socket, String msg) {
        try {
            if (msg == null) {
                return false;
            }
            if (socket == null) {
                return false;
            }
            socket.getOutputStream().write(msg.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
