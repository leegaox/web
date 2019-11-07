package cn.lee.spring.controller;


import cn.lee.spring.mapper.StudentMapper;
import cn.lee.spring.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class StudentController {

    @Autowired(required = true)
    StudentMapper studentMapper;

    @RequestMapping(value = "/listStudent",method = RequestMethod.GET)
    public String listStudent(Model model) {
        List<Student> students = studentMapper.findAll();
        model.addAttribute("students", students);
        return "listStudent";
    }
}
