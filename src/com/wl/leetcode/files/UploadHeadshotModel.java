package com.wl.leetcode.files;

import com.wl.leetcode.constant.NetworkConstant;
import com.wl.leetcode.helper.SocketHelper;

import java.io.IOException;
import java.net.Socket;

public class UploadHeadshotModel {

    public UploadHeadshotModel(String path, int id, HeadshotUpload headshotUpload) {
        try {
            Socket socket = new Socket(NetworkConstant.SERVER_IP, NetworkConstant.SERVER_PORT);
            headshotUpload.uploading();
            if (SocketHelper.uploadHeadshotPic(socket, id, path)) {
                headshotUpload.uploadSuccess();
            } else {
                headshotUpload.uploadFailed();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface HeadshotUpload {
        void uploading();

        void uploadSuccess();

        void uploadFailed();
    }
}
