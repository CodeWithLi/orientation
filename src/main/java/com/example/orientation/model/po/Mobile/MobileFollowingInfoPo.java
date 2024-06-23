package com.example.orientation.model.po.Mobile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor


//展示博主的个人信息
public class MobileFollowingInfoPo {
    public String studentId;
    public String username;
    public String sex;
    public String phone;
    public String image;
    public String introduction;//自我介绍
}
