package com.example.orientation.mapper;

import com.example.orientation.model.po.CountPointPo;
import com.example.orientation.model.po.Mobile.MobileRankingsPo;

import java.util.List;

public interface MobileRankingMapper {

    //展示排行榜
    List<MobileRankingsPo> selectRankings();

    //增加点赞数
    int increaseLikeCount(String studentId);

    //更改每个用户的总积分
    int updatePoints(CountPointPo po);
}
