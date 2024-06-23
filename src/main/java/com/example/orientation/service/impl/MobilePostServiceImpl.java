package com.example.orientation.service.impl;


import com.example.orientation.constant.MobileFollowConstant;
import com.example.orientation.exception.BaseException;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.mapper.MobileFollowMapper;
import com.example.orientation.mapper.MobilePostMapper;
import com.example.orientation.model.dto.Mobile.MobilePostDto;
import com.example.orientation.model.po.Mobile.FollowInfoPo;
import com.example.orientation.model.po.Mobile.MobileFollowPo;
import com.example.orientation.model.po.Mobile.MobilePostPo;
import com.example.orientation.model.vo.Mobile.FollowAndPostVo;
import com.example.orientation.model.vo.Mobile.MobilePostVo;
import com.example.orientation.service.MobilePostService;
import com.example.orientation.utils.MinioUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class MobilePostServiceImpl implements MobilePostService {


    @Resource
    private MobilePostMapper mobilePostMapper;


    @Resource
    private MobileFollowMapper mobileFollowMapper;


    @Autowired
    private MinioUtils minioUtils;


    //发布帖子
    @Override
    public boolean addPost(MobilePostDto mobilePostDto) {
//查询该博主是否存在
        int isExit=mobilePostMapper.exitFollowing(mobilePostDto.getFollowingId());
        if (isExit==0){
            throw new BaseException("发布者不存在");
        }
//添加帖子
        int result=mobilePostMapper.addPost(mobilePostDto);
        ThrowUtils.throwIf( result!=1, "添加帖子失败");
        return true;
    }


    //发布帖子的选择同时上传图片或视频
    @Override
    public boolean uploadMedia(String postId, MultipartFile file) {
//停留2s，防止上传时没有该帖子
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new BaseException("异常，请重新执行");
        }
//查询帖子是否存在
        int isExit=mobilePostMapper.exitPost(postId);
        if (isExit!=1){
            throw new BaseException("该帖子bcz");
        }
        String media = minioUtils.upload(file);
//更新帖子内容
        int result= mobilePostMapper.uploadMedia(postId,media);
        ThrowUtils.throwIf( result!=1, "上传帖子失败");
        return true;
    }


    //删除帖子
    @Override
    public boolean deletePost(String postId) {
        int result=mobilePostMapper.deletePost(postId);
        ThrowUtils.throwIf( result!=1, "删除帖子失败");
        return true;
    }


    //获取用户关注的博主的帖子列表
    @Override
    public List<FollowAndPostVo> getAllPost(String followerId) {
//先根据用户id查询出用户关注的所有博主
        return mobileFollowMapper.focusAllFollowings(followerId)
                .stream().map(followPo -> {
                    FollowAndPostVo vo = new FollowAndPostVo();
//根据博主id查询博主信息
                    FollowInfoPo followingInfoPo = mobileFollowMapper.followingInfoById(followPo.getFollowingId());
//查询出博主个人信息，存储到vo中
                    vo.setFollowingId(followPo.getFollowingId());
                    vo.setFollowingName(followingInfoPo.getUsername());
                    if (!StringUtils.isBlank(followingInfoPo.getImage()) && followingInfoPo.getImage() != null) {
                        String preview = minioUtils.preview(followingInfoPo.getImage());
                        vo.setFollowImage(preview);
                    }


//根据每个博主的信息查询出对应的所有帖子
                    List<MobilePostVo> postVos = mobilePostMapper.allPostByFollowing(followPo.getFollowingId())
                            .stream().map(mobilePostPo -> {
                                MobilePostVo mobilePostVo = new MobilePostVo();
                                BeanUtils.copyProperties(mobilePostPo, mobilePostVo);
                                if (!StringUtils.isBlank(mobilePostPo.getMedia()) && mobilePostPo.getMedia() != null) {
                                    String preview = minioUtils.preview(mobilePostPo.getMedia());
                                    mobilePostVo.setImageOrVideo(preview);
                                }
                                return mobilePostVo;
                            }).collect(Collectors.toList());
//将每个博主的所有帖子存到vo中
                    vo.setPostInfos(postVos);
                    return vo;
                }).collect(Collectors.toList());


    }


    //获取用户关注的某个博主的帖子列表
    @Override
    public FollowAndPostVo getFollowingPost(String followerId,String followingId) {
//根据粉丝id和博主id查询两人是否为关注关系
        MobileFollowPo mobileFollowPo = mobileFollowMapper.exitByFollow(followerId, followingId);
        if (mobileFollowPo.getStatus()== MobileFollowConstant.UN_FOLLOW){
            throw new BaseException("错误,请先关注");
        }


        FollowAndPostVo vo=new FollowAndPostVo();


//根据博主id查询博主的个人信息及发布的帖子


//根据博主id查询博主个人信息
        FollowInfoPo followingInfoPo = mobileFollowMapper.followingInfoById(followingId);
//查询出博主个人信息，存储到vo中
        vo.setFollowingId(followingId);
        vo.setFollowingName(followingInfoPo.getUsername());
        if (!StringUtils.isBlank(followingInfoPo.getImage()) && followingInfoPo.getImage() != "") {
            String preview = minioUtils.preview(followingInfoPo.getImage());
            vo.setFollowImage(preview);
        }


//根据每个博主的信息查询出对应的所有帖子
        List<MobilePostVo> postVos = mobilePostMapper.allPostByFollowing(followingId)
                .stream().map(postPo -> {
                    MobilePostVo postVo = new MobilePostVo();
                    BeanUtils.copyProperties(postPo, postVo);
                    if (!StringUtils.isBlank(postPo.getMedia()) && !Objects.equals(postPo.getMedia(), "")) {
                        String preview = minioUtils.preview(postPo.getMedia());
                        postVo.setImageOrVideo(preview);
                    }
                    return postVo;
                }).collect(Collectors.toList());
//将每个博主的所有帖子存到vo中
        vo.setPostInfos(postVos);
        return vo;
    }




    //获取用户关注的某个博主的某个帖子详细信息
    @Override
    public MobilePostVo getFollowingPostInfo(String followerId, String followingId, String postId) {
//根据粉丝id和博主id查询两人是否为关注关系
        MobileFollowPo mobileFollowPo = mobileFollowMapper.exitByFollow(followerId, followingId);
        if (mobileFollowPo.getStatus()== MobileFollowConstant.UN_FOLLOW){
            throw new BaseException("错误,请先关注");
        }
        MobilePostVo postVo = new MobilePostVo();
//根据博主id和帖子id查询出对应的帖子
        MobilePostPo postPo=mobilePostMapper.getPostInfo(followerId,postId);
//封装信息
        BeanUtils.copyProperties(postPo, postVo);
        if (!StringUtils.isBlank(postPo.getMedia()) && !Objects.equals(postPo.getMedia(), "")) {
            String preview = minioUtils.preview(postPo.getMedia());
            postVo.setImageOrVideo(preview);
        }
        return postVo;
    }
}
