package cn.lee.spring;

import cn.lee.spring.config.MyConfig;
import cn.lee.spring.controller.UserController;
import cn.lee.spring.mapper.UserMapper;
import cn.lee.spring.pojo.Result;
import cn.lee.spring.pojo.User;
import cn.lee.spring.util.GsonUtil;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MockTest {
    @MockBean
    private UserMapper userMapper;
    @Autowired
    private UserController userController;

    @Autowired
    private MyConfig config;

//    @Autowired
//    private MyProp prop;

    @Test
    public void testUserController() {
        //模拟userMapper的getAllUser返回数据

        List mockList = new ArrayList<User>();
        User user = new User();
        user.setName("GAOX");
        mockList.add(user);
        BDDMockito.given(this.userMapper.getAllUser()).willReturn(mockList);
        Result result = userController.getAllUser();


        System.out.println("MockTest: " + result);
        System.out.println("config: " + config.getJdbcUrl());
//        System.out.println("prop：" + GsonUtil.toJson(prop));
    }
}
