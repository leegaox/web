package cn.lee.spring.controller;

import cn.lee.spring.pojo.Result;

public class BaseController<T> {

    private Result result = new Result();

    public Result<T> genSuccessResult(T data){
        result.setKey(200);
        result.setMessage("");
        result.setResult(data);
        return result;
    }

    public Result<T> genErrorResult(int errorCode,String errorMsg){
        result.setKey(errorCode);
        result.setMessage(errorMsg);
        result.setResult("");
        return result;
    }
}
