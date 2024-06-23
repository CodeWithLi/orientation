package com.example.orientation.controller;


import com.example.orientation.common.Result;
import com.example.orientation.exception.ThrowUtils;
import com.example.orientation.model.dto.Mobile.MobileFollowDto;
import com.example.orientation.model.vo.Mobile.FocusToFollowers;
import com.example.orientation.model.vo.Mobile.FocusToFollowings;
import com.example.orientation.model.vo.Mobile.MobileFollowingInfoVo;
import com.example.orientation.service.MobileFollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * 移动端关注关系
 */
@RestController
@RequestMapping("/mobile/follow")
@Slf4j
public class MobileFollowController {
    @Autowired
    private MobileFollowService mobileFollowService;


    /**
     * 根据博主手机号查询博主信息
     * @param phone
     * @return
     */
    @GetMapping("/getFollowingInfo")
    public Result followingInformation(@RequestParam String phone){
        if (phone==null){
            return Result.error("请求参数错误");
        }
//根据手机号查询博主信息
        MobileFollowingInfoVo vo= mobileFollowService.getFollowInformation(phone);
        return Result.success(vo);
    }


    /**
     * 设置博主的关注状态
     * @param mobileFollowDto
     * @return
     */
    @PutMapping("/followStatus")
    public Result followUser(@RequestBody MobileFollowDto mobileFollowDto){
        if (mobileFollowDto==null){
            return Result.error("请求参数错误");
        }
        boolean result=mobileFollowService.followUser(mobileFollowDto);
        ThrowUtils.throwIf(!result,"更改关注状态失败");
        return Result.success("更改关注状态成功");
    }


    /**
     * 获取用户关注的博主列表
     * @param followerId
     * @return
     */
    @GetMapping("/focusFollowings")
    public Result focusFollowings(@RequestParam String followerId){
        if (followerId==null){
            return Result.error("请求参数错误");
        }
        List<FocusToFollowings> vos=mobileFollowService.focusFollowings(followerId);
        return Result.success(vos);
    }


    /**
     * 获取用户的粉丝列表
     * @param followingId
     * @return
     */
    @GetMapping("/focusFollowers")
    public Result focusFollowers(@RequestParam String followingId){
        if (followingId==null){
            return Result.error("请求参数错误");
        }
        List<FocusToFollowers> vos=mobileFollowService.focusFollowers(followingId);
        return Result.success(vos);
    }
}
