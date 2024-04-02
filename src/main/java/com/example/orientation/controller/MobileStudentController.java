package com.example.orientation.controller;

import com.example.orientation.common.Result;
import com.example.orientation.common.ResultByLogin;
import com.example.orientation.constant.JwtClaimsConstant;
import com.example.orientation.constant.RedisKeyPrefixConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.model.dto.MobileUser.MobileEditDto;
import com.example.orientation.model.dto.MobileUser.MobileLoginDto;
import com.example.orientation.model.dto.MobileUser.MobileRegisterDto;
import com.example.orientation.model.vo.MobileUser.MobileLoginVo;
import com.example.orientation.model.vo.MobileUser.MobileRegisterVo;
import com.example.orientation.properties.JwtProperties;
import com.example.orientation.service.MobileStudentService;
import com.example.orientation.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 移动端学生信息
 */
@RestController
@RequestMapping("/mobile/student")
@Slf4j
public class MobileStudentController {

    @Autowired
    private MobileStudentService mobileStudentService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtProperties jwtProperties;

    //学号或手机号码加密码登录

    /**
     * 学号或手机号码加密码登录
     *
     * @param mobileLoginDto
     * @return
     */
    @PostMapping("/login/password")
    public ResultByLogin loginByPassword(@RequestBody MobileLoginDto mobileLoginDto) {
        if (mobileLoginDto == null) {
            throw new BaseException("请求数据为空");
        }
        if (StringUtils.isAnyBlank(mobileLoginDto.getAccount(), mobileLoginDto.getPassword())) {
            throw new BaseException("请求数据错误");
        }
        MobileLoginVo vo = mobileStudentService.loginByPassword(mobileLoginDto);
        ThrowUtils.throwIf(vo == null, "登录失败");
        log.info("移动端学生登录成功");
        String token = null;
        //判断是否存在token
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(RedisKeyPrefixConstant.Token + vo.getAccount()))) {
            token = stringRedisTemplate.opsForValue().get(RedisKeyPrefixConstant.Token + vo.getAccount());
        } else {
            //不存在token，创建token
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.User_Account, vo.getAccount());
            claims.put(JwtClaimsConstant.User_Password, vo.getPassword());
            token = JwtUtils.createJwt(jwtProperties.getOrSecretKey(), jwtProperties.getOrTtl(), claims);
            stringRedisTemplate.opsForValue().set(RedisKeyPrefixConstant.Token + vo.getAccount(), token, 30, TimeUnit.DAYS);
        }
        return ResultByLogin.success("登录成功", token);
    }

    //发送手机验证码

    /**
     * 发送手机验证码
     *
     * @param mobileEditDto
     * @return
     */
    @PostMapping("/sendSmsCode")
    public Result sendSmsCode(@RequestBody MobileEditDto mobileEditDto) {
        if (mobileEditDto==null){
            return Result.error("请求参数错误");
        }
        String phone= mobileEditDto.getPhone();
        String purpose= mobileEditDto.getPurpose();
        ThrowUtils.throwIf(phone == null, "请求参数错误");
        ThrowUtils.throwIf((!"login".equals(purpose))&&(!"register".equals(purpose))&&(!"edit".equals(purpose)), "请求目的错误");
        //判断手机号码是否符合格式
        ThrowUtils.throwIf(!phone.matches("^1[3456789]\\d{9}$"), "手机号码格式错误");
        String smsCode = mobileStudentService.sendSmsCode(phone, purpose);
        ThrowUtils.throwIf(smsCode == null, "验证码发送失败");
        return Result.success(smsCode);
    }

    //手机号码加验证码登录

    /**
     * 手机号码加验证码登录
     *
     * @param mobileLoginDto
     * @return
     */
    @PostMapping("/login/sms")
    public ResultByLogin loginBySms(@RequestBody MobileLoginDto mobileLoginDto) {
        if (mobileLoginDto == null) {
            throw new BaseException("请求数据为空");
        }
        String smsCode = mobileLoginDto.getSms();
        if (StringUtils.isAnyBlank(mobileLoginDto.getPhone(), smsCode)) {
            throw new BaseException("请求账号或验证码为空");
        }
        //登录对比缓存的验证码
        mobileLoginDto.setPurpose("login");
        MobileLoginVo vo = mobileStudentService.loginBySms(mobileLoginDto);
        log.info("移动端学生登录成功");
        String token = null;
        //判断是否存在token
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(RedisKeyPrefixConstant.Token + vo.getAccount()))) {
            token = stringRedisTemplate.opsForValue().get(RedisKeyPrefixConstant.Token + vo.getAccount());
        } else {
            //不存在token，创建token
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.User_Account, vo.getAccount());
            claims.put(JwtClaimsConstant.User_Password, vo.getPassword());
            token = JwtUtils.createJwt(jwtProperties.getOrSecretKey(), jwtProperties.getOrTtl(), claims);
            stringRedisTemplate.opsForValue().set(RedisKeyPrefixConstant.Token + vo.getAccount(), token, 30, TimeUnit.DAYS);
        }
        return ResultByLogin.success("登录成功", token);
    }

    /**
     * 学生注册
     *
     * @param mobileRegisterDto
     * @return
     */
