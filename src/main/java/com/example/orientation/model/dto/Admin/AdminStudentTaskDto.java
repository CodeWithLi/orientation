package com.example.orientation.model.dto.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminStudentTaskDto {
    private String studentTaskId;
    private String studentId;
    private String taskId;
    private Integer status;
    private Double score;
}
