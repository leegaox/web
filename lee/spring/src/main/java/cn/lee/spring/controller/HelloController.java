package cn.lee.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.Date;

//为了能够将HTML代码返回给浏览器，浏览器再进行页面的渲染、显示：进行模板渲染，需要将@RestController改成@Controller
//返回值"hello"并非直接将字符串返回给浏览器，而是寻找名字为hello的模板（hello.jsp）进行渲染
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello(Model m){
        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        return "hello";
    }
}
