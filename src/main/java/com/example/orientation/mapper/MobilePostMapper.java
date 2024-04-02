package com.example.orientation.mapper;

import com.example.orientation.model.dto.Mobile.MobilePostDto;
import com.example.orientation.model.po.Mobile.MobilePostPo;

import java.util.List;

public interface MobilePostMapper {

    //查询发布者是否存在
    int exitFollowing(String followingId);

    //添加帖子
    int addPost(MobilePostDto mobilePostDto);

    //查询帖子是否存在
    int exitPost(String postId);

    //上传媒体
    int uploadMedia(String postId, String media);

    //删除帖子
    int deletePost(String postId);

    //查询出博主对应所有的帖子
    List<MobilePostPo> allPostByFollowing(String followingId);

    //根据博主id和帖子id查询出对应的帖子
    MobilePostPo getPostInfo(String followerId, String postId);
}
