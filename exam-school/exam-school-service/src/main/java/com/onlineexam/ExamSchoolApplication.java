package com.onlineexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.onlineexam.mapper")
@EnableFeignClients
public class ExamSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamSchoolApplication.class, args);
    }
}
