package com.example.orientation.model.po.Mobile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileRankingsPo {
    private String studentId;
    private String studentNumber;
    private String username;
    private String imageName;
    private Integer ranking;
    private Integer likeCount;
    private Integer points;
}
