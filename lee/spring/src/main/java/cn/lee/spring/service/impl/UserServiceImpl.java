package cn.lee.spring.service.impl;

import cn.lee.spring.mapper.UserMapper;
import cn.lee.spring.pojo.User;
import cn.lee.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

//Component注解 注册Bean并装配到Spring容器中
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public User getUserInfo() {
        User user = new User();
        user.setName("Lee");
        user.setPassword("12341234");
        return user;
    }

}
