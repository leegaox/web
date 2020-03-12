package cn.lee.spring.controller;

import cn.lee.spring.mapper.UserMapper;
import cn.lee.spring.pojo.Result;
import cn.lee.spring.pojo.User;
import cn.lee.spring.util.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


//该注解是 @Controller 和 @ResponseBody 注解的合体版，默认类中的方法返回值都会以json的格式写进响应体中。
@RestController
@RequestMapping(value = "/api/user/")
@Api(tags = "用户接口")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired(required = true)
    UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询所有用户
     *
     * @return
     */
    //接口路径配置
    @GetMapping(value = "getInfo")
    @ApiOperation(value = "获取用户列表")
    public Result getAllUser() {
        // 首先判断缓存中是否有数据
        List<User> users = (List<User>) redisTemplate.boundValueOps("lee:springboot:users").get();
        logger.debug("users: " + GsonUtil.toJson(users));
        System.out.println("users: " + GsonUtil.toJson(users));
        if (users == null) {
            System.out.println("read users from db...");
            //从数据库中查询
            users = userMapper.getAllUser();
            //放入缓存
            redisTemplate.boundValueOps("lee:springboot:users").set(users);
        }
        return genSuccessResult(users);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "find/{id}")
    @ApiOperation(value = "根据id查找用户")
    public Result findUserById(@PathVariable Integer id) {
        User user = userMapper.findUserById(id);
        if (user != null) {
            return genSuccessResult(user);
        } else {
            return genErrorResult(-1, "");
        }
    }

    /**
     * 增加User
     *
     * @param user
     */
    //RequestParam获取请求体中的参数
    @PostMapping(value = "addUser")
    @ApiOperation(value = "添加新用户")
    public Result addUser(@RequestParam(value = "user", required = true) User user) {
        if (userMapper.addUser(user)) {
            return genSuccessResult(true);
        } else {
            return genErrorResult(-1, "");
        }
    }

    /**
     * 更新
     *
     * @param user
     */
    @PostMapping(value = "update")
    @ResponseBody
    @ApiOperation(value = "更新用户信息")
    public Result updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return genErrorResult(-1, bindingResult.getFieldError().getDefaultMessage());
        }
        if (userMapper.updateUser(user)) {
            return genSuccessResult(true);
        } else {
            return genErrorResult(-1, "");
        }

    }

    /**
     * 刪除
     *
     * @param id
     */
    @GetMapping("delete/{id}")
    @ApiOperation(value = "删除用户")
    public Result deleteUser(@PathVariable(value = "id", required = true) Integer id) {
        if (userMapper.deleteUserById(id)) {
            return genSuccessResult(true);
        } else {
            return genErrorResult(-1, "");
        }
    }


}
