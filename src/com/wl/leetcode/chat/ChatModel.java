package com.wl.leetcode.chat;

import com.alibaba.fastjson.JSON;
import com.wl.leetcode.chatdata.ChatBean;
import com.wl.leetcode.chatdata.UserBean;
import com.wl.leetcode.constant.NetworkConstant;
import com.wl.leetcode.helper.SocketHelper;
import com.wl.leetcode.helper.TimeOutPool;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatModel implements ReceiveThread.ReceiveManager, TimeOutPool.TimeOutDealer, ChatBeanSender.ChatSendResult {

    private final ChattingRecordGetter chattingRecordGetter;

    private TimeOutPool timeOutPool;

    private Socket socket;

    private static final String TAG_LOGIN = "TAG_LOGIN";

    private ChatBeanSender chatBeanSender;


    public ChatModel(ChattingRecordGetter chattingRecordGetter) {
        this.chattingRecordGetter = chattingRecordGetter;
        try {
            // 初始化超时线程池
            initTimeOutPool();
            // 初始化相关设置
            initSocket();
        } catch (IOException e) {
            receiveError(NetworkConstant.CONNECT_ERROR);
        }
    }

    private void initSocket() throws IOException {
        if (chattingRecordGetter == null) {
            return;
        }
        this.socket = new Socket(NetworkConstant.SERVER_IP, NetworkConstant.SERVER_PORT);
        // 主线程发送消息
        // 新线程接收信息
        ReceiveThread receiveThread = new ReceiveThread(socket, this);
        receiveThread.start();

        // 发送登录消息
        chattingRecordGetter.connectingToServer();
        timeOutPool.startNewTask(TAG_LOGIN, this);
        SocketHelper.sendMsg(socket, NetworkConstant.REQUEST_HEADER_CHAT_CLIENT_LOGIN);
    }

    private void initTimeOutPool() {
        timeOutPool = new TimeOutPool();
    }

    public void getHistoryChat(int id) {
        String header = NetworkConstant.REQUEST_HEADER_REQUEST_HISTORY;
        SocketHelper.sendMsg(socket, header + NetworkConstant.REQUEST_SEPARATOR + id);
    }

    public void send(ChatBean msg) {
        //msg.setPicPath("D:/Test/test.jpg");
        // 判断是否要发送图片
        chatBeanSender = null;
        chatBeanSender = new ChatBeanSender(socket, this);
        chatBeanSender.send(msg);
    }

    @Override
    public void receiveMsg(String msg) {
        // 判断是否时服务器允许连接
        if (msg.equals(NetworkConstant.ACCESS_CHAT)) {
            timeOutPool.setReached(TAG_LOGIN);
            chattingRecordGetter.connectingToServerSuccess();
            return;
        }

        // 信息发送器检查结果
        if (chatBeanSender != null) {
            chatBeanSender.handleMsg(msg);
        }

        // 判断数据类型
        String[] data = msg.split(NetworkConstant.CHAT_MODE_SEPARATOR);
        String prefix = data[0];
        if (prefix.equals(NetworkConstant.PREFIX_MODE_SEND)) {
            handleSend(data[1]);
        }
        if (prefix.equals(NetworkConstant.PREFIX_MODE_RECEIVE)) {
            handleReceive(data[1]);
        }
        if (prefix.equals(NetworkConstant.PREFIX_MODE_GET_HISTORY)) {
            if (data.length == 1) {
                handleChatHistory(null);
                return;
            }
            handleChatHistory(data[1]);
        }
    }

    private void handleSend(String sendResult) {
        if (sendResult == null) {
            sendError(NetworkConstant.CONNECT_ERROR);
            return;
        }
        String[] code = sendResult.split("#");
        if (!code[0].equals(NetworkConstant.CHAT_SEND_SUCCESS)) {
            // 发送失败
            sendError(sendResult);
            return;
        }
        // 发送成功
        sendSuccess();
    }

    private void handleReceive(String data) {
        ChatBean chatBean = JSON.parseObject(data, ChatBean.class);
        if (chatBean == null) {
            // 数据异常
            receiveError(NetworkConstant.DATA_ILLEGAL_ERROR);
            return;
        }
        receiveSuccess(chatBean);
    }

    private void handleChatHistory(String data) {
        if (chattingRecordGetter == null) {
            return;
        }
        if (data == null) {
            chattingRecordGetter.getHistoryMsg(new ArrayList<>());
            return;
        }
        List<ChatBean> chatBeans = JSON.parseArray(data, ChatBean.class);
        chattingRecordGetter.getHistoryMsg(chatBeans);
    }

    public void timeOut(String tag) {
        if (tag.equals(TAG_LOGIN)) {
            chattingRecordGetter.connectingToServerTimeOut();
        }
    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetSocket() {
        if (socket != null) {
            return;
        }
        try {
            initSocket();
            System.out.println("重置成功!");
        } catch (IOException e) {
            receiveError(NetworkConstant.CONNECT_ERROR);
            System.out.println("连接失败");
        }
    }

    @Override
    public void sendFailed(String msg) {
        sendError(msg);
    }

    private void sendError(String errorMsg) {
        if (chattingRecordGetter == null) {
            return;
        }
        chattingRecordGetter.sendError(errorMsg);
    }

    @Override
    public void sendSuccess() {
        if (chattingRecordGetter == null) {
            return;
        }
        chattingRecordGetter.sendSuccess();
    }

    private void receiveError(String errorMsg) {
        if (chattingRecordGetter == null) {
            return;
        }
        chattingRecordGetter.receiveError(errorMsg);
    }

    private void receiveSuccess(ChatBean chatBean) {
        if (chattingRecordGetter == null) {
            return;
        }
        chattingRecordGetter.getNewMsg(chatBean);
    }

    public interface ChattingRecordGetter {

        void connectingToServer();

        void connectingToServerSuccess();

        void connectingToServerTimeOut();

        void getNewMsg(ChatBean chatBean);

        void getHistoryMsg(List<ChatBean> chatBean);

        void receiveError(String errorMsg);

        void sendError(String errorMsg);

        void sendSuccess();

        void sendPicError();
    }
}
