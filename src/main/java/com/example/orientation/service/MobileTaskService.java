package com.example.orientation.service;

import com.example.orientation.model.dto.FaceIdentify.VerifyFaceDto;
import org.springframework.web.multipart.MultipartFile;

public interface MobileTaskService {

    //实现人脸识别
    boolean FaceIdentify(MultipartFile file);

    //将需要手动审核的打卡存入数据库
    boolean manualReviewImage(MultipartFile file, String taskId);

    //实现任务打卡的自动审核
    boolean autoReviewImage(MultipartFile file, String taskId);
}
