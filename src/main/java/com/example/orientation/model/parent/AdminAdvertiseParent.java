package com.example.orientation.model.parent;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAdvertiseParent implements Serializable {
    public String advertiseId;//广告id
    public String title;//标题
    public String content;//内容
    public String type;//类型
    public String targetPerson;//目标
    public String keyWords;//关键词
    public Long costs;//广告推广费用
    public Long status;//发布状态
    public String location;//位置
    public String company;
    public String companyId;//公司id


}
