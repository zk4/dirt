package com.zk.dirt.intef;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 资源上传接口，由使用者实现
 */
public interface iResourceUploader {
    @Data
    class ImageRes {
        String name;
        String url;
        String ext;
    }
    List<ImageRes> store(List<MultipartFile> file, String id);
}
