package com.airent.appinternaltest.controller;

import com.airent.appinternaltest.bean.App;
import com.airent.appinternaltest.service.AppService;
import com.airent.appinternaltest.utils.Md5Utils;
import com.airent.appinternaltest.utils.QRCodeUtil;
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
                    String hash = Md5Utils.hash(filename);
                    app.setAppName(filename);
                    app.setMd5Name(hash);
                    File appFile = new File(appRootDir, filename);
                    file.transferTo(appFile);
                }
            }
        }


        makeQRImage(app, appRootDir,request);

        app.setCreateDate(new Date());
        app.setDownloadUrl("app/" + app.getAppName());

        appService.insert(app);

        long endTime = System.currentTimeMillis();


        System.out.println("上传时间：" + String.valueOf(endTime - startTime) + "ms");
        return "redirect:/home";
    }

    /**
     * 生成二维码
     *
     * @param app
     * @param appRootDir
     * @param request
     * @throws IOException
     */
    private void makeQRImage(App app, File appRootDir, HttpServletRequest request) throws IOException {
        if (app != null) {
            String appName = app.getAppName();
            String md5Name = app.getMd5Name();
            if (null != appRootDir && null != appName && !"".equals(appName)) {
                if (!appRootDir.exists()) {
                    appRootDir.mkdirs();
                }

                File qrDirs = new File(appRootDir, "qr");
                if (!qrDirs.exists()) {
                    qrDirs.mkdirs();
                }

                String localName = request.getLocalAddr();

                System.out.println("localName="+localName);

                File qr = new File(qrDirs, md5Name+".png");
                String prPath = "http://"+localName+"/app/qr/" + appName;
                app.setQrPath(prPath);
                QRCodeUtil.qrCodeEncode(prPath, qr);
            }
        }
    }
}