package com.airent.appinternaltest.controller;

import com.airent.appinternaltest.bean.App;
import com.airent.appinternaltest.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    AppService appService;

    @RequestMapping("/home")
    public String home(Model m) throws Exception {
        List<App> apps = appService.getAll();
        m.addAttribute("apps", apps);
        return "home";
    }

    /**
     * 上传应用页面
     *
     * @return
     */
    @RequestMapping("/upload")
    public String upload() {
        return "upload";
    }

    /**
     * 上传应用
     *
     * @return
     */
    @RequestMapping("/doUpload")
    public String doUpload(HttpSession session, HttpServletRequest request) throws IOException {
        App app = new App();
        long startTime = System.currentTimeMillis();

        //获取存储app文件夹的路径
        String appPath = session.getServletContext().getRealPath("/app");
        File appRootDir = new File(appPath);
        if (!appRootDir.exists()) {
            System.out.println("存储app的文件夹不存在 appPath= " + appPath);
            appRootDir.mkdirs();
        } else {
            System.out.println("存储app的文件夹存在 appPath= " + appPath);
        }


        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            Iterator<String> names = multiRequest.getFileNames();

            while (names.hasNext()) {
                MultipartFile file = multiRequest.getFile(names.next().toString());
                if (file != null) {
                    String filename = file.getOriginalFilename();
                    app.setAppName(filename);
                    File appFile = new File(appRootDir, filename);
                    file.transferTo(appFile);
                }
            }
        }

        app.setCreateData(new Date());
        app.setDownloadUrl("url");
        appService.insert(app);

        long endTime = System.currentTimeMillis();


        System.out.println("上传时间：" + String.valueOf(endTime - startTime) + "ms");
        return "home";
    }
}