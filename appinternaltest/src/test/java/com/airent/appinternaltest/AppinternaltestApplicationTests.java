package com.airent.appinternaltest;

import com.airent.appinternaltest.bean.App;
import com.airent.appinternaltest.service.AppService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppinternaltestApplicationTests {

    @Autowired
    AppService appService;

    @Test
    public void contextLoads() {
//        List<App> all = appService.getAll();
        App app  = new App();
        app.setDownloadUrl("url");
        app.setAppName("name");
        app.setCreateData(new Date());
        appService.insert(app);
    }

}
