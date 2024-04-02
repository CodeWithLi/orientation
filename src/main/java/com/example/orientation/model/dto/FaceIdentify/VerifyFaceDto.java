package com.example.orientation.model.dto.FaceIdentify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyFaceDto {
    //人员ID
    private String personId;
    //图片 base64 数据
    private String image;
    //图片的 Url
    private String url;
    //图片质量控制.0: 不进行控制
    private Long qualityControl;
    //是否开启图片旋转识别支持。0为不开启，1为开启。默认为0
    private Long needRotateDetection;
}
