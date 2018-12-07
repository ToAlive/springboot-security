package com.example.springbootsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1.导入security stater
 * 2.编写security相关的配置
 * 3.编写一个继承WebSecurityConfigurerAdapter的配置类，使用@EnableWebSecurity开启Security
 * 4.控制请求的权限
 */
@SpringBootApplication
public class SpringbootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurityApplication.class, args);
    }
}
