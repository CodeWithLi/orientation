package com.example.orientation.controller;

import com.example.orientation.common.Result;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.model.dto.Admin.AdminTaskDto;
import com.example.orientation.model.dto.Admin.AdminTaskReviewDto;
import com.example.orientation.model.dto.Admin.EditDto;
import com.example.orientation.model.vo.Admin.AdminTaskReviewVo;
import com.example.orientation.model.vo.Admin.AdminTaskVo;
import com.example.orientation.service.AdminTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 后台任务信息
 */
@RestController
@RequestMapping("/admin/task")
@Slf4j
public class AdminTaskController {

    @Autowired
    private AdminTaskService adminTaskService;

    /**
     * 查询所有任务
     * @return
     */
    @GetMapping("/all")
    public Result allTask() {
        List<AdminTaskVo> vos = adminTaskService.allTask();
        return Result.success(vos);
    }

    /**
     * 增加任务
     * @param adminTaskDto
     * @return
     */
    @PostMapping("/add")
    public Result addTask(@RequestBody AdminTaskDto adminTaskDto){
        log.info("数据"+adminTaskDto.toString());
        log.info("数据"+adminTaskDto);
        log.info("数据"+adminTaskDto.getCreateTime()+"=="+adminTaskDto.getDeadTime());
        if (adminTaskDto==null){
            return Result.error("传输数据为空");
        }
        boolean result=adminTaskService.addTask(adminTaskDto);
        ThrowUtils.throwIf(!result,"添加失败");
        return Result.success("添加成功");
    }

    /**
     * 编辑任务内容
     * @param adminTaskDto
     * @return
     */
    @PutMapping("/edit")
    public Result editTask(@RequestBody AdminTaskDto adminTaskDto){
        if (adminTaskDto==null){
            return Result.error("传输数据为空");
        }
        boolean result=adminTaskService.editTask(adminTaskDto);
        ThrowUtils.throwIf(!result,"修改失败");
        return Result.success("修改成功");
    }

    /**
     * 删除任务
     * @param id
     * @return
     */
    @DeleteMapping("/remove/{id}")
    public Result removeTask(@PathVariable String id){
        if (StringUtils.isBlank(id)){
            return Result.error("删除失败,id为空");
        }
        boolean result=adminTaskService.removeTask(id);
        ThrowUtils.throwIf(!result,"删除失败");
        return Result.success("删除成功");
    }

    /**
     * 根据id修改任务的发布状态
     * @param editDto
     * @return
     */
    @PutMapping("/editStatus")
    public Result editStatus(@RequestBody EditDto editDto){
        String taskId = editDto.getId();
        Integer status = editDto.getStatus();
        if (StringUtils.isBlank(taskId)){
            return Result.error("请求参数错误");
        }
        boolean result= adminTaskService.editStatus(taskId,status);
        ThrowUtils.throwIf(!result,"更改失败");
        return Result.success("更改成功");
    }


    /**
     * 根据id上传图片
     * @param file
     * @param taskId
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, @RequestParam String taskId){
        if (file==null){
            return Result.error("文件为空");
        }
        String contentType = file.getContentType();
        if (!contentType.startsWith("image")) {
            throw new BaseException("请正确上传图片");
        }
        long maxImageSizeBytes = 10 * 1024 * 1024;
        if (file.getSize() > maxImageSizeBytes) {
            throw new BaseException("图片大小最大为10MB");
        }
        String image = adminTaskService.profileImage(file, taskId);
        ThrowUtils.throwIf(image == null,"头像回显链接失败");
        return Result.success(image);
    }

    /**
     * 返回所有需手动审核的任务
     * @return
     */
    @GetMapping("/allManualReview")
    public Result allManualReview(){
        log.info("返回所有");
        List<AdminTaskReviewVo> vos=adminTaskService.allManualReview();
        return Result.success(vos);
    }

    /**
     * 任务通过
     * @param dto
     * @return
     */
    @PutMapping("/manualReview/success")
    public Result manualReviewSuccess(@RequestBody AdminTaskReviewDto dto){
        log.info("Controller任务通过");
        if (dto==null){
            return Result.error("请求参数错误");
        }
        boolean result=adminTaskService.manualReviewSuccess(dto);
        ThrowUtils.throwIf(!result,"任务通过不成功");
        return Result.success("任务通过成功");
    }

    /**
     * 任务不通过
     * @param dto
     * @return
     */
   @PutMapping("/manualReview/fail")
    public Result manualReviewFail(@RequestBody AdminTaskReviewDto dto){
       String reviewId = dto.getReviewId();
       if (StringUtils.isBlank(reviewId)){
           return Result.error("请求参数错误");
       }
       boolean result=adminTaskService.manualReviewFail(reviewId);
       ThrowUtils.throwIf(!result,"任务审核失败");
       return Result.success("不通过");
   }


}
