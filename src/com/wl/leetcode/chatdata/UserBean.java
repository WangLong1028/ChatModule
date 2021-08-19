package com.wl.leetcode.chatdata;

public class UserBean {
    private int id;
    private String userName;
    private int userHeadshot;

    public UserBean() {
    }

    public UserBean(int id, String userName, int userHeadshot) {
        this.id = id;
        this.userName = userName;
        this.userHeadshot = userHeadshot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserHeadshot() {
        return userHeadshot;
    }

    public void setUserHeadshot(int userHeadshot) {
        this.userHeadshot = userHeadshot;
    }
}
