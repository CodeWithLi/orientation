package com.example.orientation.model.dto.MobileUser;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileEditDto {
    private String identityCard;
    private String phone;
    private String sms;
    private String password;
    private String purpose;
}
