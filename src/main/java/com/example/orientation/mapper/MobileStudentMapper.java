package com.example.orientation.mapper;


import com.example.orientation.model.dto.FaceIdentify.CreatePersonDto;
import com.example.orientation.model.dto.MobileUser.MobileRegisterDto;
import com.example.orientation.model.po.MobileUser.MobileLoginPo;
import com.example.orientation.model.po.MobileUser.MobileRegisterPo;


public interface MobileStudentMapper {


    //根据手机号码查询学生信息
    MobileLoginPo loginByPhone(String phone);


    //根据学号查询学生信息
    MobileLoginPo loginByStudentNumber(String studentNumber);


    //根据身份证查询学生信息
    MobileRegisterPo registerByIdentityCard(String identityCard);


    //用户注册成功
    int SuccessByStudentRegister(MobileRegisterDto mobileRegisterDto);


    //修改密码
    int updatePass(String password, String identityCard);


    //上传头像
    int registerUploadImage(String uploadImage, String identityCard,int status);


    //存储personId
    int updatePersonId(String identityCard, String personId);


    //存储人员到数据库
    int insertPersonToIdentify(CreatePersonDto dto);
//
// //根据身份证修改用户密码
// int registerSetPass(String identityCard, String password);
}
