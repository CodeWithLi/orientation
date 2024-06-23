package com.example.orientation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@MapperScan("com.example.orientation.mapper")
@Transactional
@EnableScheduling
@EnableCaching
@EnableConfigurationProperties
public class OrientationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrientationApplication.class, args);
    }

}