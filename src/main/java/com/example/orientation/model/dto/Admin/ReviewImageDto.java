package com.example.orientation.model.dto.Admin;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewImageDto {
    private String reviewId;
    private String taskId;
    private String account;
    private String reviewImageName;
    private Integer status;
}
