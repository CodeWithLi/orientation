package com.example.orientation.model.dto.FaceIdentify;


import com.tencentcloudapi.iai.v20200303.models.PersonExDescriptionInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonDto {
    //待加入的人员库ID
    private String groupId;
    //人员名称
    private String personName;
    //人员ID
    private String personId;
    //0代表未填写，1代表男性，2代表女性
    private Long gender;
    //人员描述字段内容，key-value
    private PersonExDescriptionInfo[] personExDescriptionInfos;
    //图片 base64 数据
    private String image;
    //图片的 Url
    private String url;
    //此参数用于控制判断 Image 或 Url 中图片包含的人脸，是否在人员库中已有疑似的同一人。
//如果判断为已有相同人在人员库中，则不会创建新的人员，返回疑似同一人的人员信息。
    private Long uniquePersonControl;
    //图片质量控制.0: 不进行控制
    private Long qualityControl;
    //是否开启图片旋转识别支持。0为不开启，1为开启。默认为0
    private Long needRotateDetection;
    //id
    private String faceIdentifyId;
    //对应的电话
    private String studentPhone;
    //对应的身份证
    private String studentIdentityCard;
}
