package com.wl.leetcode;

import com.wl.leetcode.chat.ChatModel;
import com.wl.leetcode.chatdata.ChatBean;
import com.wl.leetcode.chatdata.UserBean;

import java.util.List;
import java.util.Scanner;

public class ChatView implements ChatModel.ChattingRecordGetter {

    public void run() {
        ChatModel chatModel = new ChatModel( this);
        Scanner scanner = new Scanner(System.in);
        UserBean userBean = new UserBean(12, "唯一的大呆子", 0);
        System.out.println("欢迎" + userBean.getUserName() + "进入聊天室");
        System.out.println("提示:输入 -s 你想发送的信息 即可发送信息 -r 重置网络设定");
        while (true) {
            String command = scanner.nextLine();
            if (command.substring(0, 2).equals("-s")){
                String contentText = command.substring(3);
                ChatBean msg = new ChatBean(contentText, userBean);
                chatModel.send(msg);
            }
            if (command.substring(0, 2).equals("-r")){
                chatModel.resetSocket();
            }
        }
    }

    @Override
    public void connectingToServer() {
        System.out.println("正在连接到服务器...");
    }

    @Override
    public void connectingToServerSuccess() {
        System.out.println("连接成功!");
    }

    @Override
    public void connectingToServerTimeOut() {
        System.err.println("连接失败");
    }

    @Override
    public void getHistoryMsg(List<ChatBean> chatBean) {

    }

    @Override
    public void getNewMsg(ChatBean chatBean) {
        System.out.println(chatBean.getBelongUser().getUserName() + " : " + chatBean.getContentText());
    }

    @Override
    public void receiveError(String errorMsg) {
        System.err.println("接收错误 : " + errorMsg);
    }

    @Override
    public void sendError(String errorMsg) {
        System.err.println("发送错误 : " + errorMsg);
    }

    @Override
    public void sendSuccess() {
        System.out.println("发送成功");
    }
}
