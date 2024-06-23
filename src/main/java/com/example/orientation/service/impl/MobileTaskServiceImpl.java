package com.example.orientation.service.impl;


import com.example.orientation.constant.AdminTaskConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.mapper.MobileTaskMapper;
import com.example.orientation.model.dto.Admin.ReviewImageDto;
import com.example.orientation.model.dto.FaceIdentify.VerifyFaceDto;
import com.example.orientation.model.dto.Mobile.MobileReviewDto;
import com.example.orientation.model.po.Mobile.MobileTaskPo;
import com.example.orientation.properties.FaceIdentifyProperties;
import com.example.orientation.service.MobileTaskService;
import com.example.orientation.utils.BaseUtils;
import com.example.orientation.utils.FaceIdentifyUtils;
import com.example.orientation.utils.MinioUtils;
import com.example.orientation.utils.ReviewImageUtils;
import com.tencentcloudapi.iai.v20200303.models.VerifyFaceResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.Objects;
import java.util.UUID;


@Service
@Slf4j
public class MobileTaskServiceImpl implements MobileTaskService {


    @Resource
    private MobileTaskMapper mobileTaskMapper;


    @Autowired
    private FaceIdentifyProperties faceIdentifyProperties;


    @Autowired
    private FaceIdentifyUtils faceIdentifyUtils;


    @Autowired
    private MinioUtils minioUtils;


    //实现人脸识别 通过拦截器获取用户信息 通过信息查询用户的personId
    @Override
    public boolean FaceIdentify(MultipartFile file) {
        String upload = minioUtils.upload(file);
//获取图片的链接
        String imageUrl = minioUtils.preview(upload);
        String personId = null;
//根据拦截器获取account并查询用户的personId
        String account = BaseUtils.getCurrentAccount();
//判断为学号还是手机号
        if (account.matches("^1[3456789]\\d{9}$")) {
//为手机号 根据手机号查询person
            personId = mobileTaskMapper.selectStudentByPhone(account).getPersonId();
        } else {
//为学号，根据学号查询
            personId = mobileTaskMapper.selectStudentByStudentNumber(account).getPersonId();
        }
        if (StringUtils.isBlank(personId) && Objects.equals(personId, "")) {
            throw new BaseException("腾讯云无personId，先进行注册上传头像");
        }
        VerifyFaceDto dto = new VerifyFaceDto();
        dto.setUrl(imageUrl);
        dto.setPersonId(personId);
        dto.setQualityControl(0L);
//图片反转也可以识别
        dto.setNeedRotateDetection(1L);
        VerifyFaceResponse verifyFaceResponse
                = faceIdentifyUtils.VerifyFace(dto, faceIdentifyProperties.getSecretId(), faceIdentifyProperties.getSecretKey());
        Float score = verifyFaceResponse.getScore();
        log.info("人脸识别结果：{}", verifyFaceResponse);
        return score > 80.0;
    }


    //用户打卡上传并且手动审核任务
    @Override
    public boolean manualReviewImage(MultipartFile file, String taskId) {
//通过minio获取
        String uploadImageName = minioUtils.upload(file);
//通过拦截器获取用户信息
//该信息可以后续通过判断是手机号还是学号
        String account = BaseUtils.getCurrentAccount();
        ReviewImageDto imageDto = ReviewImageDto.builder()
                .taskId(taskId)
                .reviewId(UUID.randomUUID().toString())
                .reviewImageName(uploadImageName)
                .account(account)
                .status(AdminTaskConstant.UN_SUCCESS)
                .build();
        int result = mobileTaskMapper.reviewImages(imageDto);
        ThrowUtils.throwIf( result!=1, "用户打卡上传并且手动审核任务失败");
        return true;


    }


    //实现任务打卡的自动审核
    @Override
    @Transactional
    public boolean autoReviewImage(MultipartFile file, String taskId) {
//根据id查询该任务
        MobileTaskPo po = mobileTaskMapper.selectTaskById(taskId);
        String imageName = po.getImageName();
        if (StringUtils.isBlank(imageName) && Objects.equals(imageName, "")) {
            throw new BaseException("图片信息错误，该任务无照片信息");
        }
        String previewImage = minioUtils.preview(imageName);
        Double percentage = ReviewImageUtils.AutoReview(file, previewImage);
        if (percentage >=80.0) {
            throw new BaseException("审核失败,请转手动审核");
        }
//审核成功，记录分数
//任务的分数
        Double score = po.getScore();
//通过拦截器获得用户的信息
        String studentId = null;
        if (BaseUtils.getCurrentAccount().matches("^1[3456789]\\d{9}$")) {
            studentId = mobileTaskMapper.selectStudentByPhone(BaseUtils.getCurrentAccount()).getStudentId();
        } else {
            studentId = mobileTaskMapper.selectStudentByStudentNumber(BaseUtils.getCurrentAccount()).getStudentId();
        }
//用户打卡任务成功，将该任务所对应的积分加给学生
        MobileReviewDto dto = MobileReviewDto.builder()
                .studentTaskId(UUID.randomUUID().toString())
                .studentId(studentId)
                .taskId(taskId)
                .status(AdminTaskConstant.SUCCESS)
                .score(score)
                .build();
        int result = mobileTaskMapper.addScoreByStudent(dto);
        ThrowUtils.throwIf( result!=1, "任务打卡的自动审核失败");
        return true;
    }
}
