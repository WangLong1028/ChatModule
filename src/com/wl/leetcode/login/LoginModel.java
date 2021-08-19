package com.wl.leetcode.login;

import com.wl.leetcode.constant.NetworkConstant;
import com.wl.leetcode.helper.SocketHelper;

import java.net.Socket;

public class LoginModel {
    private LoginResultCallback loginResultCallback;

    public LoginModel(LoginResultCallback loginResultCallback) {
        this.loginResultCallback = loginResultCallback;
    }

    public void login(String userName, String userPassword) {
        try {
            // 与服务器取得联系
            Socket socket = new Socket(NetworkConstant.SERVER_IP, NetworkConstant.SERVER_PORT);
            // 整合数据
            String data = combineData(userName, userPassword);
            // 将数据发给服务器
            SocketHelper.sendMsg(socket, data);
            // 得到服务器消息
            String loginResult = SocketHelper.getMsg(socket);
            // 检测消息
            if (loginResult == null){
                loginFailed(NetworkConstant.CONNECT_ERROR);
                socket.close();
                return;
            }
            if (!loginResult.equals(NetworkConstant.LOGIN_SUCCESS)){
                loginFailed(loginResult);
                socket.close();
                return;
            }
            // 登录成功
            loginSuccess();
            socket.close();
        } catch (Exception e) {
            loginFailed(NetworkConstant.CONNECT_ERROR);
        }
    }

    private String combineData(String userName, String userPassword) {
        String header = NetworkConstant.REQUEST_HEADER_LOGIN;
        String body = userName + NetworkConstant.DATA_SEPARATOR + userPassword;
        return header + NetworkConstant.REQUEST_SEPARATOR + body;
    }

    private void loginSuccess() {
        if (loginResultCallback != null) {
            loginResultCallback.logSuccess();
        }
    }

    private void loginFailed(String failedMsg) {
        if (loginResultCallback != null) {
            if (failedMsg.equals(NetworkConstant.CONNECT_ERROR)) loginResultCallback.logFailed("与服务器通讯失败");
            if (failedMsg.equals(NetworkConstant.LOGIN_PASSWORD_ERROR)) loginResultCallback.logFailed("密码错误");
            if (failedMsg.equals(NetworkConstant.LOGIN_USER_NOT_EXIST_ERROR)) loginResultCallback.logFailed("用户不存在");
        }
    }

    public interface LoginResultCallback {
        void logSuccess();

        void logFailed(String failedMsg);
    }
}
