package com.example.orientation.model.po.Admin;


import com.example.orientation.model.parent.AdminAdvertiseParent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;




@AllArgsConstructor
@Data
@NoArgsConstructor
public class AdminAdvertisePo extends AdminAdvertiseParent implements Serializable {
    private Long clickNumber;//点击数量
    private Long totalProfit;//总盈利
    private String imageVideoName;
}

