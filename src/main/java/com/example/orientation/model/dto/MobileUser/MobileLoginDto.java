package com.example.orientation.model.dto.MobileUser;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileLoginDto implements Serializable {
    private String studentNumber; //学号
    private String phone;
    private String password;
    private Integer loginType;
    private String sms;
    private String purpose;
    private String account;
}
