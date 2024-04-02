package com.example.orientation.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 排行榜
 */
public class CountPointPo {
    private String studentId;
    private Integer points;
    private Integer ranking;
}
