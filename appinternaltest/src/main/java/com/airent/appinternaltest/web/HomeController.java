package com.airent.appinternaltest.web;

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
import java.util.Iterator;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(Model m) throws Exception {

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
                    File appFile = new File(appRootDir, file.getOriginalFilename());
                    file.transferTo(appFile);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("上传时间：" + String.valueOf(endTime - startTime) + "ms");
        return "home";
    }
}