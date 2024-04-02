package com.example.orientation.service.impl;

import com.example.orientation.exception.BaseException;
import com.example.orientation.mapper.MobileFollowMapper;
import com.example.orientation.model.dto.Mobile.MobileFollowDto;
import com.example.orientation.model.po.Mobile.FollowInfoPo;
import com.example.orientation.model.po.Mobile.MobileFollowPo;
import com.example.orientation.model.po.Mobile.MobileFollowingInfoPo;
import com.example.orientation.model.vo.Mobile.FocusToFollowers;
import com.example.orientation.model.vo.Mobile.FocusToFollowings;
import com.example.orientation.model.vo.Mobile.FollowInfoVo;
import com.example.orientation.model.vo.Mobile.MobileFollowingInfoVo;
import com.example.orientation.service.MobileFollowService;
import com.example.orientation.utils.MinioUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MobileFollowServiceImpl implements MobileFollowService {

    @Resource
    private MobileFollowMapper mobileFollowMapper;

    @Autowired
    private MinioUtils minioUtils;

    //对博主的关注状态
    @Override
    public boolean followUser(MobileFollowDto mobileFollowDto) {
        String followerId = mobileFollowDto.getFollowerId();
        String followingId = mobileFollowDto.getFollowingId();
        //先判断该博主以及该用户是否存在
        //根据博主id查询该博主是否存在
        int exitFollowing=mobileFollowMapper.exitByUser(followingId);
        //根据粉丝id查询该粉丝是否存在
        int exitFollower = mobileFollowMapper.exitByUser(followerId);
        if (exitFollowing==0||exitFollower==0){
            throw new BaseException("用户不存在");
        }
        //对博主的关注状态
        int result=0;
        //查询关注表里是否存在关注关系,是的话则更改关注关系，不是则创建
        MobileFollowPo po=mobileFollowMapper.exitByFollow(followerId,followingId);
        if (po!=null){
            //存在关注关系，直接更改状态
            result=mobileFollowMapper.updateFollowStatus(mobileFollowDto);
        }else {
            //不存在关注关系，添加关系
            result=mobileFollowMapper.insertFollow(mobileFollowDto);
        }
        if (result==0){
            throw new BaseException("对博主的关注状态更改失败");
        }
        return true;
    }

    //根据手机号查询出博主个人信息
    @Override
    public MobileFollowingInfoVo getFollowInformation(String phone) {
        if (!phone.matches("^1[3456789]\\d{9}$")){
            throw new BaseException("手机号格式错误");
        }
        MobileFollowingInfoPo po=mobileFollowMapper.getFollowInfo(phone);
        if (po==null){
            throw new BaseException("该博主不存在");
        }
        MobileFollowingInfoVo vo=new MobileFollowingInfoVo();
        BeanUtils.copyProperties(po,vo);
        vo.setFollowingId(po.getStudentId());
        if (!StringUtils.isBlank(po.image)&& !Objects.equals(po.image, "")){
            vo.setPreviewImage(minioUtils.preview(po.image));
        }
        return vo;
    }

    //获取用户关注的博主列表
    @Override
    public List<FocusToFollowings> focusFollowings(String followerId) {
        //根据粉丝id查询出所有关注的博主
        List<MobileFollowPo> pos=mobileFollowMapper.focusAllFollowings(followerId);
        //遍历pos
        List<FocusToFollowings> vos = pos.stream().map(mobileFollowPo -> {
            FocusToFollowings vo = new FocusToFollowings();
            BeanUtils.copyProperties(mobileFollowPo, vo);
            //根据博主id查询出每个博主的个人信息
            FollowInfoPo infoPo =
                    mobileFollowMapper.followingInfoById(mobileFollowPo.getFollowingId());
            //设置个人信息vo进行返回
            FollowInfoVo infoVo = new FollowInfoVo();
            BeanUtils.copyProperties(infoPo, infoVo);
            if (!StringUtils.isBlank(infoPo.image) && !Objects.equals(infoPo.image, "")) {
                infoVo.setPreviewImage(minioUtils.preview(infoPo.image));
            }
            vo.setFollowingInfo(infoVo);
            return vo;
        }).collect(Collectors.toList());
        return vos;
    }

    //获取用户的粉丝列表
    @Override
    public List<FocusToFollowers> focusFollowers(String followingId) {
        //根据用户id查询出所有粉丝
        List<MobileFollowPo> pos=mobileFollowMapper.focusAllFollowers(followingId);
        //遍历pos
        List<FocusToFollowers> vos = pos.stream().map(mobileFollowPo -> {
            FocusToFollowers vo = new FocusToFollowers();
            BeanUtils.copyProperties(mobileFollowPo, vo);
            //根据粉丝id查询出每个粉丝的个人信息
            FollowInfoPo infoPo =
                    mobileFollowMapper.followingInfoById(mobileFollowPo.getFollowingId());
            //设置个人信息vo进行返回
            FollowInfoVo infoVo = new FollowInfoVo();
            BeanUtils.copyProperties(infoPo, infoVo);
            if (!StringUtils.isBlank(infoPo.image) && !Objects.equals(infoPo.image, "")) {
                infoVo.setPreviewImage(minioUtils.preview(infoPo.image));
            }
            vo.setFollowerInfo(infoVo);
            return vo;
        }).collect(Collectors.toList());
        return vos;
    }
}
