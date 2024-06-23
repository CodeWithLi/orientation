package com.example.orientation.model.dto.Admin;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageDto implements Serializable {
    private Integer index;
    private Integer pages;
// private String account;
// private String identityCard;//身份证
// private String studentNumber;//学号
// private Integer status;
// private String username;
// private char sex;
// private String phone;
// private String examineeNumber;//考生号
// private String dormitoryNumber;//宿舍号
// private String school;
// private String college;
// private String major;
// private String studentClass;
// private String instructor;//辅导员
// private String parentsName;
// private String parentsPhone;
// private String graduationSchool;//毕业学校
// private String origin;//籍贯
// private String address;//地址
}
