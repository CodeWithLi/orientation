package com.example.orientation.model.vo.Mobile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileRankingsVo {
    private String studentId;
    private String studentNumber;
    private String username;
    private String image;
    private Integer ranking;
    private Integer likeCount;
    private Integer points;
}
