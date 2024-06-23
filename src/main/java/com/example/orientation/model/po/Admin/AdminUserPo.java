package com.example.orientation.model.po.Admin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserPo implements Serializable {
    private String userId;
    private String username;
    private String password;
    private String teacherNumber;
    private Integer status;
    private String school;
    private String phone;
    private char sex;
    //数据库中头像链接
    private String image;
}
