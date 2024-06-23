package com.example.orientation.service.impl;


import com.example.orientation.common.PageResult;
import com.example.orientation.constant.AdminAdvertiseStatusConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.mapper.AdminAdvertiseMapper;
import com.example.orientation.model.dto.Admin.AdminAdvertiseDto;
import com.example.orientation.model.dto.Admin.EditDto;
import com.example.orientation.model.po.Admin.AdminAdvertisePo;
import com.example.orientation.model.vo.Admin.AdminAdvertiseVo;
import com.example.orientation.service.AdminAdvertiseService;
import com.example.orientation.utils.MinioUtils;
import com.example.orientation.utils.ReducedCodeUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
//@Transactional
@Slf4j
public class AdminAdvertiseServiceImpl implements AdminAdvertiseService {


    @Resource
    private AdminAdvertiseMapper adminAdvertiseMapper;


    @Autowired
    private MinioUtils minioUtils;


    @Autowired
    private ReducedCodeUtils reducedCodeUtils;


    //查询所有已添加广告广告
    @Override
    public List<AdminAdvertiseVo> queryAll() {
        List<AdminAdvertisePo> pos = adminAdvertiseMapper.selectAll();
//查询出所有广告
        List<AdminAdvertiseVo> vos = reducedCodeUtils.AdminByAllAdvertise(pos);
        return vos;
    }


    //点击一次时，更新点击次数及总盈利
    @Override
    public boolean editClickAndProfit(String id) {
//查询该条广告信息
        AdminAdvertisePo po = adminAdvertiseMapper.selectById(id);
        ThrowUtils.throwIf(adminAdvertiseMapper == null, "广告不存在");
        Long costs = po.costs;
        Long clickNumber = po.getClickNumber() + 1;
        Long totalProfit = costs * clickNumber;
        int result = adminAdvertiseMapper.UpdateClickAndProfit(id, clickNumber, totalProfit);
        ThrowUtils.throwIf(result != 1, "后台广告更新点击次数失败");
        return true;
    }


    //更新广告状态
    @Override
    public boolean editAdvertiseStatus(EditDto editDto) {
        String uuid = editDto.getId();
        Long status = Long.valueOf(editDto.getStatus());
        ThrowUtils.throwIf(status != AdminAdvertiseStatusConstant.PUBLISHED && status != AdminAdvertiseStatusConstant.UN_PUBLISHED, "更新状态失败");
        int result = adminAdvertiseMapper.updateAdvertiseStatus(uuid, status);
        ThrowUtils.throwIf(result != 1, "后台更新广告状态失败");
        return true;
    }


    //删除广告
    @Override
    public boolean removeAdvertise(String id) {
        int result = adminAdvertiseMapper.deleteAdvertiseById(id);
        return result == 1;
    }


    //添加广告
    @Override
    public boolean addAdvertise(AdminAdvertiseDto adminAdvertiseDto) {
//判断该广告是否已存在
        AdminAdvertisePo po = adminAdvertiseMapper.selectById(adminAdvertiseDto.getAdvertiseId());
        if (po != null) {
            throw new BaseException("该广告id已存在，请重新添加");
        }
        adminAdvertiseDto.setStatus(AdminAdvertiseStatusConstant.UN_PUBLISHED);
        adminAdvertiseDto.setAddStatus(AdminAdvertiseStatusConstant.ADD_STATUS);
        adminAdvertiseDto.setShelvesStatus(AdminAdvertiseStatusConstant.UN_SHELVES);
        int result = adminAdvertiseMapper.insertAdvertise(adminAdvertiseDto);
        ThrowUtils.throwIf(result != 1, "后台添加广告失败");
        return true;
    }


    //修改广告信息
    @Override
    public boolean editAdvertise(AdminAdvertiseDto adminAdvertiseDto) {
        int result = adminAdvertiseMapper.updateAdvertise(adminAdvertiseDto);
        ThrowUtils.throwIf(result != 1, "后台修改广告失败");
        return true;
    }


