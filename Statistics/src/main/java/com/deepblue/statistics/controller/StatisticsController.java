package com.deepblue.statistics.controller;

import com.deepblue.statistics.domain.Result;
import com.deepblue.statistics.domain.dto.Record;
import com.deepblue.statistics.domain.dto.SoftUseRecord;
import com.deepblue.statistics.domain.dto.SoftUseStatistics;
import com.deepblue.statistics.domain.po.EmployeePO;
import com.deepblue.statistics.domain.po.RecordPO;
import com.deepblue.statistics.domain.po.RecordSoftPO;
import com.deepblue.statistics.domain.po.SoftwarePO;
import com.deepblue.statistics.mapper.EmployeeMapper;
import com.deepblue.statistics.mapper.RecordMapper;
import com.deepblue.statistics.mapper.SoftwareMapper;
import com.deepblue.statistics.mapper.StatisticsMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/statistics/")
@Api(tags = "统计接口")
@Slf4j
public class StatisticsController extends BaseController {

    @Autowired(required = true)
    RecordMapper recordMapper;
    @Autowired(required = true)
    EmployeeMapper employeeMapper;
    @Autowired
    StatisticsMapper statisticsMapper;
    @Autowired
    SoftwareMapper softwareMapper;

    /**
     * 提交问卷调查
     */
    @PostMapping(value = "commit")
    @Transactional
    public Result commitInfo(@RequestBody Record param) {
        log.info("commitInfo ----> params: " + param);
//        String testParams = "{\"employeeName\":\"李明\",\"jobId\":\"1214\",\"department\":\"行政部\",\"softwareList\":[{\"id\":1,\"name\":\"Office\"},{\"id\":5,\"name\":\"Xmind\"}],\"remark\":\"VS\"}";
//        Record param = GsonUtil.fromJson(testParams, Record.class);
        if (param == null) {
            return genErrorResult(-2, "数据错误。");
        }
        EmployeePO employeeDTO = new EmployeePO(param.getEmployeeName(), param.getJobId(), param.getDepartment());
        int count = recordMapper.queryRecordNum(employeeDTO.getJobId());
        log.info("commitInfo ----> count : " + count);
        if (count > 0) {
            return genErrorResult(-2, "您已提交过，请勿重复提交。");
        }
        employeeMapper.addEmployee(employeeDTO);
        int lastEmployeeRow = employeeDTO.getId();
        log.info("commitInfo ----> add employee id : " + lastEmployeeRow);
        //插入记录
        RecordPO record = new RecordPO(lastEmployeeRow, param.getRemark());
        recordMapper.addRecord(record);
        int lastRecordRow = record.getId();
        log.info("commitInfo ----> add record id : " + lastRecordRow);
        //记录与软件关系表插入数据
        List<RecordSoftPO> rsList = new ArrayList();
        for (SoftwarePO bean : param.getSoftwareList()) {
            rsList.add(new RecordSoftPO(lastRecordRow, bean.getId()));
        }
        boolean result = recordMapper.insertCollectionList(rsList);
        log.info("add record soft: " + result);
        return genSuccessResult("");
    }

    @GetMapping(value = "pullSoft")
    public Result pullSoftStatisticsInfo() {
        List<SoftUseStatistics> softUseStatisticsList = new ArrayList<>();
        List<SoftwarePO> softList = softwareMapper.getAllSoftware();
        List<SoftUseRecord> softUseRecords = statisticsMapper.getAllSoftUseRecord();
        //組裝數據
        for (SoftwarePO soft : softList) {
            SoftUseStatistics softUseStatistics = new SoftUseStatistics();
            softUseStatistics.setSoftwareId(soft.getId());
            softUseStatistics.setSoftwareName(soft.getSoftwareName());
            List employeeList = new ArrayList<EmployeePO>();
            for (SoftUseRecord softUseRecord : softUseRecords) {
                if (softUseRecord.getSoftwareId() == soft.getId()) {
                    EmployeePO employeePO = new EmployeePO(softUseRecord.getEmployeeId(), softUseRecord.getEmployeeName(), softUseRecord.getJobId(), softUseRecord.getDepartment());
//                    employeePO.setName(softUseRecord.getEmployeeName());
//                    employeePO.setId(softUseRecord.getEmployeeId());
//                    employeePO.setJobId(softUseRecord.getJobId());
//                    employeePO.setDepartment(softUseRecord.getDepartment());
                    employeeList.add(employeePO);
                }
                softUseStatistics.setEmployeeList(employeeList);
            }

            softUseStatisticsList.add(softUseStatistics);
        }
        return genSuccessResult(softUseStatisticsList);
    }
}
