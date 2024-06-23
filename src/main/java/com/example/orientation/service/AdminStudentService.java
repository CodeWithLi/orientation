package com.example.orientation.service;


import com.example.orientation.common.PageResult;
import com.example.orientation.model.dto.Admin.AdminStudentDto;
import com.example.orientation.model.vo.Admin.AdminStudentVo;
import com.example.orientation.model.vo.FormatResultVo.ResultByNestVo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;


public interface AdminStudentService {


    //查询所有学生数据
    List<AdminStudentVo> queryAllStudent();


    //分页查询学生数据
    PageResult queryByPage(Integer index,Integer pages);


    //导入数据
// boolean uploadByExcel(MultipartFile file);
    boolean uploadByExcel(MultipartFile file, Workbook workbook );


    //查询男女比例
    List<Map<String, Object>> proportionBySex();


    //查询注册人数
    List<Map<String, Object>> registerCount();


    //各专业人数统计
// List<Map<Object,Object>> majorCount();
// List<ResultByNestVo> majorCount();
    List<ResultByNestVo> majorCount();


    //各学院人数统计
    List<Map<String, Integer>> collegeCount();


    //各地址人数统计
    List<Map<String, Integer>> addressCount();


    //添加学生
    boolean addStudent(AdminStudentDto adminStudentDto);


    //批量删除
    boolean deleteByIds(String[] ids);


    //修改学生信息
    boolean editStudent(AdminStudentDto adminStudentDto);
}
