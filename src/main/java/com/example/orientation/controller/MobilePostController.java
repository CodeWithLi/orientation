package com.example.orientation.controller;

import com.example.orientation.common.Result;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.model.dto.Mobile.MobilePostDto;
import com.example.orientation.model.vo.Mobile.FollowAndPostVo;
import com.example.orientation.model.vo.Mobile.MobilePostVo;
import com.example.orientation.service.MobilePostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 移动端社交圈
 */
@RestController
@RequestMapping("/mobile/post")
@Slf4j
public class MobilePostController {

    @Autowired
    private MobilePostService mobilePostService;

    /**
     * 发布帖子
     * @return
     */
    @PostMapping("/publish")
    public Result publishPost(@RequestBody MobilePostDto mobilePostDto){
        if (mobilePostDto==null){
            return Result.error("请求参数错误");
        }
        boolean result= mobilePostService.addPost(mobilePostDto);
        ThrowUtils.throwIf(!result,"发布失败");
        return Result.success("发布帖子成功");
    }

    /**
     * 发布帖子的选择同时上传图片或视频
     * @param postId
     * @param file
     * @return
     */
    @PutMapping("/uploadMedia")
    public Result uploadMedia(@RequestParam String postId, @RequestParam MultipartFile file){
        if (postId==null||file==null) {
            return Result.error("请求参数错误");
        }
        boolean result=mobilePostService.uploadMedia(postId,file);
        ThrowUtils.throwIf(!result,"上传失败");
        return Result.success("上传成功");
    }

    /**
     * 删除帖子
     * @param postId
     * @return
     */
    @DeleteMapping("/removePost")
    public Result removePost(String postId){
        if (postId==null) {
            return Result.error("请求参数错误");
        }
        boolean result= mobilePostService.deletePost(postId);
        ThrowUtils.throwIf(!result,"删除失败");
        return Result.success("删除成功");
    }

    /**
     * 获取用户关注的博主的帖子列表
     * @return
     */
    @GetMapping("/getFollowingsAndPosts")
    public Result getFollowingsAndPosts(@RequestParam String followerId){
        if (followerId==null){
            return Result.error("请求参数错误");
        }
        List<FollowAndPostVo> vos=mobilePostService.getAllPost(followerId);
        return Result.success(vos);
    }

    /**
     * 获取用户关注的某个博主的帖子列表
     * @param followerId
     * @param followingId
     * @return
     */
    @GetMapping("/getFollowingPosts")
    public Result getFollowingPosts(@RequestParam String followerId,@RequestParam String followingId){
        if (followerId==null){
            return Result.error("请求参数错误");
        }
        FollowAndPostVo vo=mobilePostService.getFollowingPost(followerId,followingId);
        return Result.success(vo);
    }

    /**
     * 获取用户关注的某个博主的某个帖子详细信息
     * @param followerId
     * @param followingId
     * @param postId
     * @return
     */
    @GetMapping("/getFollowingPostInfo")
    public Result getFollowingPostInfo(@RequestParam String followerId,
                                       @RequestParam String followingId,
                                       @RequestParam String postId){
        MobilePostVo vo= mobilePostService.getFollowingPostInfo(followerId,followingId,postId);
        return Result.success(vo);
    }

    //帖子点赞状态

    //发表评论

    //删除评论

    //查询所有的评论
}
