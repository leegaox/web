package com.example.learnnetty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearnnettyApplication {
    @Autowired
//    private static HttpServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(LearnnettyApplication.class, args);
//        nettyServer.startServer();
    }

}
