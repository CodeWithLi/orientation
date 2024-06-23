package com.example.orientation.service;




import com.example.orientation.model.dto.Mobile.MobileFollowDto;
import com.example.orientation.model.vo.Mobile.FocusToFollowers;
import com.example.orientation.model.vo.Mobile.FocusToFollowings;
import com.example.orientation.model.vo.Mobile.MobileFollowingInfoVo;


import java.util.List;


public interface MobileFollowService {


    //对博主的关注状态
    boolean followUser(MobileFollowDto mobileFollowDto);


    //根据手机号查询出博主个人信息
    MobileFollowingInfoVo getFollowInformation(String phone);


    //获取用户关注的博主列表
    List<FocusToFollowings> focusFollowings(String followerId);


    //获取博主的粉丝列表
    List<FocusToFollowers> focusFollowers(String followingId);
}
