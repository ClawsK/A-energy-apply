package com.submission.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户注册/登录DTO
 * @author 团队成员C
 * @since 2026-06-11
 */
@Data
public class UserDTO {
    
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    private String smsCode;
    
    // 注册专用字段
    private String realName;
    private String idType;
    private String idNumber;
}