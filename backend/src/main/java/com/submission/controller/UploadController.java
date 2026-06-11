package com.submission.controller;

import com.submission.dto.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${file.upload-path:uploads/}")
    private String uploadPath;

    /**
     * 单文件上传
     */
    @PostMapping
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error(400, "没有文件");
        }

        try {
            String fileName = saveFile(file);
            return Result.success(fileName);
        } catch (IOException e) {
            return Result.error(500, "上传失败");
        }
    }

    /**
     * 多文件上传
     */
    @PostMapping("/multiple")
    public Result<List<String>> uploadMultiple(@RequestParam("files") MultipartFile[] files) {
        if (files.length == 0) {
            return Result.error(400, "没有文件");
        }

        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String fileName = saveFile(file);
                fileNames.add(fileName);
            } catch (IOException e) {
                return Result.error(500, "上传失败");
            }
        }

        return Result.success(fileNames);
    }

    /**
     * 保存文件
     */
    private String saveFile(MultipartFile file) throws IOException {
        // 创建上传目录
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 生成唯一文件名
        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + ext;

        // 保存文件
        File dest = new File(dir, fileName);
        file.transferTo(dest);

        return fileName;
    }
}
