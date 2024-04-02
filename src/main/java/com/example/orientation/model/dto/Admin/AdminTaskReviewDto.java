package com.example.orientation.model.dto.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTaskReviewDto {
    private String reviewId;
    private String taskId;
    private String account;
    private Integer status;
}