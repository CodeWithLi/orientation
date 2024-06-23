package com.example.orientation.model.po.Mobile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileFollowPo {
    private String followId;
    //关注者id
    private String followerId;
    //博主id
    private String followingId;
    //关注状态
    private Integer status;
}
