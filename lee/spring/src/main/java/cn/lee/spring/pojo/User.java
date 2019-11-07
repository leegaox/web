package cn.lee.spring.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.io.Serializable;

public class User implements Serializable {
    /***显示指定serialVersionUID***/
    private static final long serialVersionUID = 8162436256378587958L;

    @NotNull(message = "ID不能为空")
    Integer id;
    String name;
    String password;
    //  当前端属性为nickname后台接收对象的属性为nickName时可以用@JsonProperty来保持一致
    @JsonProperty("nickname")
    String nickName;
    @Min(value = 18)
    @Max(value = 30)
    Integer age;
    String imgUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}