//    @PostMapping("/register")
    public Result register(@RequestBody MobileRegisterDto mobileRegisterDto) {
        if (mobileRegisterDto == null) {
            throw new BaseException("注册信息为空");
        }
        String identityCard = mobileRegisterDto.getIdentityCard();
        String examineeNumber = mobileRegisterDto.getExamineeNumber();
        String phone = mobileRegisterDto.getPhone();
        String smsCode = mobileRegisterDto.getSms();
        if (StringUtils.isAnyBlank(identityCard, examineeNumber, phone, smsCode)) {
            throw new BaseException("注册信息错误");
        }
        //注册缓存的验证码
        mobileRegisterDto.setPurpose("register");
        boolean result = mobileStudentService.register(mobileRegisterDto);
        ThrowUtils.throwIf(!result, "注册失败");
        return Result.success("注册成功");
    }

    //忘记密码 手机号 身份证 验证码 重置密码

    /**
     * 忘记密码
     * @param mobileEditDto
     * @return
     */
    @PutMapping("/editPass")
    public Result editPass(@RequestBody MobileEditDto mobileEditDto) {
        if (mobileEditDto == null) {
            throw new BaseException("修改信息为空");
        }
        String phone = mobileEditDto.getPhone();
        String password = mobileEditDto.getPassword();
        String identityCard = mobileEditDto.getIdentityCard();
        if (StringUtils.isAnyBlank(identityCard,phone,password)){
            throw new BaseException("修改信息错误");
        }
        if (!phone.matches("^1[3456789]\\d{9}$")){
            throw new BaseException("手机号码格式错误");
        }
        //简单的身份证校验
        if (!identityCard.matches("^\\d{17}[x0-9]$")){
            throw new BaseException("身份证格式错误");
        }
        mobileEditDto.setPurpose("edit");
        boolean result=mobileStudentService.editPass(mobileEditDto);
        ThrowUtils.throwIf(!result,"修改失败");
        return Result.success("修改成功");
    }

    //注册四步 身份证加考生号判断 手机号加验证码 设置密码 上传头像

    /**
     * 注册时判断用户是否存在
     * @param mobileRegisterDto
     * @return
     */
    @PostMapping("/register/judgment")
    public Result judgmentStudent(@RequestBody MobileRegisterDto mobileRegisterDto){
        String identityCard= mobileRegisterDto.getIdentityCard();
        String examineeNumber = mobileRegisterDto.getExamineeNumber();
        if (StringUtils.isAnyBlank(identityCard,examineeNumber)){
            return Result.error("请求参数错误");
        }
        boolean result=mobileStudentService.registerByJudgment(identityCard,examineeNumber);
        ThrowUtils.throwIf(!result,"该用户不存在，注册操作取消");
        return Result.success("该用户存在,可以进行注册时的下一步操作");
    }

    //手机号加验证码

    /**
     * 注册时判断手机号码和验证码是否正确
     * @param mobileRegisterDto
     * @return
     */
    @PostMapping("/register/validation")
    public Result validationStudentPhone(@RequestBody MobileRegisterDto mobileRegisterDto){
        String identityCard = mobileRegisterDto.getIdentityCard();
        String phone = mobileRegisterDto.getPhone();
        String sms = mobileRegisterDto.getSms();
        if (StringUtils.isAnyBlank(identityCard,phone,sms)){
            return Result.error("请求参数错误");
        }
        if (!phone.matches("^1[3456789]\\d{9}$")){
            throw new BaseException("手机号码格式错误");
        }
        //简单的身份证校验
        if (!identityCard.matches("^\\d{17}[x0-9]$")){
            throw new BaseException("身份证格式错误");
        }
        mobileRegisterDto.setPurpose("register");
        boolean result=mobileStudentService.registerByValidation(mobileRegisterDto);
        ThrowUtils.throwIf(!result,"验证失败");
        return Result.success("验证成功");
    }

    //设置密码

    /**
     * 注册时设置用户密码
     * @param mobileRegisterDto
     * @return
     */
    @PostMapping("/register/set")
    public Result setPassStudent(@RequestBody MobileRegisterDto mobileRegisterDto){
        String identityCard = mobileRegisterDto.getIdentityCard();
        String password = mobileRegisterDto.getPassword();
        if (StringUtils.isAnyBlank(identityCard,password)){
           throw new BaseException("请求参数错误");
        }
        boolean result= mobileStudentService.registerSetPass(identityCard,password);
        ThrowUtils.throwIf(!result,"设置密码失败");
        return Result.success("设置密码成功");
    }

    //上传头像

    /**
     * 注册时上传用户头像
     * @param identityCard
     * @param file
     * @return
     */
    @PostMapping("/register/upload")
    public Result uploadImage(@RequestParam String identityCard,@RequestParam MultipartFile file){
        // TODO: 2024/3/13 后期需要设计人员库，存图片 用于人脸识别
        if (file==null){
            return Result.error("文件为空");
        }
//        if (!file.getContentType().startsWith("image")) {
//            throw new BaseException("请正确上传图片");
//        }
//        long maxImageSizeBytes = 10 * 1024 * 1024;
//        if ((file.getSize() > maxImageSizeBytes)){
//            throw new BaseException("图片大小最大为10MB");
//        }
        String image=mobileStudentService.profileImage(identityCard,file);
        ThrowUtils.throwIf(image == null,"注册失败,头像回显链接失败");
        log.info("返回的数据：{}",Result.success(image));
        MobileRegisterVo vo=new MobileRegisterVo("注册成功,头像回显链接成功",image);
        return Result.success(vo);
    }

    //人脸识别

}
