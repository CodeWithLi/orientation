package com.example.orientation.service;

import com.example.orientation.model.dto.Admin.AdminTaskDto;
import com.example.orientation.model.dto.Admin.AdminTaskReviewDto;
import com.example.orientation.model.vo.Admin.AdminTaskReviewVo;
import com.example.orientation.model.vo.Admin.AdminTaskVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminTaskService {

    //查询所有任务
    List<AdminTaskVo> allTask();

    //添加任务
    boolean addTask(AdminTaskDto adminTaskDto);

    //修改任务
    boolean editTask(AdminTaskDto adminTaskDto);

    //删除任务
    boolean removeTask(String id);

    //更改任务的发布状态
    boolean editStatus(String taskId, Integer status);

    //上传任务打卡图片
    String profileImage(MultipartFile file, String taskId);

    //自动审核
    boolean autoReviewImage(MultipartFile file1, String taskId);
//
//    //手动审核
//    boolean manualReviewImage(MultipartFile file);

    //返回所有需手动审核的任务
    List<AdminTaskReviewVo> allManualReview();

    //审核成功
    boolean manualReviewSuccess(AdminTaskReviewDto dto);

    //审核失败
    boolean manualReviewFail(String reviewId);
}
