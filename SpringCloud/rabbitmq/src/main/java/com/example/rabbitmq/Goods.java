package com.example.rabbitmq;

import lombok.Data;

import java.io.Serializable;

@Data
public class Goods implements Serializable {

    private Long goodsId;
    private String goodsName;
    private String goodsIntroduce;
    private Double goodsPrice;

}
