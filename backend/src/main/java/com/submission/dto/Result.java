package com.submission.dto;

import lombok.Data;

/**
 * 统一响应结果
 * @author 团队成员
 * @since 2026-06-11
 */
@Data
public class Result<T> {
    
    /** 状态码 */
    private Integer code;
    
    /** 提示信息 */
    private String message;
    
    /** 数据 */
    private T data;
    
    public static <T> Result<T> success() {
        return success(null);
    }
    
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }
    
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}