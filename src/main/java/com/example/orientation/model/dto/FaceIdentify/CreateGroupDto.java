package com.example.orientation.model.dto.FaceIdentify;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupDto {
    //人员库名称,eg:腾讯深圳员工库
    private String GroupName;
    //人员库 ID,eg:TencentShenZhenEmployee
    private String GroupId;
    //人员库自定义描述字段
    private String[] GroupExDescriptions;
    //人员库信息备注,eg:不含实习生
    private String Tag;
    //人脸识别服务所用的算法模型版本,eg: “2.0”和“3.0“ 两个输入
    private String FaceModelVersion;
}
