package com.example.orientation.model.vo.Mobile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 帖子信息
 */
public class MobilePostVo {
    //帖子id
    private String postId;
    //帖子内容
    private String content;
    //帖子媒介
    private String imageOrVideo;
    //帖子点赞数
    private Integer likes;
    //帖子评论数
    private Integer comments;
    //帖子最新更新时间
    private String updateTime;
}