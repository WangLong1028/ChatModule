package com.wl.leetcode;

import com.wl.leetcode.login.LoginModel;

import java.util.Scanner;

public class LoginView implements LoginModel.LoginResultCallback {

    public void run(){
        Scanner scanner = new Scanner(System.in);
        LoginModel loginModel = new LoginModel(this);
        while (true){
            System.out.println("请输入用户名");
            String user_name = scanner.next();
            System.out.println("请输入密码");
            String user_password = scanner.next();
            loginModel.login(user_name, user_password);
        }

    }

    @Override
    public void logSuccess() {
        System.out.println("登录成功");
    }

    @Override
    public void logFailed(String failedMsg) {
        System.err.println(failedMsg);
    }
}
