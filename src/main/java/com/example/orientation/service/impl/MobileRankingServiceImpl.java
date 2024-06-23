package com.example.orientation.service.impl;


import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.mapper.MobileRankingMapper;
import com.example.orientation.model.po.Mobile.MobileRankingsPo;
import com.example.orientation.model.vo.Mobile.MobileRankingsVo;
import com.example.orientation.service.MobileRankingService;
import com.example.orientation.utils.MinioUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class MobileRankingServiceImpl implements MobileRankingService {


    @Resource
    private MobileRankingMapper mobileRankingMapper;


    @Autowired
    private MinioUtils minioUtils;


    //展示排行榜
    @Override
    public List<MobileRankingsVo> rankings() {
        List<MobileRankingsPo> pos = mobileRankingMapper.selectRankings();
        List<MobileRankingsVo> collect = pos.stream().map(mobileRankingsPo -> {
            MobileRankingsVo vo = new MobileRankingsVo();
            BeanUtils.copyProperties(mobileRankingsPo, vo);
            if (!StringUtils.isBlank(mobileRankingsPo.getImageName()) && mobileRankingsPo.getImageName() != "") {
                String preview = minioUtils.preview(mobileRankingsPo.getImageName());
                vo.setImage(preview);
            }
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }


    //点赞数+1
    @Override
    public boolean increaseLikeCount(String studentId) {
        int result= mobileRankingMapper.increaseLikeCount(studentId);
        ThrowUtils.throwIf( result!=1, "点赞排行榜失败");
        return true;
    }
}
