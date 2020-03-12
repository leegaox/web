package cn.lee.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

//AutoConfigureMockMvc让其进行mock相关的自动配置
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class MockEnvTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testUser() throws Exception {
        ResultActions ra = mvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/getInfo")));
        MvcResult result = ra.andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
