package com.airent.appinternaltest.controller;

import com.airent.appinternaltest.utils.CmdUtils;
import com.airent.appinternaltest.utils.JsonUtil;
import com.airent.appinternaltest.utils.ZipUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChannelApkController {


    @RequestMapping("/lookChannle")
    public String lookChannles(HttpSession session, Model model) throws Exception {
        String channelPath = session.getServletContext().getRealPath("/channle");
        File channleDir = new File(channelPath);
        if (channleDir.exists() && channleDir.isDirectory()) {

            File channleFile = new File(channleDir, "channel");
            if (!channleFile.exists() || channleFile.isDirectory()) {
                channleFile.createNewFile();
            }

            List<String> channels = getChannelsToFile(channleFile);
            model.addAttribute("channels", JsonUtil.listToJson(channels));
        }

        return "lookChannle";
    }


    @RequestMapping("/editChannelFile")
    public String editChannelFile(HttpSession session, Model model) throws Exception {

        String channelPath = session.getServletContext().getRealPath("/channle");
        File channleDir = new File(channelPath);
        if (channleDir.exists() && channleDir.isDirectory()) {

            File channleFile = new File(channleDir, "channel");
            if (!channleFile.exists() || channleFile.isDirectory()) {
                channleFile.createNewFile();
            }

            List<String> channels = getChannelsToFile(channleFile);
            model.addAttribute("channels", JsonUtil.listToJson(channels));
        }

        return "editChannle";
    }

    @RequestMapping("/channel")
    public String channelApk(HttpSession session, Model model) throws Exception {

        String channelPath = session.getServletContext().getRealPath("/channle");
        File channleFile = new File(channelPath);
        ArrayList<String> versions = new ArrayList<>();
        if (channleFile.exists() && channleFile.isDirectory()) {
            File rootApkDir = new File(channleFile, "rootApk");
            if (rootApkDir.exists() && rootApkDir.isDirectory()) {
                File[] apks = rootApkDir.listFiles();
                if (apks != null && apks.length > 0) {
                    for (int i = 0; i < apks.length; i++) {
                        File apk = apks[i];
                        if (apk != null) {
                            String name = apk.getName();
                            if (!StringUtils.isEmpty(name)) {
                                String lowerCaseName = name.toLowerCase();
                                String[] split = lowerCaseName.split("_");
                                if (split.length > 3) {
                                    String version = split[1];
                                    String versionNum = version.substring(1, version.length());
                                    versions.add(versionNum);
                                } else {
                                    throw new RuntimeException("rootApk命名不规范");
                                }
                            }
                        }
                    }
                }
            }
        }

        model.addAttribute("versions", versions);
        return "channelApk";
    }

    /**
     * 生成apk
     *
     * @param channels 批量写入是需逗号","分隔
     */
    @RequestMapping("/startChannelApk")
    public void startChannelApk(HttpSession session, HttpServletResponse response, String channels, String version) throws Exception {
        if (!StringUtils.isEmpty(version)) {
            version = version.split(" ")[1].trim();

            System.out.println("channels= " + channels + " version= " + version);

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

                saveChannelToFile(channelPath, channels);


                //加固 没签名的apk路径
                String jiaguNosignApkPath = channelPath + File.separator + "rootApk" + File.separator + "XHJ_V" + version + "_jiagu_nosign.apk ";
                //对齐后的apk路径
                String jiaguZipalignApkPath = tempDir + File.separator + version + "_jiagu_zipalign.apk";
                //加固签名后的apk路径
                String jiaguSignApkPath = tempDir + File.separator + version + "_jiagu_sign.apk";
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
                String writeChannel = "java -jar " + channelPath + File.separator + "walle.jar batch -c " + channels + " " + jiaguSignApkPath + " " + channelDir.getAbsolutePath();
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

    /**
     * 保存渠道信息(会进行去重)
     *
     * @param channel
     * @param channelPath
     */
    private void saveChannelToFile(String channelPath, String channel) throws Exception {
        if (!StringUtils.isEmpty(channelPath)) {

            File channelDer = new File(channelPath);
            if (channelDer.exists() && channelDer.isDirectory()) {
                File channleFile = new File(channelDer, "channel");
                if (!channleFile.exists() || channleFile.isDirectory()) {
                    channleFile.createNewFile();
                }

                if (!StringUtils.isEmpty(channel)) {

                    List<String> channels = getChannelsToFile(channleFile);

                    FileOutputStream fos = new FileOutputStream(channleFile, true);
                    PrintStream ps = new PrintStream(fos);

                    if (channel.contains(",")) {
                        String[] split = channel.split(",");
                        for (int i = 0; i < split.length; i++) {

                            String c = split[i];
                            if (!channels.contains(c)) {
                                ps.println(c);
                            }

                        }
                    } else {
                        if (!channels.contains(channel)) {
                            ps.println(channel);
                        }
                    }

                    ps.close();
                    fos.close();
                }


            }

        }

    }


    /**
     * 获取文件中的渠道信息
     */
    private List<String> getChannelsToFile(File file) throws Exception {
        List<String> strs = new ArrayList<>();
        if (file != null) {
            String str = "";
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((str = br.readLine()) != null) {
                strs.add(str);
            }
        }

        return strs;
    }


}
