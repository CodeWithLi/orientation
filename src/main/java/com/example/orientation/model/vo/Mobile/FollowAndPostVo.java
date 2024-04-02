package com.example.orientation.model.vo.Mobile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 返回帖子+博主信息
 */
public class FollowAndPostVo {
    //博主id
    private String followingId;
    //博主名字
    private String followingName;
    //博主头像
    private String followImage;
    //帖子信息
    private List<MobilePostVo> postInfos;
}