    // //上传广告图片或视频
// @Override
// public String profileImage(MultipartFile file, String advertiseId) {
// log.info("上传图片或视频service层");
// int result=0;
// String preview=null;
// //先判断数据库是否有该广告
// AdminAdvertisePo po = adminAdvertiseMapper.selectById(advertiseId);
// if (po==null){
// throw new BaseException("没有广告id");
// }else if (StringUtils.isBlank(po.getImageVideoName())&&"".equals(po.getImageVideoName())) {
// //有广告无头像或视频，添加头像地址
// String uploadNewImage = minioUtils.upload(file);
// result = adminAdvertiseMapper.updateToImage(uploadNewImage, advertiseId);
// preview = minioUtils.preview(uploadNewImage);
// }else {
//// 有广告图片或视频，删除minio中头像地址
// minioUtils.remove(po.getImageVideoName());
// //再次上传头像
// String uploadImage = minioUtils.upload(file);
// result= adminAdvertiseMapper.updateToImage(uploadImage, advertiseId);
// preview = minioUtils.preview(uploadImage);
// }
// if (result==1&&preview!=null){
// return preview;
// }
// throw new BaseException("上传头像失败");
// }
//
//上传广告图片或视频
    @Override
    public List<String> profileImage(MultipartFile[] file, String advertiseId) {
//        log.info("上传图片或视频service层");
        //先判断数据库是否有该广告
        AdminAdvertisePo po = adminAdvertiseMapper.selectById(advertiseId);
        if (po == null) {
            throw new BaseException("没有广告id");
        }
        //存储文件
        List<String> previews = new ArrayList<>();

        for (MultipartFile multipartFile : file) {
            //获取minio中的文件名称
            String uploadNewImage = minioUtils.upload(multipartFile);
            //上传文件
            String advertiseImageId = UUID.randomUUID().toString();
            int result = adminAdvertiseMapper.inserAdvertisImage(advertiseImageId, uploadNewImage, advertiseId);
            if (result != 1) {
                throw new BaseException("上传文件失败");
            }
        }
        System.out.println("广告图片返回全部");
        //在查询出数据库已经存在的图片
        List<String> images = adminAdvertiseMapper.selectAllMedia(advertiseId);
        for (String image : images) {
            if (!StringUtils.isBlank(image) && image != "") {
                previews.add(minioUtils.preview(image));
            }
        }

        return previews;
    }


    //分页查询
    @Override
    public PageResult queryByPage(Integer index, Integer pages) {
        PageHelper.startPage(index, pages);
        Page<AdminAdvertisePo> page = adminAdvertiseMapper.selectByPage();
        List<AdminAdvertiseVo> collect = reducedCodeUtils.AdminByAllAdvertise(page.getResult());
// 创建新的 Page 对象并设置相关属性
        Page<AdminAdvertiseVo> newPage = new Page<>(page.getPageNum(), page.getPageSize());
        newPage.setTotal(page.getTotal());
        newPage.addAll(collect);
        return new PageResult(newPage.getTotal(), newPage.getResult());
    }


    //查询所有广告草稿
    @Override
    public List<AdminAdvertiseVo> queryAllDraft() {
        List<AdminAdvertisePo> pos = adminAdvertiseMapper.selectAllDraft();
        List<AdminAdvertiseVo> vos = reducedCodeUtils.AdminByAllAdvertise(pos);
        return vos;
    }


    //添加广告草稿
    @Override
    public boolean addAdvertiseDraft(AdminAdvertiseDto adminAdvertiseDto) {
//判断该广告是否已存在
        AdminAdvertisePo po = adminAdvertiseMapper.selectById(adminAdvertiseDto.getAdvertiseId());
        if (po != null) {
            throw new BaseException("该广告id已存在，请重新添加");
        }
        adminAdvertiseDto.setStatus(AdminAdvertiseStatusConstant.UN_PUBLISHED);
        adminAdvertiseDto.setAddStatus(AdminAdvertiseStatusConstant.DRAFT_STATUS);
        adminAdvertiseDto.setShelvesStatus(AdminAdvertiseStatusConstant.UN_SHELVES);
        int result = adminAdvertiseMapper.insertAdvertiseDraft(adminAdvertiseDto);
        ThrowUtils.throwIf(result != 1, "后台添加广告草稿失败");
        return true;
    }


    //修改草稿
    @Override
    public boolean editAdvertiseDraft(AdminAdvertiseDto adminAdvertiseDto) {
        adminAdvertiseDto.setAddStatus(AdminAdvertiseStatusConstant.ADD_STATUS);
        int result = adminAdvertiseMapper.updateAdvertiseDraft(adminAdvertiseDto);
        ThrowUtils.throwIf(result != 1, "后台修改广告草稿失败");
        return true;
    }


    //更改上架状态
    @Override
    public boolean shelvesStatus(Integer shelvesStatus) {
        if (shelvesStatus != AdminAdvertiseStatusConstant.SHELVES && shelvesStatus != AdminAdvertiseStatusConstant.UN_SHELVES) {
            throw new BaseException("请求参数错误");
        }
        int result = adminAdvertiseMapper.shelvesStatus(shelvesStatus);
        ThrowUtils.throwIf(result < 0, "后台上架广告失败");
        return true;
    }


}
