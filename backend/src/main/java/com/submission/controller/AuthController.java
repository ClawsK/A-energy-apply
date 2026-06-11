package com.submission.controller;

import com.submission.dto.Result;
import com.submission.dto.UserDTO;
import com.submission.entity.User;
import com.submission.service.UserService;
import com.submission.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 认证Controller
 * @author 团队成员C
 * @since 2026-06-11
 */
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }
    
    /**
     * 一键测试登录
     */
    @PostMapping("/demo-login")
    public Result<User> demoLogin() {
        return userService.demoLogin();
    }
    
    /**
     * 获取用户信息
     */
    @GetMapping("/profile")
    public Result<User> getProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return userService.getProfile(userId);
    }
    
    /**
     * 发送短信验证码
     */
    @PostMapping("/sms")
    public Result<String> sendSms(@RequestParam String phone) {
        return userService.sendSms(phone);
    }
    
    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    public Result<String> resetPassword(@RequestParam String phone,
                                       @RequestParam String smsCode,
                                       @RequestParam String newPassword) {
        return userService.resetPassword(phone, smsCode, newPassword);
    }
}