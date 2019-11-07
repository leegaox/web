package cn.lee.spring.service;

import cn.lee.spring.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserService {

    public User getUserInfo();

}
