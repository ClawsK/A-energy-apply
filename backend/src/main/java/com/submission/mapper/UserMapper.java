package com.submission.mapper;

import com.submission.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户Mapper
 * @author 团队成员C
 * @since 2026-06-11
 */
@Mapper
public interface UserMapper {
    
    @Insert("INSERT INTO t_user (phone, password, real_name, id_type, id_number, status, create_time, last_login_time) " +
            "VALUES (#{phone}, #{password}, #{realName}, #{idType}, #{idNumber}, #{status}, #{createTime}, #{lastLoginTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    
    @Select("SELECT * FROM t_user WHERE id = #{id}")
    User selectById(Long id);
    
    @Select("SELECT * FROM t_user WHERE phone = #{phone}")
    User selectByPhone(String phone);
    
    @Update("UPDATE t_user SET last_login_time = #{lastLoginTime} WHERE id = #{id}")
    int updateLastLoginTime(User user);
    
    @Update("UPDATE t_user SET password = #{password} WHERE phone = #{phone}")
    int updatePassword(@Param("phone") String phone, @Param("password") String password);
}