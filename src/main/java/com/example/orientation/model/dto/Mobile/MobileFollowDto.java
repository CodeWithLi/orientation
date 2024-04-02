package com.example.orientation.model.dto.Mobile;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileFollowDto {
    private String followId;
    //关注者id
    private String followerId;
    //博主id
    private String followingId;
    //关注状态
    private Integer status;
    //关注时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm" )
    private String createTime;

}
