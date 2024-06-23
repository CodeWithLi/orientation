package com.example.orientation.service;


import com.example.orientation.common.PageResult;
import com.example.orientation.model.dto.Admin.AdminAdvertiseDto;
import com.example.orientation.model.dto.Admin.EditDto;
import com.example.orientation.model.vo.Admin.AdminAdvertiseVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


@CacheConfig(cacheNames = "AdminService")
public interface AdminAdvertiseService {


    @Cacheable(key = "'advertise'")
//查询所有广告
    List<AdminAdvertiseVo> queryAll();


    @CacheEvict(key = "'advertise'",allEntries = true)
//更新点击次数及总盈利
    boolean editClickAndProfit(String id);


    @CacheEvict(key = "'advertise'",allEntries = true)
//修改广告状态
    boolean editAdvertiseStatus(EditDto editDto);


    @CacheEvict(key = "'advertise'",allEntries = true)
//删除单个广告
    boolean removeAdvertise(String id);


    @CacheEvict(key = "'advertise'",allEntries = true)
//添加广告
    boolean addAdvertise(AdminAdvertiseDto adminAdvertiseDto);


    @CacheEvict(key = "'advertise'",allEntries = true)
//修改广告信息
    boolean editAdvertise(AdminAdvertiseDto adminAdvertiseDto);


    //添加广告图片或者视频
    @CacheEvict(key = "'advertise'",allEntries = true)
    List<String> profileImage(MultipartFile[] file, String userId);


    // @Cacheable(key = "'advertise'")
//分页查询
    PageResult queryByPage(Integer index, Integer pages);


    //查询所有草稿内容
    List<AdminAdvertiseVo> queryAllDraft();


    //添加广告草稿
    boolean addAdvertiseDraft(AdminAdvertiseDto adminAdvertiseDto);


    //修改草稿内容并提交
    boolean editAdvertiseDraft(AdminAdvertiseDto adminAdvertiseDto);


    //更改上架状态
    boolean shelvesStatus(Integer shelvesStatus);
}
