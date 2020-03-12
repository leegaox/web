package com.deepblue.statistics.controller;

import com.deepblue.statistics.domain.po.EmployeePO;
import com.deepblue.statistics.domain.Result;
import com.deepblue.statistics.mapper.EmployeeMapper;
import com.deepblue.statistics.util.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/employee/")
@Api(tags = "员工接口")
@Slf4j
public class EmployeeController extends BaseController {

    @Autowired(required = true)
    EmployeeMapper employeeMapper;

    /**
     * 查询软件列表
     *
     * @return
     */
    @GetMapping(value = "getAll")
    @ApiOperation(value = "获取员工列表")
    public Result getAllSoft() {
        List<EmployeePO> list = employeeMapper.getAllEmployee();
        log.info("read employee: " + GsonUtil.toJson(list));
        return genSuccessResult(list);
    }

    @GetMapping(value = "add")
    @ApiOperation(value = "插入员工列表")
    public Result addEmployee() {
        EmployeePO employeeDTO = new EmployeePO("TEST", "1", "行政");
        employeeMapper.addEmployee(employeeDTO);
        log.info("add employee: " + GsonUtil.toJson(employeeDTO));
        return genSuccessResult(employeeDTO);
    }
}
