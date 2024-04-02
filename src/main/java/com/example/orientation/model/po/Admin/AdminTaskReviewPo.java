package com.example.orientation.model.po.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTaskReviewPo {
    private String reviewId;
    private String taskId;
    private String account;
    private String reviewImageName;
    private Integer status;
}
