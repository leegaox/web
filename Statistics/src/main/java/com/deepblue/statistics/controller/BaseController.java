package com.deepblue.statistics.controller;


import com.deepblue.statistics.domain.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController<T> {

    private Result result = new Result();

    public Result<T> genSuccessResult(T data) {
        result.setKey(200);
        result.setMessage("");
        result.setResult(data);
        log.info("response success result：" + result);
        return result;
    }

    public Result<T> genErrorResult(int errorCode, String errorMsg) {
        result.setKey(errorCode);
        result.setMessage(errorMsg);
        result.setResult("");
        log.error("response error result：" + result);
        return result;
    }
}
