package com.deepblue.statistics.controller;

import com.deepblue.statistics.domain.dto.Record;
import com.deepblue.statistics.domain.po.EmployeePO;
import com.deepblue.statistics.domain.po.RecordPO;
import com.deepblue.statistics.domain.po.RecordSoftPO;
import com.deepblue.statistics.domain.Result;
import com.deepblue.statistics.domain.po.SoftwarePO;
import com.deepblue.statistics.mapper.EmployeeMapper;
import com.deepblue.statistics.mapper.RecordMapper;
import com.deepblue.statistics.util.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/record/")
@Api(tags = "记录接口")
@Slf4j
public class RecordController extends BaseController {

    @Autowired(required = true)
    RecordMapper recordMapper;


    /**
     * 查询记录列表
     *
     * @return
     */
    @GetMapping(value = "getAll")
    @ApiOperation(value = "获取记录列表")
    public Result getAllSoft() {
        List<RecordPO> list = recordMapper.getAllRecord();
        log.info("read records: " + GsonUtil.toJson(list));
        return genSuccessResult(list);
    }

}
