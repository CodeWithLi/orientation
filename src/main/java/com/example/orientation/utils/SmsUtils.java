package com.example.orientation.utils;

import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniMessage;
import com.apistd.uni.sms.UniSMS;
import com.example.orientation.exception.BaseException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class SmsUtils {

    public static UniResponse sendSms(String accessKeyId,String setPhone,String Signature,String templateId,String code,String ttl){
        Uni.init(accessKeyId);

        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<String, String>();
        templateData.put("code", code);
        templateData.put("ttl", ttl);

        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo(setPhone)
                .setSignature(Signature)
                .setTemplateId(templateId)
                .setTemplateData(templateData);

        UniResponse res=null;
        // 发送短信
        try {
            res = message.send();
        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }
        return res;
    }

    //从缓存中获取验证码
//    @Cacheable(cacheNames = "sms",key = "#purpose+'-'+#account")
    public String getSmsCode(String account,String purpose){
        return null;
    }
}
