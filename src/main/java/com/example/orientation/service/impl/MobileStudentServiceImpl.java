package com.example.orientation.service.impl;


import com.apistd.uni.UniResponse;
import com.example.orientation.constant.AdminUserStatusConstant;
import com.example.orientation.constant.RedisKeyPrefixConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.mapper.MobileStudentMapper;
import com.example.orientation.model.dto.FaceIdentify.CreatePersonDto;
import com.example.orientation.model.dto.MobileUser.MobileEditDto;
import com.example.orientation.model.dto.MobileUser.MobileLoginDto;
import com.example.orientation.model.dto.MobileUser.MobileRegisterDto;
import com.example.orientation.model.po.MobileUser.MobileLoginPo;
import com.example.orientation.model.po.MobileUser.MobileRegisterPo;
import com.example.orientation.model.vo.MobileUser.MobileLoginVo;
import com.example.orientation.properties.FaceIdentifyProperties;
import com.example.orientation.properties.UniSmsProperties;
import com.example.orientation.service.MobileStudentService;
import com.example.orientation.utils.FaceIdentifyUtils;
import com.example.orientation.utils.MinioUtils;
import com.example.orientation.utils.ReducedCodeUtils;
import com.example.orientation.utils.SmsUtils;
import com.tencentcloudapi.iai.v20200303.models.CreatePersonResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class MobileStudentServiceImpl implements MobileStudentService {


    @Resource
    private MobileStudentMapper mobileStudentMapper;


    @Autowired
    private UniSmsProperties uniSmsProperties;


    @Autowired
    private FaceIdentifyUtils faceIdentifyUtils;


    @Autowired
    private FaceIdentifyProperties identifyProperties;


    @Autowired
    private MinioUtils minioUtils;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //根据学号或手机号码加密码
    @Override
    public MobileLoginVo loginByPassword(MobileLoginDto mobileLoginDto) {
        if (mobileLoginDto.getPassword().length() < 8) {
            throw new BaseException("密码错误,长度小于8");
        }
        //加密密码
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String pass=mobileLoginDto.getPassword();
        String account=mobileLoginDto.getAccount();
        //判断登录类型是为手机号码+密码登录 还是 学号登录+密码
        if (account.matches("^1[3456789]\\d{9}$")){
        //查询是否有该用户
            MobileLoginPo mobileLoginPo= mobileStudentMapper.loginByPhone(account);
        //调用简化代码的工具类
            MobileLoginVo loginVo = ReducedCodeUtils.mobileToLoginByPassword(mobileLoginPo,bCryptPasswordEncoder,pass);
        //为手机登录 则设计手机为账号，用于后续token创建
            loginVo.setAccount(loginVo.getPhone());
            return loginVo;
        //学号登陆
        }else if (account.matches("^202206\\d{6}$")){
        //查询是否有该用户
            MobileLoginPo mobileLoginPo= mobileStudentMapper.loginByStudentNumber(account);
            MobileLoginVo loginVo = ReducedCodeUtils.mobileToLoginByPassword(mobileLoginPo,bCryptPasswordEncoder, pass);
            loginVo.setAccount(loginVo.getStudentNumber());
            return loginVo;
        }else {
            throw new BaseException("账号不符合格式");
        }
    }


    //发送手机验证码
    @Override
    public String sendSmsCode(String account,String purpose) {
    //判断是注册还是登录 用于区分 防止同时注册或登录时缓存覆盖
    //先设计返回的验证码位随机数
        Random random=new Random();
        String randomSms = String.valueOf(random.nextInt(1000, 9999));
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(RedisKeyPrefixConstant.KeyPrefix+purpose+account))){
            throw new BaseException("验证码未过期，请勿重复发送");
        }
    //开启验证码功能
        UniResponse uniResponse = SmsUtils.sendSms(uniSmsProperties.getAccessKeyId(), account, uniSmsProperties.getSignature(),
                uniSmsProperties.getTemplateId(), randomSms, uniSmsProperties.getTtl());
        if (uniResponse.status!=200){
            throw new BaseException("验证码发送失败");
        }
        stringRedisTemplate.opsForValue().set(RedisKeyPrefixConstant.KeyPrefix+purpose+account,randomSms,3, TimeUnit.MINUTES);
        return randomSms;
    }




    //根据手机号码加验证码登录
    @Override
    public MobileLoginVo loginBySms(MobileLoginDto mobileLoginDto) {
        //获取缓存中的验证码
        String smsByRedis = stringRedisTemplate.opsForValue().get(RedisKeyPrefixConstant.KeyPrefix + mobileLoginDto.getPurpose() + mobileLoginDto.getPhone());
        ThrowUtils.serviceThrow(!(mobileLoginDto.getSms()!=null&&smsByRedis!=null),"验证码为空，请重新输入");
        //判断与用户传的验证码是否一致
        ThrowUtils.serviceThrow(!smsByRedis.trim().equals(mobileLoginDto.getSms()),"验证码错误，请重新获取");
        //查询是否有该用户
        MobileLoginPo mobileLoginPo= mobileStudentMapper.loginByPhone(mobileLoginDto.getPhone());
        if (mobileLoginPo==null){
            throw new BaseException("用户不存在");
        }
        if (mobileLoginPo.getStatus()== AdminUserStatusConstant.STUDENT_STOP) {
            throw new BaseException("用户未注册");
        }
        MobileLoginVo loginVo=new MobileLoginVo();
        BeanUtils.copyProperties(mobileLoginPo,loginVo);
//为手机登录 则设计手机为账号，用于后续token创建
        loginVo.setAccount(loginVo.getPhone());
        return loginVo;
    }


    //注册
    @Override
    public boolean register(MobileRegisterDto mobileRegisterDto) {
//获取缓存中的验证码
        String smsByRedis = stringRedisTemplate.opsForValue().get(RedisKeyPrefixConstant.KeyPrefix+mobileRegisterDto.getPurpose() + mobileRegisterDto.getPhone());
        ThrowUtils.serviceThrow(!(mobileRegisterDto.getSms()!=null&&smsByRedis!=null),"验证码为空，请重新输入");
//判断与用户传的验证码是否一致
        ThrowUtils.serviceThrow(!smsByRedis.equals(mobileRegisterDto.getSms()),"验证码错误，请重新获取");
        ThrowUtils.serviceThrow(mobileRegisterDto.getPassword().length()<8,"密码小于8位");
//避免同一用户发送多次请求
        synchronized (mobileRegisterDto.getIdentityCard().intern()){
//根据身份证判断数据库是否有该用户信息
            MobileRegisterPo mobileRegisterPo=mobileStudentMapper.registerByIdentityCard(mobileRegisterDto.getIdentityCard());
            ThrowUtils.serviceThrow(mobileRegisterPo==null,"没有该用户信息");
            if (Objects.equals(mobileRegisterPo.getStatus(), AdminUserStatusConstant.STUDENT_SUCCESS)){
                throw new BaseException("该用户已注册");
            }
            if (!mobileRegisterPo.getExamineeNumber().equals(mobileRegisterDto.getExamineeNumber())||
                    !mobileRegisterPo.getPhone().equals(mobileRegisterDto.getPhone())){
                throw new BaseException("学号或手机号码不正确");
            }
//加密密码
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            String pass=bCryptPasswordEncoder.encode(mobileRegisterDto.getPassword());
//插入数据,将后台学生status改为1代表注册成功
            mobileRegisterDto.setPassword(pass);
            mobileRegisterDto.setStatus(AdminUserStatusConstant.STUDENT_SUCCESS);
            int result=mobileStudentMapper.SuccessByStudentRegister(mobileRegisterDto);
            ThrowUtils.throwIf( result!=1, "学生注册失败");
            return true;
        }
    }


    //修改密码
    @Override
    public boolean editPass(MobileEditDto mobileEditDto) {
        if (mobileEditDto.getPassword().length()<8){
            throw new BaseException("密码长度小于8");
        }
        String purpose = mobileEditDto.getPurpose();
        String phone = mobileEditDto.getPhone();
        String identityCard = mobileEditDto.getIdentityCard();
//根据身份证查询是否存在该用户
        MobileRegisterPo po = mobileStudentMapper.registerByIdentityCard(identityCard);
        if (po==null){
            throw new BaseException("该用户不存在");
        }
        if (!po.getPhone().trim().equals(phone)){
            throw new BaseException("手机号码不正确");
        }
        if (po.getStatus()==AdminUserStatusConstant.STUDENT_STOP){
            throw new BaseException("该用户未注册");
        }
//获取缓存中的验证码
        if (!Boolean.TRUE.equals(stringRedisTemplate.hasKey(RedisKeyPrefixConstant.KeyPrefix + purpose + phone))){
            throw new BaseException("请先发送验证码");
        }
        String redisSms = stringRedisTemplate.opsForValue().get(RedisKeyPrefixConstant.KeyPrefix + purpose + phone);
        String sms = mobileEditDto.getSms();
//判断验证码是否正确
        if (!redisSms.trim().equals(sms)){
            throw new BaseException("验证码错误");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String password=bCryptPasswordEncoder.encode(mobileEditDto.getPassword());
        int result=mobileStudentMapper.updatePass(password,identityCard);
        ThrowUtils.throwIf( result!=1, "学生修改密码失败");
        return true;
    }


    //注册时判断用户是否存在
    @Override
    public boolean registerByJudgment(String identityCard, String examineeNumber) {
//简单的身份证校验
        if (!identityCard.matches("^\\d{17}[x0-9]$")){
            throw new BaseException("身份证格式错误");
        }
//根据身份证查收是否存在该用户
        MobileRegisterPo po = mobileStudentMapper.registerByIdentityCard(identityCard);
        ReducedCodeUtils.mobileByRegisterStudent(po);
        if (!examineeNumber.equals(po.getExamineeNumber())){
            throw new BaseException("考生号错误");
        }
        return true;
    }


    //注册时判断手机号码和验证码是否正确
    @Override
    public boolean registerByValidation(MobileRegisterDto mobileRegisterDto) {
        String purpose = mobileRegisterDto.getPurpose();
        String dtoPhone = mobileRegisterDto.getPhone();
//判断用户是否存在
        MobileRegisterPo po = mobileStudentMapper.registerByIdentityCard(mobileRegisterDto.getIdentityCard());
        ReducedCodeUtils.mobileByRegisterStudent(po);
//判断手机号码
        if (!po.getPhone().trim().equals(dtoPhone)){
            throw new BaseException("手机号码不正确");
        }
        if (StringUtils.isBlank(mobileRegisterDto.getSms())){
            throw new BaseException("验证码为空，请重新输入");
        }
//根据用户的手机号码获取缓存中的验证码
        if (!Boolean.TRUE.equals(stringRedisTemplate.hasKey(RedisKeyPrefixConstant.KeyPrefix + purpose + dtoPhone))){
            throw new BaseException("请先发送验证码");
        }
        String smsByRedis =
                stringRedisTemplate.opsForValue().get(RedisKeyPrefixConstant.KeyPrefix+purpose+dtoPhone);
        if (!Objects.equals(smsByRedis, mobileRegisterDto.getSms())){
            throw new BaseException("验证码对比错误，请重新输入");
        }
//验证通过
        return true;
    }


    //注册时设置用户密码
    @Override
    public boolean registerSetPass(String identityCard, String password) {
//简单的身份证校验
        if (!identityCard.matches("^\\d{17}[x0-9]$")){
            throw new BaseException("身份证格式错误");
        }
        if (password.length()<8){
            throw new BaseException("用户密码长度过短");
        }
//判断用户是否存在
        MobileRegisterPo po = mobileStudentMapper.registerByIdentityCard(identityCard);
        ReducedCodeUtils.mobileByRegisterStudent(po);
//加密
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encodePass = bCryptPasswordEncoder.encode(password);
//根据身份证修改用户密码
        int result=mobileStudentMapper.updatePass(encodePass,identityCard);
        ThrowUtils.throwIf( result!=1, "注册时设置用户密码失败");
        return true;
    }


    //注册时上传用户头像
    @Override
    @Transactional
    public String profileImage(String identityCard, MultipartFile file) {
        int result=0;
        String preview=null;
//判断用户是否存在
        MobileRegisterPo po = mobileStudentMapper.registerByIdentityCard(identityCard);
// ReducedCodeUtils.mobileByRegisterStudent(po);
        int status=AdminUserStatusConstant.STUDENT_SUCCESS;
        if (StringUtils.isBlank(po.getImage())){
//该学生不存在头像，上传头像
            String uploadImage = minioUtils.upload(file);
            result=mobileStudentMapper.registerUploadImage(uploadImage,identityCard,status);
            preview = minioUtils.preview(uploadImage);
        }else {
//存在头像,先删除头像
            minioUtils.remove(po.getImage());
//再次上传头像
            String uploadImage = minioUtils.upload(file);
            result= mobileStudentMapper.registerUploadImage(uploadImage, identityCard,status);
            preview = minioUtils.preview(uploadImage);
        }
//创建腾讯云人脸识别所需要的人员及存储personId
        Long gender = (po.getSex() == '男') ? 1L : 2L;
        CreatePersonDto dto = new CreatePersonDto();
        dto.setGroupId("3506fa78-8e0e-49ab-86a9-f58233409d1a");
        dto.setPersonId(UUID.randomUUID().toString());
        dto.setPersonName(po.getUsername());
        dto.setGender(gender);
        dto.setUrl(preview);
//存储人员到数据库
        dto.setFaceIdentifyId(UUID.randomUUID().toString());
        dto.setStudentPhone(po.getPhone());
        dto.setStudentIdentityCard(po.getIdentityCard());
        CreatePersonResponse createPersonResponse = faceIdentifyUtils.CreatePerson(dto, identifyProperties.getSecretId(), identifyProperties.getSecretKey());
// int resultById=mobileStudentMapper.updatePersonId(po.getIdentityCard(),dto.getPersonId());
        int resultById=mobileStudentMapper.insertPersonToIdentify(dto);
        System.out.println(createPersonResponse);
        if (result==1&&preview!=null&&resultById==1){
            return preview;
        }
        throw new BaseException("上传头像失败");
    }




}
