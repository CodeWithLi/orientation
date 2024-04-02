package com.example.orientation.model.vo.FormatResultVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用于前端需要返回的嵌套格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataByNestVo implements Serializable {
    private String major;
    private Integer count;
}
