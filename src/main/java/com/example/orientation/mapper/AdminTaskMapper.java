package com.example.orientation.mapper;

import com.example.orientation.model.dto.Admin.AdminStudentTaskDto;
import com.example.orientation.model.dto.Admin.AdminTaskDto;
import com.example.orientation.model.dto.Admin.ReviewImageDto;
import com.example.orientation.model.po.Admin.AdminTaskPo;
import com.example.orientation.model.po.Admin.AdminTaskReviewPo;

import java.util.List;

public interface AdminTaskMapper {

    //查询所有任务
    List<AdminTaskPo> allTask();

    //根据任务id查询任务是否存在
    AdminTaskPo selectById(String taskId);

    //添加任务
    int insertTask(AdminTaskDto adminTaskDto);

    //修改任务
    int updateTask(AdminTaskDto adminTaskDto);

    //根据id删除任务
    int deleteTask(String id);

    //修改任务的发布状态
    int updateStatus(String taskId, Integer status);

    //上传图片
    int uploadToImage(String image,String taskId);

    //手动审核
    int reviewImages(ReviewImageDto imageDto);

    //查询所有需审核的任务是否成功完成
    List<AdminTaskReviewPo> allManualReview();

    //修改审核状态为成功
    int updateReviewToSuccess(String reviewId, Integer successStatus);

    //根据手机号查询学生id
    String selectStudentByPhone(String account);

    //根据学号查询学生id
    String selectStudentByStudentNumber(String account);

    //根据任务id查询任务分数
    Double selectScoreById(String taskId);

    //为学生添加积分
    int addScoreByStudent(AdminStudentTaskDto buildDto);

    //任务失败，修改状态
    int updateReviewToFail(String reviewId, Integer failStatus);
}
