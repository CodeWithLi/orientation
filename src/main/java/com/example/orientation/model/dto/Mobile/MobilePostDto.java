package com.example.orientation.model.dto.Mobile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobilePostDto {
    private String postId;
    //发布者id（博主id）
    private String followingId;
    private String content;
    //图片or视频
    private String media;
    private String createTime;
    //点赞数
    private Integer likes;
    //评论数
    private Integer comments;
    private String updateTime;
}
