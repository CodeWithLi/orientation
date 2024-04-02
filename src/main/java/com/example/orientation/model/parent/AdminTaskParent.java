package com.example.orientation.model.parent;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTaskParent {
    private String taskId;
    //任务标题
    private String title;
    private String content;//任务内容
    private String creator;//任务编辑者
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private String createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private String deadTime;//结束时间
    private String school;
    private Integer dependencies;//主线分线
    private double score;//分数
    private Integer status;
    private String location;
}
