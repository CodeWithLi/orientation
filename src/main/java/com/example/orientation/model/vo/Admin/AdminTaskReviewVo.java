package com.example.orientation.model.vo.Admin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTaskReviewVo {
    private String reviewId;
    private String taskId;
    private String account;
    private String taskImage;
    private String reviewImage;
    private Integer status;
}
