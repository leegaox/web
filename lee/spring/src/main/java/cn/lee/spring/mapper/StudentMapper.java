package cn.lee.spring.mapper;

import cn.lee.spring.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

//hibernate和mybatis 都可以使用注解或者使用mapper配置文件实现增删改查
//Mapper注解表示这个接口是基于注解实现的CRUD。
@Repository //声明为Spring的Bean
@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student")
    List<Student> findAll();
}
