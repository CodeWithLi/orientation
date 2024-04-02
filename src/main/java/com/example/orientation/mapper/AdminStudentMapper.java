package com.example.orientation.mapper;

import com.example.orientation.model.dto.Admin.AdminStudentDto;
import com.example.orientation.model.dto.Admin.PageDto;
import com.example.orientation.model.po.Admin.AdminStudentPo;
import com.example.orientation.model.po.CountPointPo;
import com.example.orientation.model.vo.Admin.AdminStudentVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface AdminStudentMapper {

    //查询所有学生数据
    List<AdminStudentPo> selectAllStudent();

    //导入数据
    int insertByExcel(List<AdminStudentDto> excelDto);

    //分页查询学生数据
    Page<AdminStudentVo> selectByPage(PageDto pageDto);

    //查询男女比例
    Map<String, Long> proportionBySex();

    //查询注册人数
    Map<String, Long> registerCount();

    //各专业人数统计
    @MapKey("student_id")
    List<Map<String, Object>> majorCount();

    //各学院人数统计
    List<Map<String, Integer>> collegeCount();

    //各地址人数统计
    List<Map<String, Integer>> addressCount();

    //添加学生
    int insertStudent(AdminStudentDto dto);

    //批量删除数据
    int deleteByIds(List<String> ids);

    //修改学生数据
    int updateStudent(AdminStudentDto adminStudentDto);

    //根据身份证查询是否有该用户
    AdminStudentPo selectStudentByIdentityCard(String identityCard);

    //统计用户的总积分
    List<CountPointPo> countPoint();

    //更改每个用户的总积分
//    int updatePoints(List<CountPointPo> list);
    int updatePoints(CountPointPo po);
}
