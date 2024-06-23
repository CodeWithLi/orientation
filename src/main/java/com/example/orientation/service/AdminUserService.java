package com.example.orientation.service;


import com.example.orientation.model.dto.Admin.AdminUserDto;
import com.example.orientation.model.vo.Admin.AdminUserVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


@CacheConfig(cacheNames = "AdminService")
public interface AdminUserService {


    //登录用户
    Integer userLogin(AdminUserDto adminUserDto);


    @Cacheable(key = "'user'")
        //查询所有管理员
    List<AdminUserVo> selectAllAdmin();


    @CacheEvict(key = "'user'",allEntries = true)
        //添加管理员
    boolean addAdmin(AdminUserDto adminUserDto);


    //上传头像
    @CacheEvict(key = "'user'",allEntries = true)
    String profileImage(MultipartFile file, String userId);


    @CacheEvict(key = "user", allEntries = true)
        //删除管理员
    boolean deleteAdmin(String teacherNumber);


    @CacheEvict( key = "user",allEntries = true)
        //更改管理员信息
    boolean updateAdmin(AdminUserDto adminUserDto);
}
