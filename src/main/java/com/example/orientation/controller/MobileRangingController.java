package com.example.orientation.controller;


import com.example.orientation.common.Result;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.model.dto.Mobile.MobileRankingsDto;
import com.example.orientation.model.vo.Mobile.MobileRankingsVo;
import com.example.orientation.service.MobileRankingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * 排行榜
 */
@RestController
@RequestMapping("/mobile/ranking")
@Slf4j
public class MobileRangingController {


    @Autowired
    private MobileRankingService mobileRankingService;


    /**
     * 展示排行榜
     * @return
     */
    @GetMapping("/showRankings")
    public Result showRankings(){
        log.info("展示排行榜");
        List<MobileRankingsVo> vos= mobileRankingService.rankings();
        return Result.success(vos);
    }


    /**
     * 点击点赞后点赞数+1
     * @param dto
     * @return
     */
    @PutMapping("increaseLikeCount")
    public Result increaseLikeCount(@RequestBody MobileRankingsDto dto){
        String studentId=dto.getStudentId();
        if (StringUtils.isBlank(studentId) && studentId==""){
            return Result.error("请求参数错误");
        }
        boolean result= mobileRankingService.increaseLikeCount(studentId);
        ThrowUtils.throwIf(!result,"点赞失败");
        return Result.success("点赞成功");
    }
}
