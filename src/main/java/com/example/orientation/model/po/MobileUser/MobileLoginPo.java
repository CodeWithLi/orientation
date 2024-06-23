package com.example.orientation.model.po.MobileUser;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileLoginPo {
    private String studentId;
    private String username;
    private String studentNumber;
    private String phone;
    private String password;
    private Integer status;
}
