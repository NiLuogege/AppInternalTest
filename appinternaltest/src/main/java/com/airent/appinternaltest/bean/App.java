package com.airent.appinternaltest.bean;

import java.util.Date;

public class App {
    private int id;
    private String appName;
    private Date createDate;
    private String downloadUrl;
    private String qrPath;

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

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", createDate=" + createDate +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
