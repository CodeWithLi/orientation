package com.example.orientation.model.dto.Admin;


import com.example.orientation.model.parent.AdminAdvertiseParent;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;


import java.io.Serializable;




@Data
@AllArgsConstructor
@NoArgsConstructor
//extends AdminAdvertiseParent
public class AdminAdvertiseDto implements Serializable {
    @NotNull("广告id不能为空")
    public String advertiseId;//广告id
    public String title;//标题
    public String content;//内容
    public String type;//类型
    public String targetPerson;//目标
    public String keyWords;//关键词
    @DecimalMin(value = "0.0", message = "广告成本不能为负数")
    public Long costs;//广告推广费用
    public Long status;//发布状态
    public String location;//位置
    public String company;
    public String companyId;//公司id
    public Long addStatus;//添加状态，0为草稿
    public Integer shelvesStatus;//上架状态


    private String imageVideoName;


}
