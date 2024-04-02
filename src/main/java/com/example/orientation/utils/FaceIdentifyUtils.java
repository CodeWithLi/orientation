package com.example.orientation.utils;

import com.example.orientation.model.dto.FaceIdentify.CreateGroupDto;
import com.example.orientation.model.dto.FaceIdentify.CreatePersonDto;
import com.example.orientation.model.dto.FaceIdentify.VerifyFaceDto;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.*;
import org.springframework.stereotype.Component;

@Component
public class FaceIdentifyUtils {

    //人脸验证
    public VerifyFaceResponse VerifyFace(VerifyFaceDto dto,String SecretId,String SecretKey){
        VerifyFaceResponse resp=null;
        try{
            Credential cred = new Credential(SecretId, SecretKey);
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            // 实例化要请求产品的client对象,clientProfile是可选的
            IaiClient client = new IaiClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            VerifyFaceRequest req = new VerifyFaceRequest();
            req.setPersonId(dto.getPersonId());
//            req.setImage(dto.getImage());
            req.setUrl(dto.getUrl());
            req.setQualityControl(dto.getQualityControl());
            req.setNeedRotateDetection(dto.getNeedRotateDetection());
            // 返回的resp是一个VerifyFaceResponse的实例，与请求对象对应
            resp = client.VerifyFace(req);
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return resp;
    }

    //创建人员
    public CreatePersonResponse CreatePerson(CreatePersonDto dto,String SecretId,String SecretKey){
        CreatePersonResponse resp=null;
        try{
            Credential cred = new Credential(SecretId, SecretKey);
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            // 实例化要请求产品的client对象,clientProfile是可选的
            IaiClient client = new IaiClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreatePersonRequest req = new CreatePersonRequest();
            req.setGroupId(dto.getGroupId());
            req.setPersonId(dto.getPersonId());
            req.setPersonName(dto.getPersonName());
            req.setGender(dto.getGender());
//            req.setPersonExDescriptionInfos(dto.getPersonExDescriptionInfos());
            req.setUrl(dto.getUrl());
//            req.setImage(dto.getImage());
//            req.setUniquePersonControl(dto.getUniquePersonControl());
//            req.setQualityControl(dto.getQualityControl());
//            req.setNeedRotateDetection(dto.getNeedRotateDetection());
            // 返回的resp是一个CreatePersonResponse的实例，与请求对象对应
            resp = client.CreatePerson(req);
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return resp;
    }

    //生成人员库，用于存储PersonId
    public CreateGroupResponse createGroup(CreateGroupDto dto, String SecretId, String SecretKey){
        CreateGroupResponse resp=null;
        try{
            Credential cred = new Credential(SecretId, SecretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            IaiClient client = new IaiClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateGroupRequest req = new CreateGroupRequest();
            req.setGroupId(dto.getGroupId());
            req.setGroupName(dto.getGroupName());
            req.setTag(dto.getTag());
            req.setGroupExDescriptions(dto.getGroupExDescriptions());
            req.setFaceModelVersion(dto.getFaceModelVersion());
            // 返回的resp是一个CreateGroupResponse的实例，与请求对象对应
            resp = client.CreateGroup(req);
            // 输出json格式的字符串回包
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return resp;
    }
}
