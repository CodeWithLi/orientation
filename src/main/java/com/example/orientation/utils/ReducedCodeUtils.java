    package com.example.orientation.utils;

    import com.example.orientation.constant.AdminUserStatusConstant;
    import com.example.orientation.exception.BaseException;
    import com.example.orientation.mapper.AdminAdvertiseMapper;
    import com.example.orientation.model.po.Admin.AdminAdvertisePo;
    import com.example.orientation.model.po.MobileUser.MobileLoginPo;
    import com.example.orientation.model.po.MobileUser.MobileRegisterPo;
    import com.example.orientation.model.vo.Admin.AdminAdvertiseVo;
    import com.example.orientation.model.vo.MobileUser.MobileLoginVo;
    import jakarta.annotation.Resource;
    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Component;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;

    //简化代码工具类
    @Component
    public class ReducedCodeUtils {

        @Autowired
        private MinioUtils minioUtils;

        @Resource
        private AdminAdvertiseMapper adminAdvertiseMapper;


        //简化客户端查询所有广告并封装成vos
        public  List<AdminAdvertiseVo> AdminByAllAdvertise(List<AdminAdvertisePo> pos){

            List<AdminAdvertiseVo> vos = pos.stream().map(adminAdvertisePo -> {
                AdminAdvertiseVo advertiseVo = new AdminAdvertiseVo();
                BeanUtils.copyProperties(adminAdvertisePo, advertiseVo);
                //先查询该广告有没有图片，有则进行下一步
                List<Integer> counts=adminAdvertiseMapper.exitMedia(advertiseVo.getAdvertiseId());
                if (counts.size()>=1){
                    //根据po查询出广告所有的图片
                    List<String> allMedias=
                            adminAdvertiseMapper.selectAllMedia(adminAdvertisePo.getAdvertiseId());
                    List<String> medias=new ArrayList<>();
                    //遍历allMedias
                    for (String allMedia : allMedias) {
                        if (allMedia != null){
                            medias.add(minioUtils.preview(allMedia));
                        }
                }
                advertiseVo.setImageVideos(medias);
            }
            advertiseVo.setTotalProfit(advertiseVo.getClickNumber()*advertiseVo.getCosts());
            return advertiseVo;
        }).collect(Collectors.toList());
        return vos;

//        List<AdminAdvertiseVo> vos = pos.stream().map(adminAdvertisePo -> {
//            AdminAdvertiseVo advertiseVo = new AdminAdvertiseVo();
//            BeanUtils.copyProperties(adminAdvertisePo, advertiseVo);
//            if (!"".equals(adminAdvertisePo.getImageVideoName())&&adminAdvertisePo.getImageVideoName()!=null){
//                advertiseVo.setImageVideo(minioUtils.preview(adminAdvertisePo.getImageVideoName()));
//            }
//            advertiseVo.setTotalProfit(advertiseVo.getClickNumber()*advertiseVo.getCosts());
//            return advertiseVo;
//        }).collect(Collectors.toList());
//        return vos;
    }


    //简化移动端用户登录的判断数据库是否有该用户

    public static MobileLoginVo mobileToLoginByPassword(MobileLoginPo mobileLoginPo, BCryptPasswordEncoder bCryptPasswordEncoder,String password){
        if (mobileLoginPo==null){
            throw new BaseException("用户不存在");
        }
        if (mobileLoginPo.getStatus()== AdminUserStatusConstant.STUDENT_STOP) {
            throw new BaseException("用户未注册");
        }
        if (!bCryptPasswordEncoder.matches(password, mobileLoginPo.getPassword())){
            throw new BaseException("密码错误");
        }
        MobileLoginVo loginVo=new MobileLoginVo();
        BeanUtils.copyProperties(mobileLoginPo,loginVo);
        return loginVo;
    }

    //简化移动端用户注册时根据身份证判断是否存在该用户
    public static void mobileByRegisterStudent(MobileRegisterPo po){
        if (po==null){
            throw new BaseException("该用户不存在");
        }
        if (po.getStatus()==AdminUserStatusConstant.STUDENT_SUCCESS){
            throw new BaseException("该用户已注册");
        }
    }

    //简化文件判断
    public static void file(MultipartFile file){
        if (file==null){
            throw new BaseException("请求参数错误");
        }
        String contentType1 = file.getContentType();
        if (!contentType1.startsWith("image")) {
            throw new BaseException("请正确上传图片");
        }
        long maxImageSizeBytes = 10 * 1024 * 1024;
        long fileSize1 = file.getSize();
        if (fileSize1 > maxImageSizeBytes) {
            throw new BaseException("图片大小最大为10MB");
        }
    }

    //简化移动端查询用户关注的博主或用户的粉丝
    public static void FocusToFollow(){

    }



}