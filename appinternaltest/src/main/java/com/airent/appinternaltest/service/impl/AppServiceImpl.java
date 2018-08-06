package com.airent.appinternaltest.service.impl;

import com.airent.appinternaltest.bean.App;
import com.airent.appinternaltest.mapper.AppMapper;
import com.airent.appinternaltest.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    AppMapper appMapper;

    @Override
    public List<App> getAll() {
        return appMapper.getAll();
    }

    @Override
    public void insert(App app) {
        if (null != app)
            appMapper.insert(app);
    }

    @Override
    public void delete(int id) {
        appMapper.delete(id);
    }

    @Override
    public List<App> getAppByAppName(String appName) {
        return appMapper.getAppByAppName(appName);
    }
}
