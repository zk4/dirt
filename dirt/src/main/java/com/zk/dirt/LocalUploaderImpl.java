package com.zk.dirt;


import com.zk.dirt.intef.iResourceUploader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class LocalUploaderImpl implements iResourceUploader {

    @Autowired
    private ServletContext context;

    @Value("${server.port}")
    private int serverPort;


    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
        }
        return "";
    }

    /**
     * 将inputStream写入文件
     *
     * @param stream 文件流
     * @param path   要写入的文件路径
     */
    private static void write(InputStream stream, String path) {
        try {
            FileUtils.copyInputStreamToFile(stream, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ImageRes> store(List<MultipartFile> multipartFiles, String id) {
        List<ImageRes> imageRes1 = new LinkedList<>();
        for (MultipartFile file : multipartFiles) {
            //参数校验
            if (file == null) {
                throw new IllegalArgumentException("file or filename object is null");
            }
            // 获取文件类型
            String contentType = file.getContentType();
            // 获取文件后缀
            String ext = contentType.substring(contentType.lastIndexOf("/") + 1);
            // uuid生成文件名 + 后缀
            String fileName = UUID.randomUUID() + "." + ext;
            // 返回url路径
            String url = "http://" + getIpAddress() + ":"+serverPort+"/statics/attachment/" + fileName;
            log.info("url：" + url);

            // 本地磁盘存储路径
            String filePath = context.getRealPath("/") + "statics/attachment/" + fileName;
            log.info("入库本地磁盘路径：" + filePath);
            // 写入文件
            try {
                write(file.getInputStream(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 返回数据
            ImageRes imageRes = new ImageRes();
            imageRes.setName(fileName);
            imageRes.setUrl(url);
            imageRes.setExt(ext);
            imageRes1.add(imageRes);
        }

        return imageRes1;

        ////参数校验
        //if (file == null) {
        //    throw new IllegalArgumentException("file or filename object is null");
        //}
        //// 获取文件类型
        //String contentType = file.getContentType();
        //// 获取文件后缀
        //String ext = contentType.substring(contentType.lastIndexOf("/") + 1);
        //// uuid生成文件名 + 后缀
        //String fileName = UUID.randomUUID() + "." + ext;
        //// 返回url路径
        //String url = "http://" + GetIpAddressUtil.getIpAddress() + ":8000/statics/attachment/" + fileName;
        //// 本地磁盘存储路径
        //String filePath = context.getRealPath("/") + "statics/attachment/" + fileName;
        //log.info("入库本地磁盘路径：" + filePath);
        //// 写入文件
        //write(file.getInputStream(), filePath);
        //// 返回数据
        //Map<String, String> dataMap = new HashMap<>(3);
        //dataMap.put("name", fileName);
        //dataMap.put("url", url);
        //dataMap.put("ext", ext);
        //return dataMap;

    }
}
