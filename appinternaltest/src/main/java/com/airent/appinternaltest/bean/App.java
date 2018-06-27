package com.airent.appinternaltest.bean;

import java.util.Date;

public class App {
    private int id;
    private String appName;
    private String md5Name;
    private Date createDate;
    private String downloadUrl;
    private String qrPath;


    public String getMd5Name() {
        return md5Name;
    }

    public void setMd5Name(String md5Name) {
        this.md5Name = md5Name;
    }
    public String getQrPath() {
        return qrPath;
    }

    public void setQrPath(String qrPath) {
        this.qrPath = qrPath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

}
