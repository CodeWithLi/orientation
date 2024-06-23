package com.example.orientation.mapper;


import com.example.orientation.model.dto.Admin.AdminUserDto;
import com.example.orientation.model.po.Admin.AdminUserPo;


import java.util.List;


/**
 * 后台用户
 */
public interface AdminUserMapper {


    //判断是否有该用户
    AdminUserPo exitUser(String userId);


    //根据id查询用户
    AdminUserPo selectUserById(String userId);


    //添加管理员
    int insertAdmin(AdminUserDto adminUserDto);


    //上传头像
    int updateToImage(String uploadImage,String userId);


    //展示所有管理员
    List<AdminUserPo> selectAllAdmin();


    //删除管理员
    int deleteAdmin(String teacherNumber);


    //更改管理员信息
    int updateAdmin(AdminUserDto adminUserDto);


    //上传头像时没该管理员的话创建新管理员
    int insertNewAdmin(String userId, String uploadNewImage,int status);
}
