package com.example.orientation.model.vo.Mobile;


import com.example.orientation.model.vo.Mobile.FollowInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor


/**
 * 用户的粉丝列表
 */
public class FocusToFollowers {
    private String followId;
    //关注者id
    private String followerId;
    //博主id
    private String followingId;


    //每个粉丝的个人信息
    private FollowInfoVo FollowerInfo;
}
