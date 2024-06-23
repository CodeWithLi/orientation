package com.example.orientation.model.po.MobileUser;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileRegisterPo {
    private String identityCard;
    private String examineeNumber;
    private String phone;
    private Integer status;
    private String image;
    private String username;
    private char sex;
}
