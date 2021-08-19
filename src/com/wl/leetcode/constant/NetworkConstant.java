package com.wl.leetcode.constant;

public class NetworkConstant {
    //public static final String SERVER_IP = "1.182.113.131";
    //public static final int SERVER_PORT = 1028;

    //public static final String SERVER_IP = "reimage.f3322.net";
    //public static final String SERVER_IP = "1.182.112.41";
    public static final String SERVER_IP = "192.168.43.9";
    public static final int SERVER_PORT = 1028;

    public static final String SERVER_IP_TEST = "192.168.43.249";
    public static final int SERVER_PORT_TEST = 1080;

    // 请求分隔符
    public static final String REQUEST_SEPARATOR = "&REQUEST_SEPARATOR&";
    public static final String DATA_SEPARATOR = "&DATA_SEPARATOR&";

    // 请求头
    public static final String REQUEST_HEADER_SIGN_UP = "REQUEST_HEADER_SIGN_UP";
    public static final String REQUEST_HEADER_LOGIN = "REQUEST_HEADER_LOGIN";
    public static final String REQUEST_HEADER_CHAT_CLIENT_LOGIN = "REQUEST_HEADER_CHAT_CLIENT_LOGIN";
    public static final String REQUEST_HEADER_CHAT_SEND = "REQUEST_HEADER_CHAT_SEND";
    public static final String REQUEST_HEADER_REQUEST_USER = "REQUEST_HEADER_REQUEST_USER";
    public static final String REQUEST_HEADER_REQUEST_HISTORY = "REQUEST_HEADER_REQUEST_HISTORY";

    // 注册相关常量
    public static final String SIGN_UP_SUCCESS = "SIGN_UP_SUCCESS";
    public static final String SIGN_UP_EXIST_SAME_USER_NAME_ERROR = "SIGN_UP_EXIST_SAME_USER_NAME_ERROR";

    // 登录相关常量
    public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    public static final String LOGIN_PASSWORD_ERROR = "LOGIN_PASSWORD_ERROR";
    public static final String LOGIN_USER_NOT_EXIST_ERROR = "LOGIN_USER_NOT_EXIST_ERROR";

    // 接收相关常量
    public static final String CHAT_RECEIVE_CHAT_BEAN_SEPARATOR = "&CHAT_BEAN_SEPARATOR&";
    public static final String CHAT_RECEIVE_CHAT_BEAN_ATTRIBUTE_SEPARATOR = "&CHAT_ATTR_SEPARATOR&";

    // 聊天相关常量
    public static final String PREFIX_MODE_SEND = "PREFIX_MODE_SEND";
    public static final String PREFIX_MODE_RECEIVE = "PREFIX_MODE_RECEIVE";
    public static final String PREFIX_MODE_GET_HISTORY = "PREFIX_MODE_GET_HISTORY";
    public static final String CHAT_MODE_SEPARATOR = "&CHAT_MODE_SEPARATOR&";
    public static final String CHAT_BEAN_SEPARATOR = "&CHAT_BEAN_SEPARATOR&";
    public static final String CHAT_SEND_SUCCESS = "CHAT_SEND_SUCCESS";
    public static final String ACCESS_CHAT = "ACCESS_CHAT";


    // 通用返回码
    public static final String DATA_ILLEGAL_ERROR = "DATA_ILLEGAL_ERROR";
    public static final String CONNECT_ERROR = "CONNECT_ERROR";
}
