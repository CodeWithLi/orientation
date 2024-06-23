package com.example.orientation.controller;




import cn.hutool.json.JSON;
import com.example.orientation.common.PageResult;
import com.example.orientation.common.Result;
import com.example.orientation.constant.UniteExceptionConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.model.IDCardInfo;
import com.example.orientation.model.dto.Admin.AdminAdvertiseDto;
import com.example.orientation.model.dto.Admin.EditDto;
import com.example.orientation.model.vo.Admin.AdminAdvertiseVo;
import com.example.orientation.service.AdminAdvertiseService;
import com.example.orientation.utils.HttpUtil;
import com.example.orientation.utils.MinioUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台广告信息
 */
@RestController
@RequestMapping("/admin/advertise")
@Slf4j
public class AdminAdvertiseController {
    @Autowired
    private AdminAdvertiseService adminAdvertiseService;

    /**
     * 查询所有广告
     *
     * @return
     */
    @GetMapping("/all")
    public Result allAdvertise() {
        log.info("查询所有广告");
        List<AdminAdvertiseVo> vos = adminAdvertiseService.queryAll();
        return Result.success(vos);
    }

    /**
     * 添加广告信息
     *
     * @param adminAdvertiseDto
     * @return
     */
    @PostMapping("/add")
    public Result addAdvertise(@Valid @RequestBody AdminAdvertiseDto adminAdvertiseDto) {
        if (adminAdvertiseDto == null) {
            return Result.error(UniteExceptionConstant.ERROR_RESULT);
        }
        boolean result = adminAdvertiseService.addAdvertise(adminAdvertiseDto);
        ThrowUtils.throwIf(!result, "添加失败");
        return Result.success("添加成功");
    }

    /**
     * 修改广告信息
     */
    @PutMapping("/edit")
    public Result editAdvertise(@Valid @RequestBody AdminAdvertiseDto adminAdvertiseDto) {
        if (adminAdvertiseDto == null) {
            return Result.error(UniteExceptionConstant.ERROR_RESULT);
        }
        boolean result = adminAdvertiseService.editAdvertise(adminAdvertiseDto);
        ThrowUtils.throwIf(!result, "更新失败");
        return Result.success("更新成功");
    }

    /**
     * 删除广告
     *
     * @param id
     * @return
     */
    @DeleteMapping("/remove/{id}")
    public Result removeAdvertise(@PathVariable String id) {
        boolean result = adminAdvertiseService.removeAdvertise(id);
        ThrowUtils.throwIf(!result, "删除失败");
        return Result.success("删除成功");
    }

    /**
     * 根据广告id上传图片或视频
     *
     * @param file
     * @param advertiseId
     * @return
     */
    @PostMapping("/uploadImageVideo")
    public Result uploadImageVideo(@RequestParam MultipartFile[] file, @RequestParam String advertiseId) {
        if (file == null) {
            return Result.error(UniteExceptionConstant.ERROR_RESULT);
        }
        for (MultipartFile multipartFile : file) {
            String contentType = multipartFile.getContentType();
            if (!contentType.startsWith("image") && !contentType.startsWith("video")) {
                throw new BaseException("请正确上传图片或视频");
            }
            long maxImageSizeBytes = 10 * 1024 * 1024;
            long maxVideoSizeBytes = 50 * 1024 * 1024;
            long fileSize = multipartFile.getSize();
            if ((contentType.startsWith("image") && fileSize > maxImageSizeBytes) ||
                    (contentType.startsWith("video") && fileSize > maxVideoSizeBytes)) {
                throw new BaseException(contentType.startsWith("image") ? "图片大小最大为10MB" : "视频大小最大为50MB");
            }
        }
        List<String> images = adminAdvertiseService.profileImage(file, advertiseId);
        ThrowUtils.throwIf(images == null, "头像回显链接失败");
        log.info("返回的数据：{}", Result.success(images));
        return Result.success(images);
    }

    /**
     * 更改发布状态
     *
     * @param editStatusDto
     * @return
     */
    @PutMapping("/edit/status")
    public Result editStatus(@RequestBody EditDto editStatusDto) {
        boolean result = adminAdvertiseService.editAdvertiseStatus(editStatusDto);
        ThrowUtils.throwIf(!result, "更新状态");
        return Result.success("更新成功");
    }

    /**
     * 点击广告时更新点击次数及总盈利
     *
     * @param editDto
     * @return
     */
    @PutMapping("/edit/clickandprofit")
    public Result editClickAndProfit(@RequestBody EditDto editDto) {
        log.info("更新点击次数及总盈利");
        boolean result = adminAdvertiseService.editClickAndProfit(editDto.getId());
        ThrowUtils.throwIf(!result, "更新失败");
        return Result.success("更新成功");
    }

    /**
     * 分页查询
     *
     * @param index
     * @param pages
     * @return
     */
    @GetMapping("/page")
    public Result queryByPage(@RequestParam Integer index, @RequestParam() Integer pages) {
        log.info("分页的页数:{}及数量为：{}", index, pages);
        PageResult result = adminAdvertiseService.queryByPage(index, pages);
        return Result.success(result);
    }

    /**
     * 返回所有草稿内容
     *
     * @return
     */
    @GetMapping("/all/draft")
    public Result allToDraft() {
        log.info("查询所有草稿内容");
        List<AdminAdvertiseVo> vos = adminAdvertiseService.queryAllDraft();
        return Result.success(vos);
    }


    /**
     * 添加内容到草稿
     *
     * @param adminAdvertiseDto
     * @return
     */
    @PostMapping("/add/draft")
    public Result addToDraft(@Valid @RequestBody AdminAdvertiseDto adminAdvertiseDto) {
        if (adminAdvertiseDto == null) {
            return Result.error(UniteExceptionConstant.ERROR_RESULT);
        }
        boolean result = adminAdvertiseService.addAdvertiseDraft(adminAdvertiseDto);
        ThrowUtils.throwIf(!result, "广告草稿添加失败");
        return Result.success("广告草稿添加成功");
    }


    /**
     * 修改草稿内容并提交
     *
     * @param adminAdvertiseDto
     * @return
     */
    @PutMapping("/edit/draft")
    public Result editToDraft(@Valid @RequestBody AdminAdvertiseDto adminAdvertiseDto) {
        if (adminAdvertiseDto == null) {
            return Result.error(UniteExceptionConstant.ERROR_RESULT);
        }
        boolean result = adminAdvertiseService.editAdvertiseDraft(adminAdvertiseDto);
        ThrowUtils.throwIf(!result, "广告草稿修改失败");
        return Result.success("广告草稿修改成功");
    }


    //更改上架广告到app状态
    @PostMapping("/shelvesStatus")
    public Result shelvesStatus(@RequestBody EditDto editDto) {
        Integer shelvesStatus = editDto.getStatus();
        if (shelvesStatus == null) {
            return Result.error(UniteExceptionConstant.ERROR_RESULT);
        }
        boolean result = adminAdvertiseService.shelvesStatus(shelvesStatus);
        ThrowUtils.throwIf(!result, "更改失败");
        return Result.success("更改成功");
    }
}
