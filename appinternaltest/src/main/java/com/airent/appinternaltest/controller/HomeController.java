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
     * 下载文件
     *
     * @return
     */
    @RequestMapping("/download")
    public void doDownload(HttpSession session, HttpServletResponse response,String md5Name) {
        String fileName = md5Name+".app";
        if (null != fileName) {
            //文件存储路径
            String appPath = session.getServletContext().getRealPath("/app");
            File app = new File(appPath, fileName);
            if (app.exists()) {
                response.setContentType("application/force-download");//设置强制下载
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);//设置文件名
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
                    app.setDownloadUrl("app/" + app.getAppName());

                    makeQRImage(app, appRootDir, request);

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

                System.out.println("localName=" + localName);

                File qr = new File(qrDirs, md5Name + ".png");
                String prPath = "http://" + localName + "/app/qr/" + appName;
                app.setQrPath(prPath);
                QRCodeUtil.qrCodeEncode(prPath, qr);
            }
        }
    }
}