package com.example.orientation.service.impl;

import com.example.orientation.constant.AdminTaskConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.mapper.AdminTaskMapper;
import com.example.orientation.model.dto.Admin.AdminStudentTaskDto;
import com.example.orientation.model.dto.Admin.AdminTaskDto;
import com.example.orientation.model.dto.Admin.AdminTaskReviewDto;
import com.example.orientation.model.po.Admin.AdminTaskPo;
import com.example.orientation.model.vo.Admin.AdminTaskReviewVo;
import com.example.orientation.model.vo.Admin.AdminTaskVo;
import com.example.orientation.service.AdminTaskService;
import com.example.orientation.utils.MinioUtils;
import com.example.orientation.utils.ReviewImageUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
//@Transactional
public class AdminTaskServiceImpl implements AdminTaskService {

    @Resource
    private AdminTaskMapper adminTaskMapper;

    @Autowired
    private MinioUtils minioUtils;

    //查询所有任务
    @Override
    public List<AdminTaskVo> allTask() {
        List<AdminTaskVo> vos = adminTaskMapper.allTask().stream().map(adminTaskPo -> {
            AdminTaskVo vo = new AdminTaskVo();
            BeanUtils.copyProperties(adminTaskPo, vo);
            if (!StringUtils.isBlank(adminTaskPo.getImageName()) && adminTaskPo.getImageName() != "") {
                vo.setImage(minioUtils.preview(adminTaskPo.getImageName()));
            }
            return vo;
        }).collect(Collectors.toList());

        return vos;
    }

    //添加任务
    @Override
    public boolean addTask(AdminTaskDto adminTaskDto) {
        //根据id查询内容是否已存在
        AdminTaskPo po = adminTaskMapper.selectById(adminTaskDto.getTaskId());
        if (po != null) {
            throw new BaseException("任务id已存在");
        }
        adminTaskDto.setStatus(AdminTaskConstant.UN_PUBLISHED);
        int result = adminTaskMapper.insertTask(adminTaskDto);
        ThrowUtils.throwIf(result < 0, "后台添加任务失败");
        return true;
    }

    //修改任务
    @Override
    public boolean editTask(AdminTaskDto adminTaskDto) {
        int result = adminTaskMapper.updateTask(adminTaskDto);
        ThrowUtils.throwIf(result < 0, "后台修改任务失败");
        return true;
    }

    @Override
    public boolean removeTask(String id) {
        int result = adminTaskMapper.deleteTask(id);
        ThrowUtils.throwIf(result < 0, "后台删除任务失败");
        return true;
    }

    @Override
    public boolean editStatus(String taskId, Integer status) {
        if (!Objects.equals(status, AdminTaskConstant.UN_PUBLISHED)
                && !Objects.equals(status, AdminTaskConstant.PUBLISHED)) {
            throw new BaseException("更新失败");
        }
        int result = adminTaskMapper.updateStatus(taskId, status);
        ThrowUtils.throwIf(result < 0, "后台修改任务状态失败");
        return true;
    }

    @Override
    public String profileImage(MultipartFile file, String taskId) {
        int result = 0;
        String preview = null;
        //先判断数据库是否有该广告
        AdminTaskPo po = adminTaskMapper.selectById(taskId);
        if (po == null) {
            throw new BaseException("没有该任务");
        } else if (StringUtils.isEmpty(po.getImageName()) && "".equals(po.getImageName())) {
            //有广告无头像或视频，添加头像地址
            String uploadNewImage = minioUtils.upload(file);
            result = adminTaskMapper.uploadToImage(uploadNewImage, taskId);
            preview = minioUtils.preview(uploadNewImage);
        } else {
//            有广告图片或视频，删除minio中头像地址
            minioUtils.remove(po.getImageName());
            //再次上传头像
            String uploadImage = minioUtils.upload(file);
            result = adminTaskMapper.uploadToImage(uploadImage, taskId);
            preview = minioUtils.preview(uploadImage);
        }
        if (result == 1 && preview != null) {
            return preview;
        }
        throw new BaseException("上传头像失败");
    }

    //通过id查询出该任务的图片
    @Override
    public boolean autoReviewImage(MultipartFile file1, String taskId) {
        //根据id查询该任务
        AdminTaskPo po = adminTaskMapper.selectById(taskId);
        String previewImage = minioUtils.preview(po.getImageName());
        Double percentage = ReviewImageUtils.AutoReview(file1, previewImage);
        //判断手机号 一般用学号当积分记录
        ThrowUtils.throwIf( percentage > 5.0, "后台添加任务失败");
        return true;

    }

    //返回所有需手动审核的任务
    @Override
    public List<AdminTaskReviewVo> allManualReview() {
        //查询所有需手动审核的任务
        List<AdminTaskReviewVo> vos = adminTaskMapper.allManualReview().stream().map(po -> {
            AdminTaskReviewVo vo = new AdminTaskReviewVo();
            BeanUtils.copyProperties(po, vo);
            //通过id得到该任务图片
            String taskImageName = adminTaskMapper.selectById(po.getTaskId()).getImageName();
            if (StringUtils.isAnyBlank(taskImageName, po.getReviewImageName())
                    && taskImageName == "" && po.getReviewImageName() == "") {
                throw new BaseException("返回参数出错");
            }
            String taskImage = minioUtils.preview(taskImageName);
            String reviewImage = minioUtils.preview(po.getReviewImageName());
            vo.setTaskImage(taskImage);
            vo.setReviewImage(reviewImage);
            vo.setStatus(AdminTaskConstant.UN_SUCCESS);
            return vo;
        }).collect(Collectors.toList());
        return vos;
    }

    //审核成功，给用户加积分，并且修改该任务
    @Override
//    @Transactional
    public boolean manualReviewSuccess(AdminTaskReviewDto dto) {
        log.info("Service审核成功，给用户加积分，并且修改该任务");

        String reviewId = dto.getReviewId();
        Integer successStatus=AdminTaskConstant.SUCCESS;
        //先将该任务设为已完成状态
        int resultReview=adminTaskMapper.updateReviewToSuccess(reviewId,successStatus);
        if (resultReview!=1){
            throw new BaseException("更改任务状态失败");
        }
        //给用户加积分
        //根据任务id查询该任务对应的分数
        Double score=adminTaskMapper.selectScoreById(dto.getTaskId());
        //根据account查询学生id
        String studentId = null;
        if (dto.getAccount().matches("^1[3456789]\\d{9}$")) {
            studentId = adminTaskMapper.selectStudentByPhone(dto.getAccount());
        } else {
            studentId = adminTaskMapper.selectStudentByStudentNumber(dto.getAccount());
        }
        AdminStudentTaskDto buildDto = AdminStudentTaskDto.builder()
                                        .studentTaskId(UUID.randomUUID().toString())
                                        .studentId(studentId)
                                        .taskId(dto.getTaskId())
                                        .score(score)
                                        .status(AdminTaskConstant.SUCCESS).build();
        int result=adminTaskMapper.addScoreByStudent(buildDto);
        ThrowUtils.throwIf( result!=1, "任务通过不成功");
        return true;
    }

    //任务通过失败，修改对应状态为失败
    @Override
    public boolean manualReviewFail(String reviewId) {
        Integer failStatus=AdminTaskConstant.FAIL;
        int result=adminTaskMapper.updateReviewToFail(reviewId,failStatus);
        ThrowUtils.throwIf( result!=1, "修改状态失败");
        return true;
    }

}
