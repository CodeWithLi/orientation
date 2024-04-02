package com.example.orientation.model.dto.Admin;

import cn.hutool.core.annotation.Alias;
import com.example.orientation.model.parent.AdminStudentParent;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStudentDto  implements Serializable {
    private String studentId;

    private String studentNumber;//学号
    private String password;
    private Integer status;

    private String dormitoryNumber;//宿舍号
    private String image;

    private String studentClass;

    private String address;//地址
    private Integer isDelete;
    @Alias("身份证")
    private String identityCard;//身份证
    @Alias("姓名")
    private String username;
    @Alias("性别")
    private String sex;
    @Alias("手机号")
    private String phone;
    @Alias("考生号")
    private String examineeNumber;//考生号
    @Alias("学校")
    private String school;
    @Alias("学院")
    private String college;
    @Alias("专业")
    private String major;
    @Alias("辅导员")
    private String instructor;//辅导员
    @Alias("父母姓名")
    private String parentsName;
    @Alias("父母电话")
    private String parentsPhone;
    @Alias("毕业学校")
    private String graduationSchool;//毕业学校
    @Alias("籍贯")
    private String origin;//籍贯
    @Alias("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bornDate;
    private String introduction;//自我介绍
    private String expectation;//期望
    @Alias("民族")
    private String nation;
    private String selfAssessment;//自我评价
    private String target;
    @Alias("学生头像")
    private String excelImage;
    private String specialty;//特长

}
