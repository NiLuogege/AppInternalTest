package com.airent.appinternaltest.service;

import com.airent.appinternaltest.bean.App;

import java.util.List;

public interface AppService {
    List<App> getAll();
    void insert(App app);
    void delete(int id);
}
