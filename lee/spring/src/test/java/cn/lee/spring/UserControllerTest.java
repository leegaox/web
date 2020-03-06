package cn.lee.spring;

import cn.lee.spring.controller.UserController;
import cn.lee.spring.pojo.Result;
import cn.lee.spring.util.GsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//测试业务组件UserController
//业务组件无需启动web容器 设置为SpringBootTest.WebEnvironment.NONE
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @Test
    public void testUser(){
        Result result =userController.getAllUser();
        System.out.println("UserControllerTest: "+GsonUtil.toJson(result));
    }
}
