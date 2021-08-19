package com.wl.leetcode.login;

import com.wl.leetcode.constant.NetworkConstant;
import com.wl.leetcode.helper.SocketHelper;

import java.io.IOException;
import java.net.Socket;

public class SignUpModel {

    private SignUpResultCallback signUpResultCallback;

    public SignUpModel(SignUpResultCallback signUpResultCallback) {
        this.signUpResultCallback = signUpResultCallback;
    }

    public void SignUp(String userName, String password, String secureProblem, String secureAns, int headshot) {
        // 先检查数据合法性
        if (userName.equals("null") || password.equals("null") || secureProblem.equals("null") || secureAns.equals("null")){
            // 数据不合法
            signFailed(NetworkConstant.DATA_ILLEGAL_ERROR);
            return;
        }
        try {
            // 连接服务器
            Socket socket = new Socket(NetworkConstant.SERVER_IP, NetworkConstant.SERVER_PORT);
            // 整合数据
            String data = combineData(userName, password, secureProblem, secureAns, headshot);
            // 将数据发给服务器
            SocketHelper.sendMsg(socket, data);
            // 得到服务器消息
            String signUpResult = SocketHelper.getMsg(socket);
            if (signUpResult == null){
                // 说明与服务器通讯失败
                signFailed(NetworkConstant.CONNECT_ERROR);
                socket.close();
                return;
            }
            if (!signUpResult.equals(NetworkConstant.SIGN_UP_SUCCESS)){
                // 注册失败
                signFailed(signUpResult);
                socket.close();
                return;
            }
            // 注册成功
            signUpSuccess();
            socket.close();
        } catch (IOException e) {
            signFailed(NetworkConstant.CONNECT_ERROR);
        }

    }

    // 整合数据方法
    private String combineData(String userName, String password, String secureProblem, String secureAns, int headshot) {
        String header = NetworkConstant.REQUEST_HEADER_SIGN_UP;
        String body = userName + NetworkConstant.DATA_SEPARATOR + password + NetworkConstant.DATA_SEPARATOR + secureProblem
                + NetworkConstant.DATA_SEPARATOR + secureAns + NetworkConstant.DATA_SEPARATOR + headshot;
        return header + NetworkConstant.REQUEST_SEPARATOR + body;
    }


    public void signUpSuccess() {
        if (signUpResultCallback != null) {
            signUpResultCallback.signUpSuccess();
        }
    }

    public void signFailed(String failedMsg) {
        if (signUpResultCallback != null) {
            String returnMsg = failedMsg;
            if (failedMsg.equals(NetworkConstant.CONNECT_ERROR))
                returnMsg = "与服务器通讯失败";
            if (failedMsg.equals(NetworkConstant.DATA_ILLEGAL_ERROR))
                returnMsg = "请检查是否包含了null等不合法字符";
            if (failedMsg.equals(NetworkConstant.SIGN_UP_EXIST_SAME_USER_NAME_ERROR))
                returnMsg = "已经存在同名用户";
            signUpResultCallback.signUpFailed(returnMsg);
        }
    }

    public static interface SignUpResultCallback {
        void signUpSuccess();

        void signUpFailed(String failedMsg);
    }
}
