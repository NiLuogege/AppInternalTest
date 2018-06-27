package com.airent.appinternaltest.bean;

import java.util.Date;

public class App {
    private int id;
    private String appName;
    private Date createData;
    private String downloadUrl;

    public Date getCreateData() {
        return createData;
    }

    public void setCreateData(Date createData) {
        this.createData = createData;
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
                ", createData=" + createData +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
