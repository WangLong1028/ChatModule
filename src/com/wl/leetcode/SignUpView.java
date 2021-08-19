package com.wl.leetcode;

import com.wl.leetcode.login.SignUpModel;

import java.util.Scanner;

public class SignUpView implements SignUpModel.SignUpResultCallback {

    public void run() {
        SignUpModel signUpModel = new SignUpModel(this);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名:");
            String userName = scanner.next();
            System.out.println("请输入密码");
            String password = scanner.next();
            System.out.println("请输入安全问题");
            String secureProblem = scanner.next();
            System.out.println("请输入答案");
            String secureAns = scanner.next();
            signUpModel.SignUp(userName, password, secureProblem, secureAns, 0);
        }
    }

    @Override
    public void signUpSuccess() {
        System.out.println("注册成功");
    }

    @Override
    public void signUpFailed(String failedMsg) {
        System.err.println("注册失败 : " + failedMsg);
    }
}
