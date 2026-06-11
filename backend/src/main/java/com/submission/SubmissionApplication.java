package com.submission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 申报系统启动类
 * @author 团队成员
 * @since 2026-06-11
 */
@SpringBootApplication
public class SubmissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubmissionApplication.class, args);
        System.out.println("========================================");
        System.out.println("  申报系统后端服务已启动");
        System.out.println("  访问地址: http://localhost:8080");
        System.out.println("  API文档: http://localhost:8080/api");
        System.out.println("========================================");
    }
}