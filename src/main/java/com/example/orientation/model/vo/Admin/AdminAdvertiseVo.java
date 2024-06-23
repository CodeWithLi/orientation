package com.example.orientation.model.vo.Admin;


import com.example.orientation.model.parent.AdminAdvertiseParent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.List;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class AdminAdvertiseVo extends AdminAdvertiseParent implements Serializable {
    private Long clickNumber;//点击数量
    private Long totalProfit;//总盈利
    private List<String> imageVideos;
// private String imageVideo;


}
