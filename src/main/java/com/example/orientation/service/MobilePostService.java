package com.example.orientation.service;

import com.example.orientation.model.dto.Mobile.MobilePostDto;
import com.example.orientation.model.vo.Mobile.FollowAndPostVo;
import com.example.orientation.model.vo.Mobile.MobilePostVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MobilePostService {

    //发布帖子
    boolean addPost(MobilePostDto mobilePostDto);

    //发布帖子的选择同时上传图片或视频
    boolean uploadMedia(String postId, MultipartFile file);

    //删除帖子
    boolean deletePost(String postId);

    //获取用户关注的博主的帖子列表
    List<FollowAndPostVo> getAllPost(String followerId);

    //获取用户关注的某个博主的帖子列表
    FollowAndPostVo getFollowingPost(String followerId,String FollowingId);

    //获取用户关注的某个博主的某个帖子详细信息
    MobilePostVo getFollowingPostInfo(String followerId, String followingId, String postId);
}
