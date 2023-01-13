package com.deng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.deng.dao.mapper") // 这里开启了扫描 MyBatis-plus需要

public class DealApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealApplication.class, args);

    }

    @Bean
    public CommandLineRunner MyCommandLineRunner() {
		return args -> {
            System.out.println("swagger地址: "+"http://localhost:9999/swagger-ui/index.html");
        };
    }


}
