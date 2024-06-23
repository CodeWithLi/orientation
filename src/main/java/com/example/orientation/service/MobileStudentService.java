package com.example.orientation.service;


import com.example.orientation.model.dto.MobileUser.MobileEditDto;
import com.example.orientation.model.dto.MobileUser.MobileLoginDto;
import com.example.orientation.model.dto.MobileUser.MobileRegisterDto;
import com.example.orientation.model.vo.MobileUser.MobileLoginVo;
import org.springframework.web.multipart.MultipartFile;


public interface MobileStudentService {


    //移动端学生登录
    MobileLoginVo loginByPassword(MobileLoginDto mobileLoginDto);


    //发送验证码
    String sendSmsCode(String account,String purpose);




    //移动端学生登录
    MobileLoginVo loginBySms(MobileLoginDto mobileLoginDto);


    //移动端学生注册
    boolean register(MobileRegisterDto mobileRegisterDto);


    //修改密码
    boolean editPass(MobileEditDto mobileEditDto);


    //注册时判断用户是否存在
    boolean registerByJudgment(String identityCard, String examineeNumber);


    //注册时判断手机号码和验证码是否正确
    boolean registerByValidation(MobileRegisterDto mobileRegisterDto);


    //注册时设置用户密码
    boolean registerSetPass(String identityCard, String password);


    //注册时上传用户头像
    String profileImage(String identityCard, MultipartFile file);
}
