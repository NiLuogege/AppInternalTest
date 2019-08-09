package com.airent.appinternaltest.controller;

import com.airent.appinternaltest.utils.CmdUtils;
import com.airent.appinternaltest.utils.ZipUtils;
import com.mysql.jdbc.log.LogUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class ChannelApkController {

    @RequestMapping("/channel")
    public String channelApk(Model model) throws Exception {
        return "channelApk";
    }

    /**
     * 生成apk
     *
     * @param channels 批量写入是需逗号","分隔
     */
    @RequestMapping("/startChannelApk")
    public void startChannelApk(HttpSession session, HttpServletResponse response, String channels) throws Exception {
        System.out.println("channels= " + channels);

        String version = "3.3.3";

        if (!StringUtils.isEmpty(channels)) {
            String channelPath = session.getServletContext().getRealPath("/channle");

            //创建临时文件夹
            File tempDir = new File(channelPath, "tempDir");
            if (!tempDir.exists() || tempDir.isFile()) {
                tempDir.mkdirs();
            }

            //渠道包存储路径
            File channelDir = new File(tempDir, version + "_channle");
            if (!channelDir.exists() || channelDir.isFile()) {
                channelDir.mkdirs();
            }


            //加固 没签名的apk路径
            String jiaguNosignApkPath = channelPath + File.separator + "rootApk" + File.separator + "XHJ_V3.3.3_jiagu_nosign.apk ";
            //对齐后的apk路径
            String jiaguZipalignApkPath = tempDir + File.separator + "3.3.3_jiagu_zipalign.apk";
            //加固签名后的apk路径
            String jiaguSignApkPath = tempDir + File.separator + "3.3.3_jiagu_sign.apk";
            //签名文件
            String keyFilePath = channelPath + File.separator + "observer_app.keystore";


            //zip对齐
            String zipalignTool = channelPath + File.separator + "zipalign";
            if (!isWin()) {
                zipalignTool = channelPath + File.separator + "build_tools_linux_26.0.2" + File.separator + "zipalign";
            }
            String zipalign = zipalignTool + " -v 4 " + jiaguNosignApkPath + jiaguZipalignApkPath;
            String zipalignResult = CmdUtils.execCmd(zipalign, false);
            System.out.println("channel--> zipalignResult--> " + zipalignResult);

            //签名
            String sign = "java -jar " + channelPath + File.separator + "apksigner.jar sign --ks " + keyFilePath + " --ks-pass pass:Aihuishou99 --key-pass pass:Aihuishou99 --out " + jiaguSignApkPath + " " + jiaguZipalignApkPath;
            String signResult = CmdUtils.execCmd(sign, true);
            System.out.println("channel--> signResult--> " + signResult);


            //校验签名结果
            String checkSign = "java -jar " + channelPath + File.separator + "CheckAndroidSignature.jar " + jiaguSignApkPath;
            String checkSignResult = CmdUtils.execCmd(checkSign, true);
            System.out.println("channel--> checkSignResult--> " + checkSignResult);


            //打渠道包
            String writeChannel = "java -jar " + channelPath + File.separator + "walle.jar batch -c meituan,meituan2,meituan3 " + jiaguSignApkPath + " " + channelDir.getAbsolutePath();
            String writeResult = CmdUtils.execCmd(writeChannel, true);
            System.out.println("channel--> writeResult--> " + writeResult);


            String zipName = version + "_channle.zip";
            String zipPath = tempDir + File.separator + zipName;
            File[] files = channelDir.listFiles();
            boolean zipSuccess = ZipUtils.zipFiles(files, zipPath);
            if (zipSuccess) {
                System.out.println("channel--> 压缩成功");

                downloadZip(response, zipPath, zipName);

                //下载完成后删除临时文件
                deleteFile(tempDir);
                System.out.println("channel--> 删除成功");

            } else {
                throw new RuntimeException("压缩失败");
            }

        } else {
            throw new RuntimeException("渠道号为空");
        }
    }


    private void downloadZip(HttpServletResponse response, String zipPath, String fileName) {
        File zip = new File(zipPath);
        if (zip.exists()) {
            response.setContentType("application/force-download");//设置强制下载
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);//设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(zip);
                bis = new BufferedInputStream(fis);
                ServletOutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("channel--> 下载成功");
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

    /**
     * 是 win 还是 linux
     *
     * @return
     */
    private boolean isWin() {
        String os = System.getProperty("os.name");
        System.out.println("os= " + os);
        return os.toLowerCase().startsWith("win");
    }

    /**
     * 递归删除文件
     *
     * @param f
     */
    public static void deleteFile(File f) {
        File[] b = f.listFiles();//获取包含file对象对应的子目录或者文件
        for (int i = 0; i < b.length; i++) {
            if (b[i].isFile()) {//判断是否为文件
                b[i].delete();//如果是就删除
            } else {
                deleteFile(b[i]);//否则重新递归到方法中
            }
        }
        f.delete();//最后删除该目录中所有文件后就删除该目录
    }


}
