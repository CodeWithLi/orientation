package com.example.orientation;

import com.example.orientation.mapper.AdminStudentMapper;
import com.example.orientation.mapper.MobileRankingMapper;
import com.example.orientation.mapper.MobileTaskMapper;
import com.example.orientation.model.dto.Mobile.MobileFollowDto;
import com.example.orientation.model.po.CountPointPo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class OrientationApplicationTests {

//	@Autowired
//	private FaceIdentifyUtils faceIdentifyUtils;
//
//	@Autowired
//	private FaceIdentifyProperties identifyProperties;
//
//
//	@Test
//	void VerifyFaceTest(){
//		VerifyFaceDto dto=new VerifyFaceDto();
//		dto.setPersonId("7e1b97b8-4b32-4b1c-b626-ba67efda1318");
////		dto.setImage();
//		dto.setUrl("http://114.132.67.226:9000/orientation/WIN_20230705_14_00_07_Pro.jpg?Content-Disposition=attachment%3B%20filename%3D%22WIN_20230705_14_00_07_Pro.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minio%2F20240312%2F%2Fs3%2Faws4_request&X-Amz-Date=20240312T140410Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=291113603550764bf7641e77185793e6eb0c066e08dc58400f0a3e50f289bc66");
//		dto.setQualityControl(0L);
//		dto.setNeedRotateDetection(1L);
//		VerifyFaceResponse faceResponse = faceIdentifyUtils.VerifyFace(dto, identifyProperties.getSecretId(), identifyProperties.getSecretKey());
//		System.out.println(faceResponse.getScore());
//		if (faceResponse.getScore()<80L){
//			System.out.println("验证失败");
//		}
//	}
//
//
//	@Test
//	void  CreatePersonTest() {
//		CreatePersonDto dto=new CreatePersonDto();
//		dto.setGroupId("3506fa78-8e0e-49ab-86a9-f58233409d1a");
//		dto.setPersonId(UUID.randomUUID().toString());
//		System.out.println(dto.getPersonId());
//		dto.setPersonName("小李");
//		dto.setGender(1L);
//		dto.setUrl("http://114.132.67.226:9000/orientation/WIN_20230301_20_28_37_Pro.jpg?Content-Disposition=attachment%3B%20filename%3D%22WIN_20230301_20_28_37_Pro.jpg%22&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minio%2F20240312%2F%2Fs3%2Faws4_request&X-Amz-Date=20240312T135024Z&X-Amz-Expires=432000&X-Amz-SignedHeaders=host&X-Amz-Signature=57aaadfb8902e995cfe6aa612211db26f3489d96adadb8c6d8a01d8756ec07fc");
//		CreatePersonResponse createPerson = faceIdentifyUtils.CreatePerson(dto, identifyProperties.getSecretId(), identifyProperties.getSecretKey());
//		System.out.println(createPerson);
//	}
//
//	@Test
//	void  CreateGroupTest() {
//		CreateGroupDto dto=new CreateGroupDto();
//		dto.setGroupId(UUID.randomUUID().toString());
//		System.out.println(dto.getGroupId());
//		dto.setGroupName("测试");
//		dto.setTag("只针对于大学生");
//		dto.setGroupExDescriptions(new String[]{"学号","身份证","姓名"});
//		dto.setFaceModelVersion("3.0");
//		CreateGroupResponse group = faceIdentifyUtils.createGroup(dto, identifyProperties.getSecretId(), identifyProperties.getSecretKey());
//		System.out.println(group.toString());
//	}
//
//	@Resource
//	private AdminStudentMapper adminStudentMapper;
//	@Resource
//	private MobileRankingMapper mobileRankingMapper;
////
//	@Test
//	void test(){
//		int count=0;
//		//统计总积分
//		List<CountPointPo> list=adminStudentMapper.countPoint();
//		//根据总积分重新设定排行榜
//		//对list重新培训，根据从高往低
//		Collections.sort(list, Comparator.comparingInt(CountPointPo::getPoints).reversed());
//		//遍历集合
//		for (int i = 0; i < list.size(); i++){
//			CountPointPo po = list.get(i);
//			po.setRanking(i+1);
//			mobileRankingMapper.updatePoints(po);
//			count++;
//		}
//		System.out.println(count);
//
//	}
//
//	@Resource
//	private MobileTaskMapper mobileTaskMapper;
//
//	@Test
//	void test02(){
//		MobileFollowDto dto=new MobileFollowDto();
//		dto.setCreateTime(new Date().toString());
//		System.out.println(new Date().toString());
//		System.out.println(dto.getCreateTime());
//	}

}


