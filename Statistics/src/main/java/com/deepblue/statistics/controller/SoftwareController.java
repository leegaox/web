package com.deepblue.statistics.controller;

import com.deepblue.statistics.domain.Result;
import com.deepblue.statistics.domain.po.SoftwarePO;
import com.deepblue.statistics.mapper.SoftwareMapper;
import com.deepblue.statistics.util.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.deepblue.statistics.Constants.REDIS_SOFTWARES;

@RestController
@RequestMapping(value = "/api/soft/")
@Api(tags = "软件接口")
@Slf4j
public class SoftwareController extends BaseController {

    @Autowired(required = true)
    SoftwareMapper softwareMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 查询软件列表
     *
     * @return
     */
    @GetMapping(value = "getAll")
    @ApiOperation(value = "获取软件列表")
    public Result getAllSoft() {
        List<SoftwarePO> list = null;
        list = (List<SoftwarePO>) redisTemplate.boundValueOps(REDIS_SOFTWARES).get();
        if (list == null) {
            log.info("read software form db.");
            list = softwareMapper.getAllSoftware();
            redisTemplate.boundValueOps(REDIS_SOFTWARES).set(list);
        }
        log.info("read software: " + GsonUtil.toJson(list));
        return genSuccessResult(list);
    }
}
