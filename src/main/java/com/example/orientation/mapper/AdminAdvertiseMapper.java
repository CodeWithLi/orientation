package com.example.orientation.mapper;

import com.example.orientation.model.dto.Admin.AdminAdvertiseDto;
import com.example.orientation.model.po.Admin.AdminAdvertisePo;
import com.github.pagehelper.Page;

import java.util.List;

public interface AdminAdvertiseMapper {

    //查询所有广告信息
    List<AdminAdvertisePo> selectAll();

    //查询某条广告信息
    AdminAdvertisePo selectById(String id);

    //更新广告点击次数和总盈利
    int UpdateClickAndProfit(String id,Long clickNumber,Long totalProfit);

    //更新所有广告的点击次数和总盈利
    int UpdateAllClickAndProfit();

    //更新广告状态
    int updateAdvertiseStatus(String id, Long status);

    //根据id删除广告
    int deleteAdvertiseById(String id);

    //添加广告
    int insertAdvertise(AdminAdvertiseDto adminAdvertiseDto);

    //修改广告
    int updateAdvertise(AdminAdvertiseDto adminAdvertiseDto);

    //分页查询
    Page<AdminAdvertisePo> selectByPage();

    //查询所有广告草稿
    List<AdminAdvertisePo> selectAllDraft();

    //添加广告草稿
    int insertAdvertiseDraft(AdminAdvertiseDto adminAdvertiseDto);

    //添加广告头像
    int updateToImage(String uploadImage, String advertiseId);

    //修改草稿内容并提交
    int updateAdvertiseDraft(AdminAdvertiseDto adminAdvertiseDto);

    //更改已发布的广告上架状态
    int shelvesStatus(Integer shelvesStatus);

    //插入图片或视频
    int inserAdvertisImage(String advertiseImageId, String uploadNewImage, String advertiseId);

    //根据id查询出所有图片
    List<String> selectAllMedia(String advertiseId);

    //根据id查询是否有数据
    List<Integer> exitMedia(String advertiseId);
}
