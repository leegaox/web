package com.deepblue.statistics.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private int key;
    private String message = "";
    private T result;
    private Long time = System.currentTimeMillis();
}