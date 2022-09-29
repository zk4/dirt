package com.zk.dirt;


import com.zk.dirt.intef.iResourceUploader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ResourceUploaderImpl implements iResourceUploader {
    @Override
    public String store(MultipartFile file) {

         log.info(file.getOriginalFilename() + "收到了");
         return "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png";
    }
}
