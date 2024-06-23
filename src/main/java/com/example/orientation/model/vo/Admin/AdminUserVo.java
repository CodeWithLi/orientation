package com.example.orientation.model.vo.Admin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserVo implements Serializable {
    private String userId;
    private String teacherNumber;
    private String password;
    private String username;
    private String school;
    private String phone;
    private Integer status;
    private char sex;
    //minio中头像预览地址
    private String previewImages;
}
