package com.example.orientation.model.vo.Mobile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 返回个人信息（不包含id），用于嵌套
 */
public class FollowInfoVo {
    public String username;
    public String sex;
    public String phone;
    public String previewImage;
    public String introduction;//自我介绍
}
