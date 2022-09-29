package com.zk.dirt.intef;

import org.springframework.web.multipart.MultipartFile;

/**
 * 资源上传接口，由使用者实现
 */
public interface iResourceUploader {
    String store(MultipartFile file);
}
