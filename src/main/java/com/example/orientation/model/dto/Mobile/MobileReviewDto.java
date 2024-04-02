package com.example.orientation.model.dto.Mobile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//用于移动端用户人脸识别或图片自动审核所需
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MobileReviewDto {
    private String studentTaskId;
    private String taskId;
    private String studentId;
    private Integer status;
    private Double score;
    private String personId;
}
