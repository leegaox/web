package cn.lee.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@SpringBootApplication 是 Spring Boot 的核心注解，它是一个组合注解，
// 该注解组合了：@Configuration、@EnableAutoConfiguration、@ComponentScan；，用于开启组件扫描和自动配置
//MapperScan: mapper 接口类扫描包配置

//SpringApplication 则是用于从main方法启动Spring应用的类。
//默认，它会执行以下步骤：
//1.创建一个合适的ApplicationContext实例 （取决于classpath）。
//2.注册一个CommandLinePropertySource，以便将命令行参数作为Spring properties。
//3.刷新application context，加载所有单例beans。
//4.激活所有CommandLineRunner beans。
@SpringBootApplication
@MapperScan("cn.lee.spring")
public class Application {

    public static void main(String[] args) {
        // spring boot执行流程的主方法，启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(Application.class, args);
    }

}
