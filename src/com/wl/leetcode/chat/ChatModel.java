package com.wl.leetcode.chat;

import com.wl.leetcode.chatdata.ChatBean;
import com.wl.leetcode.chatdata.UserBean;
import com.wl.leetcode.constant.NetworkConstant;
import com.wl.leetcode.helper.SocketHelper;
import com.wl.leetcode.helper.TimeOutPool;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatModel implements ReceiveThread.ReceiveManager, TimeOutPool.TimeOutDealer{

    private final ChattingRecordGetter chattingRecordGetter;

    private TimeOutPool timeOutPool;

    private Socket socket;

    private static final String TAG_LOGIN = "TAG_LOGIN";

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

    private void initTimeOutPool(){
        timeOutPool = new TimeOutPool();
    }

    public void getHistoryChat(int id) {
        String header = NetworkConstant.REQUEST_HEADER_REQUEST_HISTORY;
        SocketHelper.sendMsg(socket, header + NetworkConstant.REQUEST_SEPARATOR + id);
    }

    public void send(ChatBean msg) {
        // 整合数据
        String data = combineData(msg);
        // 发送数据
        SocketHelper.sendMsg(socket, data);
    }

    private String combineData(ChatBean chatBean) {
        String header = NetworkConstant.REQUEST_HEADER_CHAT_SEND;
        String body = chatBean.getContentText() + NetworkConstant.DATA_SEPARATOR + chatBean.getBelongUser().getId();
        return header + NetworkConstant.REQUEST_SEPARATOR + body;
    }

    @Override
    public void receiveMsg(String msg) {
        // 判断是否时服务器允许连接
        if (msg.equals(NetworkConstant.ACCESS_CHAT)) {
            timeOutPool.setReached(TAG_LOGIN);
            chattingRecordGetter.connectingToServerSuccess();
            return;
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
            if(data.length == 1){
                handleChatHistory(null);
                return;
            }
            handleChatHistory(data[1]);
        }
    }

    private void handleSend(String sendResult) {
        if (sendResult == null || !sendResult.equals(NetworkConstant.CHAT_SEND_SUCCESS)) {
            // 发送失败
            sendError(sendResult == null ? NetworkConstant.CONNECT_ERROR : sendResult);
            return;
        }
        // 发送成功
        sendSuccess();
    }

    private void handleReceive(String data) {
        ChatBean chatBean = parseChatData(data);
        if (chatBean == null) {
            // 数据异常
            receiveError(NetworkConstant.DATA_ILLEGAL_ERROR);
            return;
        }
        receiveSuccess(chatBean);
    }

    private void handleChatHistory(String data) {
        if(chattingRecordGetter == null){
            return;
        }
        if (data == null) {
            chattingRecordGetter.getHistoryMsg(new ArrayList<>());
            return;
        }
        String[] chatBeanDatas = data.split(NetworkConstant.CHAT_BEAN_SEPARATOR);
        List<ChatBean> chatBeans = new ArrayList<>();
        for (String chatBeanData : chatBeanDatas) {
            ChatBean chatBean = parseChatData(chatBeanData);
            if(chatBean == null){
                continue;
            }
            chatBeans.add(chatBean);
        }
        chattingRecordGetter.getHistoryMsg(chatBeans);
    }

    private ChatBean parseChatData(String data) {
        String[] chatData = data.split(NetworkConstant.CHAT_RECEIVE_CHAT_BEAN_ATTRIBUTE_SEPARATOR);
        if (chatData.length != 4) {
            return null;
        }

        int chatId = Integer.parseInt(chatData[0]);
        String contentText = chatData[1];
        int userId = Integer.parseInt(chatData[2]);
        String userName = SocketHelper.decodeMsg(chatData[3]);

        UserBean userBean = new UserBean(userId, userName, 0);
        ChatBean chatBean = new ChatBean(contentText, userBean);
        chatBean.setId(chatId);

        return chatBean;
    }

    public void timeOut(String tag){
        if(tag.equals(TAG_LOGIN)){
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

    private void sendError(String errorMsg) {
        if (chattingRecordGetter == null) {
            return;
        }
        chattingRecordGetter.sendError(errorMsg);
    }

    private void sendSuccess() {
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
    }
}
