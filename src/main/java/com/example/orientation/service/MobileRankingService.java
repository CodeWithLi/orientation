package com.example.orientation.service;


import com.example.orientation.model.vo.Mobile.MobileRankingsVo;


import java.util.List;


public interface MobileRankingService {


    //展示排行榜
    List<MobileRankingsVo> rankings();


    //添加点赞数
    boolean increaseLikeCount(String studentId);
}
