package com.example.orientation.model.dto.MobileUser;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileRegisterDto {
    // 手机号去，验证码，身份证，新密码，考生号去注册
    private String identityCard;
    private String examineeNumber;
    private String phone;
    private String sms;
    private String password;
    private Integer status;
    private String purpose;
}
