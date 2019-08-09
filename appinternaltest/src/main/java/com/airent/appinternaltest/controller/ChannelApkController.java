package com.airent.appinternaltest.controller;

import com.airent.appinternaltest.utils.CmdUtils;
import com.airent.appinternaltest.utils.ZipUtils;
import com.mysql.jdbc.log.LogUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

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
            String zipalign = channelPath + File.separator + "zipalign -v 4 " + jiaguNosignApkPath + jiaguZipalignApkPath;
            String zipalignResult = CmdUtils.execCmd(zipalign, false);
            System.out.println("channel--> zipalignResult--> " + zipalignResult);

            //签名
            String sign = "java -jar " + channelPath + File.separator + "apksigner.jar sign --ks " + keyFilePath + " --ks-pass pass:Aihuishou99 --key-pass pass:Aihuishou99 --out " + jiaguSignApkPath + " " + jiaguZipalignApkPath;
            String signResult = CmdUtils.execCmd(sign, true);
            System.out.println("channel--> signResult--> " + signResult);

            //打渠道包
            String writeChannel = "java -jar " + channelPath + File.separator + "walle.jar batch -c meituan,meituan2,meituan3 " + jiaguSignApkPath + " " + channelDir.getAbsolutePath();
            String writeResult = CmdUtils.execCmd(writeChannel, true);
            System.out.println("channel--> writeResult--> " + writeResult);


            String imageZipPath = tempDir + File.separator + version + "_channle.zip";
            File[] files = channelDir.listFiles();
            boolean zipSuccess = ZipUtils.zipFiles(files, imageZipPath);
            if (zipSuccess) {
                System.out.println("channel--> 压缩成功");


            } else {
                throw new RuntimeException("压缩失败");
            }

        } else {
            throw new RuntimeException("渠道号为空");
        }
    }


}
