package cn.lee.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

//运行测试时，web容器随机分配端口
// SpringBootTest.WebEnvironment.DEFINED_PORT会使用项目配置的端口
//SpringBootTest.WebEnvironment.DEFINED_PORT 和 SpringBootTest.WebEnvironment.RANDOM_PORT都会启动真实的web容器;
// 设置SpringBootTest.WebEnvironment.MOCK来启动模拟的web容器
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class UserTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testHello(){
        String result =restTemplate.getForObject("/api/user/getInfo",String.class);
        System.out.println("result:"+result);
    }
}
