package com.example.orientation.model.vo.FormatResultVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用于返回每个学院对应的每个专业的人数嵌套格式
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultByNestVo implements Serializable {
    private String college;
    private List<DataByNestVo> data;
}
