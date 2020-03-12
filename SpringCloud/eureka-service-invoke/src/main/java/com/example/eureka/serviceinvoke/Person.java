package com.example.eureka.serviceinvoke;

import lombok.Data;

@Data
public class Person {
    public Person(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    Integer id;
    String name;
    Integer age;


}
