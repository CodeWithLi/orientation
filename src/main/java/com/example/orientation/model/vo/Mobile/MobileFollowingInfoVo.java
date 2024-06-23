package com.example.orientation.model.vo.Mobile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor


/**
 * 展示博主的个人信息
 */
public class MobileFollowingInfoVo {
    public String followingId;
    public String username;
    public String sex;
    public String phone;
    public String previewImage;
    public String introduction;//自我介绍
}
