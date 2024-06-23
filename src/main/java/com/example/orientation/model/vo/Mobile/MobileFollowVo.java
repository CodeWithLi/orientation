package com.example.orientation.model.vo.Mobile;


import com.example.orientation.model.vo.Mobile.MobileFollowingInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileFollowVo {
    private String followId;
    //关注者id
    private String followerId;
    //博主id
    private String followingId;
    // //关注状态
// private Integer status;
//每个博主的个人信息
    private MobileFollowingInfoVo FollowingInfo;
}
