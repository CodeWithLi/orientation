package com.example.orientation.controller;

import com.example.orientation.constant.AdminUserStatusConstant;
import com.example.orientation.constant.JwtClaimsConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.model.dto.Admin.AdminUserDto;
import com.example.orientation.model.vo.Admin.AdminUserVo;
import com.example.orientation.properties.JwtProperties;
import com.example.orientation.service.AdminUserService;
import com.example.orientation.utils.JwtUtils;
import com.example.orientation.common.Result;
import com.example.orientation.common.ResultByLogin;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 后台管理员用户
 */
@RestController
@RequestMapping("/admin/user")
@Slf4j
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 管理员登录
     *
     * @param adminUserDto
     * @return
     */
    @PostMapping("/login")
    public ResultByLogin<String> adminLogin(@RequestBody AdminUserDto adminUserDto, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(adminUserDto.getTeacherNumber(), adminUserDto.getPassword())) {
            throw new BaseException("账号或密码为空");
        }
        log.info("登录用户信息：{}", adminUserDto);
        Integer loginStatus = adminUserService.userLogin(adminUserDto);
        log.info("是否登录成功:{}", loginStatus);
        String token = null;
        //判断是否存在token
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(adminUserDto.getTeacherNumber()))) {
            token = stringRedisTemplate.opsForValue().get(adminUserDto.getTeacherNumber());
        } else {
            //不存在token，创建token
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.Admin_account, adminUserDto.getTeacherNumber());
            claims.put(JwtClaimsConstant.Admin_Password, adminUserDto.getPassword());
            token = JwtUtils.createJwt(jwtProperties.getOrSecretKey(), jwtProperties.getOrTtl(), claims);
            stringRedisTemplate.opsForValue().set(adminUserDto.getTeacherNumber(), token, 30, TimeUnit.DAYS);
        }
        String userByStatus = (Objects.equals(loginStatus, AdminUserStatusConstant.SUPER_ADMIN)) ? "超级管理员登录" : "管理员登录";
        return ResultByLogin.successByStatus(userByStatus, token, loginStatus);
    }

    /**
     * 展示所有管理员
     *
     * @return
     */
    @GetMapping("/allAdmin")
    public Result allAdmin() {
        log.info("查询所有管理员");
        List<AdminUserVo> adminUserVos = adminUserService.selectAllAdmin();
        return Result.success(adminUserVos);
    }

    /**
     * 添加管理员
     *
     * @param adminUserDto
     * @return
     */
    @PostMapping("/add")
    public Result<String> addAdmin(@RequestBody AdminUserDto adminUserDto) {
        //判断传来的数据是否为空
        if (adminUserDto == null) {
            return Result.error("请传输数据");
        }
        boolean add = adminUserService.addAdmin(adminUserDto);
        log.info("添加状态：{}", add);
        ThrowUtils.throwIf(!add,"添加失败");
        return Result.success("添加成功");
    }

    /**
     * 上传头像
     *
     * @param file
     * @param userId
     * @return
     */
    @PostMapping("/uploadImage")
    public Result uploadImage(@RequestParam MultipartFile file, @RequestParam String userId) {
        if (file==null){
            return Result.error("文件为空");
        }
        if (file.getContentType() == null && !file.getContentType().startsWith("image")) {
            return Result.error("请正确上传图片");
        }
        String image = adminUserService.profileImage(file, userId);
        ThrowUtils.throwIf(image == null,"头像回显链接失败");
        log.info("返回的数据：{}",Result.success(image));
        return Result.success(image);
    }

    /**
     * 删除管理员
     *
     * @param teacherNumber
     * @return
     */
    @DeleteMapping("/remove/{teacherNumber}")
    public Result deleteAdmin(@PathVariable String teacherNumber) {
        if (teacherNumber == null) {
            throw new BaseException("无教工号，请正确传输");
        }
        boolean result = adminUserService.deleteAdmin(teacherNumber);
        ThrowUtils.throwIf(!result, "删除失败");
        return Result.success("删除成功");
    }

    /**
     * 更改管理员信息
     * @param adminUserDto
     * @return
     */
    @PutMapping("/edit")
    public Result editAdmin(@RequestBody AdminUserDto adminUserDto) {
        if (adminUserDto == null) {
            throw new BaseException("请求数据不存在");
        }
        boolean result = adminUserService.updateAdmin(adminUserDto);
        ThrowUtils.throwIf(!result, "更新失败");
        return Result.success("更新成功");
    }
}
