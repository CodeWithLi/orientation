package com.example.orientation.controller;

import com.example.orientation.common.Result;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.model.dto.FaceIdentify.VerifyFaceDto;
import com.example.orientation.model.dto.Mobile.MobileTaskDto;
import com.example.orientation.service.MobileTaskService;
import com.example.orientation.utils.FaceIdentifyUtils;
import com.example.orientation.utils.ReducedCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 移动端任务信息
 */
@RestController
@RequestMapping("/mobile/task")
@Slf4j
public class MobileTaskController {

    @Autowired
    private MobileTaskService mobileTaskService;


    /**
     * 人脸识别
     * @param file
     * @return
     */
    @PostMapping("/Identify")
    public Result FaceIdentify(@RequestParam MultipartFile file){
        ReducedCodeUtils.file(file);
        boolean result=mobileTaskService.FaceIdentify(file);
        ThrowUtils.throwIf(!result,"识别失败,请重新上传");
        return Result.success("识别成功");
    }

    /**
     * 任务打卡的自动审核
     * @param file
     * @param taskId
     * @return
     */
    @PostMapping("/autoReview")
    public Result autoReviewImage(@RequestParam("file") MultipartFile file,
                                  @RequestParam String taskId){
        if (StringUtils.isBlank(taskId)){
            return Result.error("请求参数错误");
        }
        ReducedCodeUtils.file(file);
        boolean result=mobileTaskService.autoReviewImage(file,taskId);
        ThrowUtils.throwIf(!result,"审核失败,请转手动审核");
        return Result.success("审核成功");
    }

    /**
     * 上传需手动审核的任务
     * @param file
     * @param taskId
     * @return
     */
    @PostMapping("/manualReview")
    public Result manualReviewImage(@RequestParam MultipartFile file,
                                    @RequestParam  String taskId){
        //判断文件大小
        ReducedCodeUtils.file(file);
        boolean result=mobileTaskService.manualReviewImage(file,taskId);
        ThrowUtils.throwIf(!result,"手动审核上传失败,请重新打卡任务");
        return Result.success("手动审核上传成功");
    }
}
