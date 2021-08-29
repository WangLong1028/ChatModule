package com.wl.leetcode;

import com.alibaba.fastjson.JSON;
import com.wl.leetcode.chatdata.ChatBean;
import com.wl.leetcode.chatdata.UserBean;
import com.wl.leetcode.constant.NetworkConstant;
import com.wl.leetcode.helper.SocketHelper;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        testServerSocket();
    }

    private static void testServerSocket() {
        new ChatView().run();
//        UserBean userBean = new UserBean(0, "唯一的大呆子");
//        ChatBean chatBean = new ChatBean("测试", userBean);
//        chatBean.setPicPath("D:/Test/test.jpg");
//        chatBean.setChatPicImg("123.jpg");
//        String json = JSON.toJSONString(chatBean);
//        System.out.println(json);
//        String newJson = "{\"belongUser\": {\"id\": 0, \"userName\": \"唯一的大呆子\"}, \"chatPicImg\": \"123.jpg\", \"contentText\": \"测试\", \"id\": 0, \"picPath\": \"D:/Test/test.jpg\"}";
//        //String newJson = "{\"belongUser\":{\"id\":0,\"userName\":\"唯一的大呆子\"},\"chatPicImg\":\"123.jpg\",\"contentText\":\"测试\",\"id\":0,\"picPath\":\"D:/Test/test.jpg\"}";
//        ChatBean ans = JSON.parseObject(newJson, ChatBean.class);
//        System.out.println(ans.getBelongUser().getUserName());
//
//        CityBean c1 = new CityBean("广州");
//        String json1 = JSON.toJSONString(c1);
//        c1.setCityName("杭州");
//        String json2 = JSON.toJSONString(c1);
//        String jsonArray = "["+json1+","+json2+",]";
//        List<CityBean> cityBeans = JSON.parseArray(jsonArray, CityBean.class);
//        for (CityBean cityBean : cityBeans) {
//            System.out.println(cityBean.getCityName());
//        }


//        new ChatView().run();
//        try {
//            Socket socket = new Socket(NetworkConstant.SERVER_IP, NetworkConstant.SERVER_PORT);
//            SocketHelper.uploadChatPic(socket, 20, "D:/Test/test.jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        try {
//            URL url = new URL("http://127.0.0.1:1028/headshot/test.png");
//            URLConnection urlConnection = url.openConnection();
//            InputStream inputStream = urlConnection.getInputStream();
//
//            File file = new File("D:/Test/new.png");
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//
//            byte[] bytes = new byte[1024];
//            int len = 0;
//            while ((len = inputStream.read(bytes)) != -1){
//                fileOutputStream.write(bytes, 0, len);
//            }
//
//            inputStream.close();
//            fileOutputStream.close();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }
}
