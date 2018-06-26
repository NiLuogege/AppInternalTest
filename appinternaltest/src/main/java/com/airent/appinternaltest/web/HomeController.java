package com.airent.appinternaltest.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.Date;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(Model m) throws Exception {

        return "home";
    }

    /**
     * 上传应用页面
     * @return
     */
    @RequestMapping("/upload")
    public String upload() {
        return "upload";
    }

    /**
     * 上传应用
     * @return
     */
    @RequestMapping("/doUpload")
    public String doUpload() {
        return "upload";
    }
}