package com.submission.controller;

import com.submission.dto.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传Controller - 角色D：郭哲宇
 * @author 郭哲宇
 * @since 2026-06-11
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    
    @Value("${file.upload-path:./uploads/}")
    private String uploadPath;
    
    /**
     * 单文件上传
     */
    @PostMapping
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error(400, "文件不能为空");
        }
        
        try {
            // 生成唯一文件名
            String originalName = file.getOriginalFilename();
            String ext = originalName.substring(originalName.lastIndexOf("."));
            String newName = UUID.randomUUID().toString() + ext;
            
            // 保存文件
            Path path = Paths.get(uploadPath, newName);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path);
            
            return Result.success(newName);
        } catch (IOException e) {
            return Result.error(500, "文件上传失败：" + e.getMessage());
        }
    }
    
    /**
     * 多文件上传
     */
    @PostMapping("/multiple")
    public Result<List<String>> uploadMultiple(@RequestParam("files") MultipartFile[] files) {
        List<String> names = new ArrayList<>();
        
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String originalName = file.getOriginalFilename();
                    String ext = originalName.substring(originalName.lastIndexOf("."));
                    String newName = UUID.randomUUID().toString() + ext;
                    
                    Path path = Paths.get(uploadPath, newName);
                    Files.createDirectories(path.getParent());
                    Files.copy(file.getInputStream(), path);
                    names.add(newName);
                } catch (IOException e) {
                    return Result.error(500, "文件上传失败：" + e.getMessage());
                }
            }
        }
        
        return Result.success(names);
    }
}
