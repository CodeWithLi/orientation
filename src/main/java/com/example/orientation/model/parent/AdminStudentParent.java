package com.example.orientation.model.parent;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStudentParent implements Serializable {
    public String studentId;
    public String identityCard;//身份证
    public String studentNumber;//学号
    public String password;
    public Integer status;
    public String username;
    public String sex;
    public String phone;
    public String examineeNumber;//考生号
    public String dormitoryNumber;//宿舍号
    public String image;
    public String school;
    public String college;
    public String major;
    public String studentClass;
    public String instructor;//辅导员
    public String parentsName;
    public String parentsPhone;
    public String graduationSchool;//毕业学校
    public String origin;//籍贯
    public String address;//地址
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date bornDate;
    public String introduction;//自我介绍
    public String expectation;//期望
    public String nation;
    public String selfAssessment;//自我评价
    public String target;
    public String specialty;//特长


}
