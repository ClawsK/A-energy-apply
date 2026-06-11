package com.submission.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * @author 团队成员C
 * @since 2026-06-11
 */
@Data
public class User {
    
    /** 用户ID */
    private Long id;
    
    /** 手机号 */
    private String phone;
    
    /** 密码（加密存储） */
    private String password;
    
    /** 真实姓名 */
    private String realName;
    
    /** 证件类型 */
    private String idType;
    
    /** 证件号码 */
    private String idNumber;
    
    /** 账号状态：1-正常，0-禁用 */
    private Integer status;
    
    /** 创建时间 */
    private LocalDateTime createTime;
    
    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;
    
    /** JWT token（不存入数据库） */
    private String token;
}