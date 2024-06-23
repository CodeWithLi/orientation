package com.example.orientation.mapper;


import com.example.orientation.model.dto.Admin.ReviewImageDto;
import com.example.orientation.model.dto.Mobile.MobileReviewDto;
import com.example.orientation.model.po.Mobile.MobileTaskPo;


public interface MobileTaskMapper {


    String selectStudentByAccount(String phone,String studentNumber);


    //根据手机号查询personId
    MobileReviewDto selectStudentByPhone(String account);


    //根据学号查询personId
    MobileReviewDto selectStudentByStudentNumber(String account);


    //手动审核任务是否完成
    int reviewImages(ReviewImageDto imageDto);


    //根据任务id查询任务
    MobileTaskPo selectTaskById(String taskId);


    //为学生添加积分
    int addScoreByStudent(MobileReviewDto dto);
}
