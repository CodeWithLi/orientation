package com.example.orientation.service.impl;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.example.orientation.common.PageResult;
import com.example.orientation.constant.AdminUserStatusConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.mapper.AdminStudentMapper;
import com.example.orientation.model.dto.Admin.AdminStudentDto;
import com.example.orientation.model.dto.Admin.PageDto;
import com.example.orientation.model.po.Admin.AdminStudentPo;
import com.example.orientation.model.vo.Admin.AdminStudentVo;
import com.example.orientation.model.vo.FormatResultVo.DataByNestVo;
import com.example.orientation.model.vo.FormatResultVo.ResultByNestVo;
import com.example.orientation.service.AdminStudentService;
import com.example.orientation.utils.MinioUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
//@Transactional
@Slf4j
public class AdminStudentServiceImpl implements AdminStudentService {


    @Resource
    private AdminStudentMapper adminStudentMapper;


    @Autowired
    private MinioUtils minioUtils;


    //查询所有学生数据
    @Override
    public List<AdminStudentVo> queryAllStudent() {
        List<AdminStudentPo> listPo = adminStudentMapper.selectAllStudent();
        List<AdminStudentVo> collectVos = listPo.stream().map(adminStudentPo -> {
            AdminStudentVo vo = new AdminStudentVo();
            BeanUtils.copyProperties(adminStudentPo, vo);
            return vo;
        }).collect(Collectors.toList());
        return collectVos;
    }


    //分页查询学生数据
    @Override
    public PageResult queryByPage(Integer index, Integer pages) {
        PageDto pageDto = new PageDto(index, pages);
        PageHelper.startPage(pageDto.getIndex(), pageDto.getPages());
        Page<AdminStudentVo> page = adminStudentMapper.selectByPage(pageDto);
        return new PageResult(page.getTotal(), page.getResult());
    }




