package com.wl.leetcode;

import com.wl.leetcode.files.UploadHeadshotModel;

public class FileView implements UploadHeadshotModel.HeadshotUpload {
    public void run() {
        String fileName = "D:/Test/big.zip";
        new UploadHeadshotModel(fileName, 1, this);
    }

    @Override
    public void uploading() {
        System.out.println("上传文件中。。。");
    }

    @Override
    public void uploadSuccess() {
        System.out.println("上传成功！");
    }

    @Override
    public void uploadFailed() {
        System.out.println("上传失败！");
    }
}
