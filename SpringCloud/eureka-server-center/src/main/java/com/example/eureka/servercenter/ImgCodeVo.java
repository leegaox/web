package com.example.eureka.servercenter;

import lombok.Data;

@Data
public class ImgCodeVo implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8392439236359030241L;

    private String imgData;

    private String imgKey;

    private String rand;

}
