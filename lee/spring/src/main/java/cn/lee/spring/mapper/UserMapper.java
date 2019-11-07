package cn.lee.spring.mapper;

import cn.lee.spring.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    public List<User> getAllUser();

    @Select("SELECT * FROM user where id=#{id}")
    User findUserById(Integer id);

    /**
     * 用户数据新增
     */
    @Insert("insert int user(name,password,nickname,age,imgUrl) values (#{name},#{password},#{nickname},#{age},#{imgUrl})")
    boolean addUser(User user);

    /**
     * 用户数据修改
     */
    @Update("update user set name=#{name},password=#{password},nickName=#{nickName},age=#{age},imgUrl=#{imgUrl} where id=#{id}")
    boolean updateUser(User user);

    /**
     * 用户数据删除
     */
    @Delete("delete from user where id=#{id}")
    boolean deleteUserById(Integer id);


}
