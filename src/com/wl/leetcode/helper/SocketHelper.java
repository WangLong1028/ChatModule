package com.wl.leetcode.helper;


import com.wl.leetcode.constant.NetworkConstant;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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

    public static String decodeMsg(String msg) {
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == '\0') {
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

    public static boolean uploadPic(Socket socket, int id, String path, String request_type) {
        if (socket == null) {
            return false;
        }
        sendMsg(socket, request_type);
        File file = new File(path);

        String[] fileInfo = file.getName().split("\\.");
        sendMsg(socket, id + "." + fileInfo[fileInfo.length - 1] + "#" + file.length());

        String ans = getMsg(socket);
        if (!ans.equals("Got")) {
            System.err.println(ans);
            return false;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int data;
            while ((data = fileInputStream.read(bytes)) != -1) {
                socket.getOutputStream().write(bytes, 0, data);
            }
            fileInputStream.close();

            String msg = getMsg(socket);
            if (msg == null) {
                return false;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return true;
    }

    public static boolean uploadHeadshotPic(Socket socket, int id, String path) {
        return uploadPic(socket, id, path, NetworkConstant.REQUEST_HEADER_POST_HEADSHOT_PIC);
    }

    public static boolean uploadChatPic(Socket socket, int id, String path) {
        return uploadPic(socket, id, path, NetworkConstant.REQUEST_HEADER_POST_CHAT_PIC);
    }
}