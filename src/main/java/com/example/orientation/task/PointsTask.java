package com.example.orientation.task;

import com.example.orientation.mapper.AdminStudentMapper;
import com.example.orientation.mapper.MobileRankingMapper;
import com.example.orientation.model.po.CountPointPo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
@Slf4j
/**
 * 定时器
 */
public class PointsTask {

    @Resource
    private AdminStudentMapper adminStudentMapper;
    @Resource
    private MobileRankingMapper mobileRankingMapper;

    //每隔6小时自动统计每个用户的积分并重新设定排行
    @Scheduled(cron = "0 0 0,6,12,18 * * ?")
    public void UpdatePointTask(){
        log.info("定时任务开始执行");
        int count=0;
        //统计总积分
        List<CountPointPo> list=adminStudentMapper.countPoint();
        //根据总积分重新设定排行榜
        //对list重新培训，根据从高往低
        Collections.sort(list, Comparator.comparingInt(CountPointPo::getPoints).reversed());
        //遍历集合
        for (int i = 0; i < list.size(); i++){
            CountPointPo po = list.get(i);
            po.setRanking(i+1);
            mobileRankingMapper.updatePoints(po);
            count++;
        }
    }

}