    //查询男女比例
    @Override
    public List<Map<String, Object>> proportionBySex() {
        List<Map<String, Object>> list = new ArrayList<>();
        adminStudentMapper.proportionBySex().forEach((key, value) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", key);
            map.put("value", value);
            if ("已注册男生".equals(key)) {
                map.put("color", "indigo.6");
            } else {
                map.put("color", "pink.6");
            }
            list.add(map);
        });
        return list;
    }


    //查询注册人数
    @Override
    public List<Map<String, Object>> registerCount() {
        List<Map<String, Object>> list = new ArrayList<>();
        adminStudentMapper.registerCount().forEach((key, value) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", key);
            map.put("value", value);
            if ("已注册".equals(key)) {
                map.put("color", "teal.6");
            } else {
                map.put("color", "red.6");
            }
            list.add(map);
        });
        return list;
    }


    //各专业人数统计
    @Override
    public List<ResultByNestVo> majorCount() {
        List<ResultByNestVo> finalResult = new ArrayList<>();
        Map<String, List<Map<String, Object>>> collegeMap = new HashMap<>();


        for (Map<String, Object> result : adminStudentMapper.majorCount()) {
            String college = (String) result.get("college");
// 检查学院是否已存在于 Map 中
            List<Map<String, Object>> collegeList = collegeMap.get(college);
            if (collegeList == null) {
                collegeList = new ArrayList<>();
                collegeMap.put(college, collegeList);
            }
            collegeList.add(result);
        }


        for (Map.Entry<String, List<Map<String, Object>>> entry : collegeMap.entrySet()) {
            ResultByNestVo resultByNestVo = new ResultByNestVo();
            resultByNestVo.setCollege(entry.getKey());


            List<DataByNestVo> data = entry.getValue().stream()
                    .map(result -> {
                        DataByNestVo vo = new DataByNestVo();
                        vo.setMajor((String) result.get("major"));
                        vo.setCount(((Number) result.get("count")).intValue());
                        return vo;
                    })
                    .collect(Collectors.toList());


            resultByNestVo.setData(data);
            finalResult.add(resultByNestVo);
        }


        return finalResult;
    }


    //各学院人数统计
    @Override
    public List<Map<String, Integer>> collegeCount() {
        return adminStudentMapper.collegeCount();
    }


    //各地址人数统计
    @Override
    public List<Map<String, Integer>> addressCount() {
        return adminStudentMapper.addressCount();
    }


    //添加学生
    @Override
    public boolean addStudent(AdminStudentDto adminStudentDto) {
        if (adminStudentDto.getPhone() != null) {
            ThrowUtils.throwIf(!adminStudentDto.getPhone().matches("^1[3456789]\\d{9}$"), "手机号码格式不符");
        }
        if (adminStudentDto.getIdentityCard() != null) {
            ThrowUtils.throwIf(!adminStudentDto.getIdentityCard().matches("^\\d{17}[0-9Xx]$"), "身份证格式不符");
        }
//根据身份证查询是否已存在该学生
        AdminStudentPo po=adminStudentMapper.selectStudentByIdentityCard(adminStudentDto.getIdentityCard());
        if (po!=null){
            throw new BaseException("该学生已存在");
        }
        adminStudentDto.setStatus(AdminUserStatusConstant.STUDENT_STOP);
        adminStudentDto.setIsDelete(AdminUserStatusConstant.IS_NOT_DELETED);
        int result = adminStudentMapper.insertStudent(adminStudentDto);
        ThrowUtils.throwIf(result != 1, "删除失败");
        return true;
    }


    //批量删除学生数据
    @Transactional
    @Override
    public boolean deleteByIds(String[] ids) {
        List<String> uuidList = Arrays.asList(ids);
        for (String id : uuidList) {
            try {
                UUID.fromString(id);
            } catch (Exception e) {
                throw new BaseException("传输的id错误,id不为uuid格式,删除数据失败");
            }
        }
        int result = adminStudentMapper.deleteByIds(uuidList);
        ThrowUtils.throwIf(uuidList.size() != result, "删除数量与传输的id数量不符,删除数据失败");
        return true;
    }


    //修改学生信息
    @Override
    public boolean editStudent(AdminStudentDto adminStudentDto) {
        if (adminStudentDto.getPhone() != null) {
            ThrowUtils.throwIf(!adminStudentDto.getPhone().matches("^1[3456789]\\d{9}$"), "手机号码格式不符");
        }
        if (adminStudentDto.getIdentityCard() != null) {
            ThrowUtils.throwIf(!adminStudentDto.getIdentityCard().matches("^\\d{17}[0-9Xx]$"), "身份证格式不符");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (adminStudentDto.getPassword() != null) {
            ThrowUtils.throwIf(adminStudentDto.getPassword().length() < 8, "密码长度小于8");
            adminStudentDto.setPassword(passwordEncoder.encode(adminStudentDto.getPassword()));
        }
        int result = adminStudentMapper.updateStudent(adminStudentDto);
        ThrowUtils.throwIf(result != 1, "更改失败");
        return true;
    }


    //导入学生数据
    @Transactional
    @Override
    public boolean uploadByExcel(MultipartFile file, Workbook workbook) {


        List<List<AdminStudentDto>> allAdminStudentDto = new ArrayList<>();
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
//读取excel表格中不同的sheet数据并存储到一个list中
            try {
                ExcelReader excelReader = ExcelUtil.getReader(file.getInputStream()).setSheet(sheet);
                List<AdminStudentDto> adminStudentDtos = excelReader.readAll(AdminStudentDto.class);
                allAdminStudentDto.add(adminStudentDtos);
            } catch (IOException e) {
                throw new BaseException(e.getMessage());
            }
        }
        if (!CollectionUtils.isEmpty(allAdminStudentDto)) {
            List<AdminStudentDto> listDto = allAdminStudentDto.stream()
                    .flatMap(List::stream)
                    .peek(adminStudentDto -> {
                        adminStudentDto.setStudentId(UUID.randomUUID().toString());
                        adminStudentDto.setStatus(AdminUserStatusConstant.STUDENT_STOP);
                        adminStudentDto.setIsDelete(AdminUserStatusConstant.IS_NOT_DELETED);
                    })
                    .collect(Collectors.toList());
            int insertSize = adminStudentMapper.insertByExcel(listDto);
            ThrowUtils.throwIf(listDto.size() != insertSize, "导入数据失败");
            return true;
        }
        return false;
    }


}
