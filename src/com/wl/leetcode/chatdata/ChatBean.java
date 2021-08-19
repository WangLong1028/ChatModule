package com.wl.leetcode.chatdata;

public class ChatBean {
    private  int id;
    private String contentText;
    private UserBean belongUser;

    public ChatBean() {
    }

    public ChatBean(String contentText, UserBean belongUser) {
        this.contentText = contentText;
        this.belongUser = belongUser;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public UserBean getBelongUser() {
        return belongUser;
    }

    public void setBelongUser(UserBean belongUser) {
        this.belongUser = belongUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
