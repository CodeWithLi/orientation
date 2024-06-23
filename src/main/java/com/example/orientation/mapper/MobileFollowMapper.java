package com.example.orientation.mapper;


import com.example.orientation.model.dto.Mobile.MobileFollowDto;
import com.example.orientation.model.po.Mobile.FollowInfoPo;
import com.example.orientation.model.po.Mobile.MobileFollowPo;
import com.example.orientation.model.po.Mobile.MobileFollowingInfoPo;


import java.util.List;


public interface MobileFollowMapper {


    //查询用户是否存在
    int exitByUser(String account);


    //根据手机号查询出博主个人信息
    MobileFollowingInfoPo getFollowInfo(String phone);


    //查询关注表里是否存在关注关系
    MobileFollowPo exitByFollow(String followerId, String followingId);


    //更改关注状态
    int updateFollowStatus(MobileFollowDto mobileFollowDto);


    //添加关注状态
    int insertFollow(MobileFollowDto mobileFollowDto);


    //根据粉丝id查询出所有关注的博主
    List<MobileFollowPo> focusAllFollowings(String followerId);


    //根据博主id查询博主的个人信息
    FollowInfoPo followingInfoById(String followingId);


    //根据博主id查询出所有粉丝
    List<MobileFollowPo> focusAllFollowers(String followingId);

}
