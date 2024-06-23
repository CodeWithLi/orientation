package com.example.orientation.model.vo.MobileUser;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileLoginVo implements Serializable {
    private String studentId;
    private String username;
    private String studentNumber;
    private String password;
    private String phone;
    private String account;
}
