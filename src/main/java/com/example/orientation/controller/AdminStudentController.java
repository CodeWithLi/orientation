package com.example.orientation.controller;

import com.example.orientation.common.PageResult;
import com.example.orientation.common.Result;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.model.dto.Admin.AdminStudentDto;
import com.example.orientation.model.vo.Admin.AdminStudentVo;
import com.example.orientation.model.vo.FormatResultVo.ResultByNestVo;
import com.example.orientation.service.AdminStudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 后台学生信息
 */
@RestController
@RequestMapping("/admin/student")
@Slf4j
public class AdminStudentController {

    @Autowired
    private AdminStudentService adminStudentService;

    /**
     * 查询所有学生数据
     * @return
     */
    @GetMapping("/all")
    public Result AllStudent(){
        log.info("查询所有学生数据");
        List<AdminStudentVo> list=adminStudentService.queryAllStudent();
        return Result.success(list);
    }

    /**
     * 分页查询学生数据
     * @param index
     * @param pages
     * @return
     */
    @GetMapping("/page")
    public Result queryByPage(@RequestParam Integer index,@RequestParam() Integer pages){
        log.info("分页的页数:{}及数量为：{}",index,pages);
        PageResult result=adminStudentService.queryByPage(index,pages);
        return Result.success(result);
    }

    /**
     * 添加学生
     * @param adminStudentDto
     * @return
     */
    @PostMapping("/add")
    public Result addStudent(@RequestBody AdminStudentDto adminStudentDto){
        //判断传来的数据是否为空
        if (adminStudentDto == null) {
            return Result.error("请传输数据");
        }
        boolean add = adminStudentService.addStudent(adminStudentDto);
        log.info("添加状态：{}", add);
        ThrowUtils.throwIf(!add,"添加失败");
        return Result.success("添加成功");
    }

    /**
     * 修改学生数据
     * @param adminStudentDto
     * @return
     */
    @PutMapping("/edit")
    public Result editStudent(@RequestBody AdminStudentDto adminStudentDto){
        if (adminStudentDto==null){
            return Result.error("传输的数据为空");
        }
       boolean result=adminStudentService.editStudent(adminStudentDto);
       ThrowUtils.throwIf(!result,"更改失败");
       return Result.success("修改成功");
    }
    /**
     * 批量删除学生数据
     * @param ids
     * @return
     */
    @DeleteMapping("/remove")
    public Result deleteByIds(@RequestParam("ids") String[] ids){
        if (ids==null||ids.length==0){
            return Result.error("传输的id为空");
        }
        boolean result=adminStudentService.deleteByIds(ids);
        ThrowUtils.throwIf(!result,"删除失败");
        return Result.success("删除成功");
    }
    /**
     * 导入excel数据
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result uploadByExcel(@RequestParam MultipartFile file){
        if (file==null){
            return Result.error("上传的文件为空");
        }
        Workbook workbook=null;
        try {
            workbook = WorkbookFactory.create(file.getInputStream());
        } catch (IOException e) {
            log.info("导入学生数据时异常的信息为：{}",e.getMessage());
            throw new BaseException("上传的文件不是excel文件，请正确上传文件");
        }
        boolean result=adminStudentService.uploadByExcel(file,workbook);
        ThrowUtils.throwIf(!result,"导入数据失败");
        return Result.success("导入数据成功");
    }

    /**
     * 男女比例统计
     * @return
     */
    @GetMapping("/proportionBySex")
    public Result proportionBySex(){
        log.info("查询男女比例");
        List<Map<String, Object>> list = adminStudentService.proportionBySex();
        ThrowUtils.throwIf(list.isEmpty(),"数据库的性别无信息,为空");
        return Result.success(list);
    }

    /**
     *  注册人数统计
     * @return
     */
    @GetMapping("/registerCount")
    public Result registerCount(){
        log.info("查询已注册的比例");
        List<Map<String, Object>> list = adminStudentService.registerCount();
        ThrowUtils.throwIf(list.isEmpty(),"数据库无信息,为空");
        return Result.success(list);
    }

    /**
     * 各专业人数统计
     * @return
     */
    @GetMapping("/majorCount")
    public Result majorCount(){
        log.info("专业统计");
        List<ResultByNestVo> maps = adminStudentService.majorCount();
        ThrowUtils.throwIf(maps.isEmpty(),"数据库的专业无信息,为空");
        return Result.success(maps);
    }

    /**
     * 各学院人数统计
     * @return
     */
    @GetMapping("/collegeCount")
    public Result collegeCount(){
        log.info("专业统计");
        List<Map<String,Integer>> list=adminStudentService.collegeCount();
        ThrowUtils.throwIf(list.isEmpty(),"数据库的学院无信息,为空");
        return Result.success(list);
    }

    /**
     * 各地址人数统计
     * @return
     */
    @GetMapping("/addressCount")
    public Result addressCount(){
        log.info("专业统计");
        List<Map<String,Integer>> list=adminStudentService.addressCount();
        ThrowUtils.throwIf(list.isEmpty(),"数据库的地址无信息,为空");
        return Result.success(list);
    }
}
