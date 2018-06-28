package com.airent.appinternaltest.controller;

import com.airent.appinternaltest.bean.App;
import com.airent.appinternaltest.service.AppService;
import com.airent.appinternaltest.utils.Md5Utils;
import com.airent.appinternaltest.utils.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    AppService appService;

    @Value("${server.port}")
    private String erverPort;


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

    @RequestMapping("/delete")
    public String delete(HttpSession session, App app) {
        appService.delete(app.getId());

        //获取存储app文件夹的路径
        String appPath = session.getServletContext().getRealPath("/app");
        File appRootDir = new File(appPath);
        if (appRootDir.exists()) {
            File appFile = new File(appRootDir, app.getMd5Name());
            if (appFile.exists()) {
                appFile.delete();
            }

            File qrDirs = new File(appRootDir, "qr");
            if (qrDirs.exists()) {
                File qr = new File(qrDirs, app.getMd5Name() + ".png");
                qr.delete();
            }
        }


        return "redirect:/home";
    }


    /**
     * 下载文件
     *
     * @return
     */
    @RequestMapping("/download")
    public void doDownload(HttpSession session, HttpServletResponse response, String md5Name) {
        String fileName = md5Name;
        if (null != fileName) {
            //文件存储路径
            String appPath = session.getServletContext().getRealPath("/app");
            File app = new File(appPath, fileName);
            if (app.exists()) {
                response.setContentType("application/force-download");//设置强制下载
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".apk");//设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(app);
                    bis = new BufferedInputStream(fis);
                    ServletOutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    /**
     * 上传应用
     *
     * @return
     */
    @RequestMapping("/doUpload")
    public String doUpload(HttpSession session, HttpServletRequest request) throws IOException {
//        System.out.println("浏览器发出请求时的完整URL，包括协议 主机名 端口(如果有): " + request.getRequestURL());
//        System.out.println("浏览器发出请求的资源名部分，去掉了协议和主机名: " + request.getRequestURI());
//        System.out.println("请求行中的参数部分: " + request.getQueryString());
//        System.out.println("浏览器所处于的客户机的IP地址: " + request.getRemoteAddr());
//        System.out.println("浏览器所处于的客户机的主机名: " + request.getRemoteHost());
//        System.out.println("浏览器所处于的客户机使用的网络端口: " + request.getRemotePort());
//        System.out.println("服务器的IP地址: " + request.getLocalAddr());
//        System.out.println("服务器的主机名: " + request.getLocalName());
//        System.out.println("得到客户机请求方式: " + request.getMethod());

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

            if (names.hasNext()) {
                MultipartFile file = multiRequest.getFile(names.next().toString());
                if (file != null) {

                    Date date = new Date();
                    String filename = file.getOriginalFilename();
                    String Md5Name = Md5Utils.hash(filename + date.getTime());
                    File appFile = new File(appRootDir, Md5Name);
                    file.transferTo(appFile);

                    App app = new App();
                    app.setAppName(filename);
                    app.setMd5Name(Md5Name);
                    app.setCreateDate(date);
                    String downloadUrl = "http://" + request.getLocalName() + ":" + erverPort + "/download?md5Name=" + Md5Name;
                    app.setDownloadUrl(downloadUrl);

                    makeQRImage(app, appRootDir);

                    appService.insert(app);
                }
            }
        }

        return "redirect:/home";
    }

    /**
     * 生成二维码
     *
     * @param app
     * @param appRootDir
     * @throws IOException
     */
    private void makeQRImage(App app, File appRootDir) throws IOException {
        if (app != null) {
            String md5Name = app.getMd5Name();
            if (null != appRootDir && null != md5Name && !"".equals(md5Name)) {
                if (!appRootDir.exists()) {
                    appRootDir.mkdirs();
                }

                File qrDirs = new File(appRootDir, "qr");
                if (!qrDirs.exists()) {
                    qrDirs.mkdirs();
                }
                File qr = new File(qrDirs, md5Name + ".png");
                QRCodeUtil.qrCodeEncode(app.getDownloadUrl(), qr);
            }
        }
    }
}