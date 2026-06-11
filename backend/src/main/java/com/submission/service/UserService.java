package com.submission.service;

import com.submission.dto.Result;
import com.submission.dto.UserDTO;
import com.submission.entity.User;
import com.submission.mapper.UserMapper;
import com.submission.util.JwtUtil;
import com.submission.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户Service
 * @author 团队成员C
 * @since 2026-06-11
 */
@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordUtil passwordUtil;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * 用户注册
     */
    @Transactional
    public Result<User> register(UserDTO userDTO) {
        // 检查手机号是否已注册
        User existingUser = userMapper.selectByPhone(userDTO.getPhone());
        if (existingUser != null) {
            return Result.error(400, "该手机号已注册");
        }
        
        // 创建新用户
        User user = new User();
        user.setPhone(userDTO.getPhone());
        user.setPassword(passwordUtil.encode(userDTO.getPassword()));
        user.setRealName(userDTO.getRealName());
        user.setIdType(userDTO.getIdType());
        user.setIdNumber(userDTO.getIdNumber());
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());
        
        userMapper.insert(user);
        
        // 生成token
        String token = jwtUtil.generateToken(user.getId());
        user.setPassword(null);
        user.setToken(token);
        
        return Result.success(user);
    }
    
    /**
     * 用户登录
     */
    public Result<User> login(UserDTO userDTO) {
        User user = userMapper.selectByPhone(userDTO.getPhone());
        if (user == null) {
            return Result.error(400, "用户不存在");
        }
        
        // 密码登录
        if (userDTO.getPassword() != null) {
            if (!passwordUtil.matches(userDTO.getPassword(), user.getPassword())) {
                return Result.error(400, "密码错误");
            }
        }
        // 验证码登录
        else if (userDTO.getSmsCode() != null) {
            if (!"123456".equals(userDTO.getSmsCode())) {
                return Result.error(400, "验证码错误");
            }
        }
        else {
            return Result.error(400, "请提供密码或验证码");
        }
        
        // 更新登录时间
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateLastLoginTime(user);
        
        // 生成token
        String token = jwtUtil.generateToken(user.getId());
        user.setPassword(null);
        user.setToken(token);
        
        return Result.success(user);
    }
    
    /**
     * 一键测试登录
     */
    public Result<User> demoLogin() {
        String testPhone = "13800138000";
        User user = userMapper.selectByPhone(testPhone);
        
        // 如果不存在，自动创建测试账号
        if (user == null) {
            user = new User();
            user.setPhone(testPhone);
            user.setPassword(passwordUtil.encode("123456"));
            user.setRealName("测试用户");
            user.setIdType("id_card");
            user.setIdNumber("110101199001011234");
            user.setStatus(1);
            user.setCreateTime(LocalDateTime.now());
            user.setLastLoginTime(LocalDateTime.now());
            userMapper.insert(user);
        }
        
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateLastLoginTime(user);
        
        // 生成token
        String token = jwtUtil.generateToken(user.getId());
        user.setPassword(null);
        user.setToken(token);
        
        return Result.success(user);
    }
    
    /**
     * 获取用户信息
     */
    public Result<User> getProfile(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.error(404, "用户不存在");
        }
        user.setPassword(null);
        return Result.success(user);
    }
    
    /**
     * 发送短信验证码
     */
    public Result<String> sendSms(String phone) {
        return Result.success("123456");
    }
    
    /**
     * 重置密码
     */
    @Transactional
    public Result<String> resetPassword(String phone, String smsCode, String newPassword) {
        if (!"123456".equals(smsCode)) {
            return Result.error(400, "验证码错误");
        }
        
        User user = userMapper.selectByPhone(phone);
        if (user == null) {
            return Result.error(400, "用户不存在");
        }
        
        userMapper.updatePassword(phone, passwordUtil.encode(newPassword));
        return Result.success("密码重置成功");
    }
}