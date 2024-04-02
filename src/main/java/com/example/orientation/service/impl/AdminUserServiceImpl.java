package com.example.orientation.service.impl;

import com.example.orientation.constant.AdminUserStatusConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.mapper.AdminUserMapper;
import com.example.orientation.model.dto.Admin.AdminUserDto;
import com.example.orientation.model.po.Admin.AdminUserPo;
import com.example.orientation.model.vo.Admin.AdminUserVo;
import com.example.orientation.service.AdminUserService;
import com.example.orientation.utils.MinioUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
//@Transactional
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Autowired
    private MinioUtils minioUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //用户登录
    @Override
    public Integer userLogin(AdminUserDto adminUserDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info("登录");
        //判断数据库是否有该用户
        AdminUserPo adminUserPo = adminUserMapper.exitUser(adminUserDto.getTeacherNumber());
        if (adminUserPo==null){
            log.info("不存在用户");
            throw new BaseException("不存在该用户");
        }
        //判断是否为可登录状态
        if (Objects.equals(adminUserPo.getStatus(), AdminUserStatusConstant.STOP)){
            log.info("登录状态：{}",adminUserPo.getStatus());
            throw new BaseException("账号已被删除，请联系管理员");
        }
        //存在用户，则加盐密码并且判断密码是否一样
        if (!passwordEncoder.matches(adminUserDto.getPassword(), adminUserPo.getPassword())){
            log.info("密码错误");
            throw new BaseException("密码错误");
        }
        return adminUserPo.getStatus();
    }

    //添加管理员
    @Override
    public boolean addAdmin(AdminUserDto adminUserDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info("添加");
        if (adminUserDto.getTeacherNumber()==null||adminUserDto.getPassword()==null){
            throw new BaseException("账号或密码为空");
        }
        if (adminUserDto.getPassword().length()<8){
            throw new BaseException("密码错误，长度不得小于8");
        }
        String regex="^1[3456789]\\d{9}$";
        if (adminUserDto.getPhone()!=null){
            if (!adminUserDto.getPhone().matches(regex)){
                throw new BaseException("手机号码格式错误");
            }
        }
        adminUserDto.setPassword(passwordEncoder.encode(adminUserDto.getPassword()));
        //登录状态默认为管理员
        adminUserDto.setStatus(AdminUserStatusConstant.ADMIN);
        //判断是否存在该用户
        AdminUserPo adminUserPo = adminUserMapper.selectUserById(adminUserDto.getUserId());
        //数据库中存在头像名称但是不存在教工号
        if (adminUserPo!=null){
            if ("".equals(adminUserPo.getTeacherNumber())||StringUtils.isEmpty(adminUserPo.getTeacherNumber())){
                //往数据库中存储其他信息
                int number = adminUserMapper.updateAdmin(adminUserDto);
                return number==1;
            }
            throw new BaseException("该用户已存在");
        }
        //不存在，添加管理员,进行md5加密
        int number= adminUserMapper.insertAdmin(adminUserDto);
        ThrowUtils.throwIf( number!=1, "添加管理员失败");
        return true;
    }

    //上传头像
    @Override
    public String profileImage(MultipartFile file, String userId) {
        log.info("上传头像service层");
        int result=0;
        String preview=null;
        //先判断数据库是否有该用户，没有则添加用户id和头像链接
        AdminUserPo adminUserPo = adminUserMapper.selectUserById(userId);
        if (adminUserPo==null){
            //无用户，添加新用户id和头像名称
            String uploadNewImage = minioUtil.upload(file);
            result=adminUserMapper.insertNewAdmin(userId,uploadNewImage,AdminUserStatusConstant.STOP);
            preview= minioUtil.preview(uploadNewImage);
        }else if (StringUtils.isEmpty(adminUserPo.getImage())&&"".equals(adminUserPo.getImage())){
            //有用户无头像，添加头像地址
            String uploadNewImage = minioUtil.upload(file);
            result = adminUserMapper.updateToImage(uploadNewImage, userId);
            preview = minioUtil.preview(uploadNewImage );
        }else {
//            有用户头像，删除minio中头像地址
            minioUtil.remove(adminUserPo.getImage());
            //再次上传头像
            String uploadImage = minioUtil.upload(file);
            result= adminUserMapper.updateToImage(uploadImage, userId);
            preview = minioUtil.preview(uploadImage);
        }
        if (result==1&&preview!=null){
            return preview;
        }
        throw new BaseException("上传头像失败");
    }

    //展示所有管理员
    @Override
    public List<AdminUserVo> selectAllAdmin() {
        log.info("展示所有");
        //查询出数据库中所有的信息
        List<AdminUserPo> pos= adminUserMapper.selectAllAdmin();
        //转化为vo
        List<AdminUserVo> collectByVo = pos.stream().map(adminUserPo -> {
            AdminUserVo adminUserVo = new AdminUserVo();
            BeanUtils.copyProperties(adminUserPo,adminUserVo);
            if (!Objects.equals(adminUserPo.getImage(), "")&&!StringUtils.isEmpty(adminUserPo.getImage())){
                //获取minio中的图像链接
                adminUserVo.setPreviewImages(minioUtil.preview(adminUserPo.getImage()));
            }
            return adminUserVo;
        }).collect(Collectors.toList());
        return collectByVo;
    }

    //删除管理员
    @Override
    public boolean deleteAdmin(String teacherNumber) {
        //判断有没有该token，有则删除
        if (stringRedisTemplate.hasKey(teacherNumber)){
            stringRedisTemplate.delete(teacherNumber);
        }
        int result = adminUserMapper.deleteAdmin(teacherNumber);
        ThrowUtils.throwIf( result!=1, "删除管理员失败");
        return true;
    }

    //更改管理员信息
    @Override
    public boolean updateAdmin(AdminUserDto adminUserDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!Objects.equals(adminUserDto.getPassword(), "")&&!StringUtils.isEmpty(adminUserDto.getPassword())){
            if (adminUserDto.getPassword().length()<8){
                throw new BaseException("密码长度小于8");
            }
        }
        if (!Objects.equals(adminUserDto.getPhone(), "")&&!StringUtils.isEmpty(adminUserDto.getPhone())){
            if (!adminUserDto.getPhone().matches("^1[3456789]\\d{9}$")){
                throw new BaseException("手机号码格式错误");
            }
        }
        //加密密码
        adminUserDto.setPassword(passwordEncoder.encode(adminUserDto.getPassword()));
        int result= adminUserMapper.updateAdmin(adminUserDto);
        ThrowUtils.throwIf( result!=1, "修改信息失败");
        return true;
    }

}
